//
//  MyClass.m
//  viewimagetouchtest
//
//  Created by Slobodan Pejic on 11-10-19.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SPDiceView.h"

@interface CacheImage : NSObject {
	@private
	int refcount;
	UIImage* image;
	UIImage* selected;
}

-(id) initWithImage: (UIImage*) image
	andSelected: (UIImage*) selected;

-(UIImage*) image;

-(UIImage*) selected;

-(void) imageRetain;

-(void) imageRelease;

@end

@implementation CacheImage

-(id) initWithImage: (UIImage*) image_
	andSelected: (UIImage*) selected_
{
	if (![super init]) {
		return nil;
	}
	image = [image_ retain];
	selected = [selected_ retain];
	refcount = 0;
	return self;
}

-(void) dealloc
{
	[image release];
	[selected release];
	[super dealloc];
}

-(BOOL) isEqual: (id) other
{
	if ([other isMemberOfClass: [CacheImage class]]) {
		CacheImage* o = other;
		if (o->refcount == self->refcount
		    && o->image == self->image) {
			return YES;
		}
	}
	return NO;
}

-(UIImage*) image
{
	return (image);
}

-(UIImage*) selected
{
	return (selected);
}

-(void) imageRetain
{
	refcount++;
}

-(void) imageRelease
{
	refcount--;
}
	
@end


@interface SPDiceView (Private)
-(void) updateDice;
-(void) updateSelection;
-(CacheImage*) imageCacheForDie: (SPDie*) die;
-(int) diePositionForLocation: (CGPoint) loc;
-(void) onChangeNotification: (NSNotification*) note;
@end

@implementation SPDiceView (Private)
-(void) updateDice
{
	int numdice = [dice count];
	while (numdice > [imageViews count]) {
		UIImageView* imgview = [[UIImageView alloc]
					initWithImage: nil];
		[imageViews addObject: imgview];
		[self addSubview: imgview];
		[imgview release];
		UIImageView* selview = [[UIImageView alloc]
					initWithImage: nil];
		[selectedViews addObject: selview];
		[self addSubview: selview];
		[self sendSubviewToBack: selview];
		[selview release];
	}
	int pos;
	for (pos = 0; pos < [imageViews count]; pos++) {
		SPDie* die = nil;
		if (pos < [dice count]) {
			die = [dice getDieAtIndex: pos];
		}
		CacheImage* oldcache = nil;
		if (pos < [dieImageCache count]) {
			oldcache = [dieImageCache objectAtIndex: pos];
		}
		CacheImage* cache = [self imageCacheForDie: die];
		if (oldcache == cache) {
			continue;
		}
		UIImageView* imgview = [imageViews objectAtIndex: pos];
		UIImageView* selview = [selectedViews objectAtIndex: pos];
		[cache imageRetain];
		UIImage* img = [cache image];
		UIImage* sel = [cache selected];
		imgview.image = img;
		selview.image = sel;
		[oldcache imageRelease];
		if (pos < [dieImageCache count] && cache) {
			[dieImageCache replaceObjectAtIndex: pos
						 withObject: cache];
		}
		else if (pos < [dieImageCache count] && !cache) {
			[dieImageCache removeObjectAtIndex: pos];
		}
		else {
			assert(pos == [dieImageCache count]);
			[dieImageCache addObject: cache];
		}
		imgview.bounds = CGRectMake(0, 0,
					    img.size.width, img.size.height);
		selview.bounds = CGRectMake(0, 0,
					    sel.size.width, sel.size.height);
		int row = pos % dicePerRow;
		int col = pos / dicePerRow;
		float width = self.bounds.size.width;
		CGPoint center = CGPointMake(
					     (width/dicePerRow)*(row+0.5),
					     (rowHeight)*(col + 0.5));
		imgview.center = center;
		selview.center = center;
	}
}

-(void) updateSelection
{
	int pos;
	for (pos = 0; pos < [imageViews count]; pos++) {
		UIImageView* selview = [selectedViews objectAtIndex: pos];
		if (pos < [dice count]) {
			selview.hidden = !selectionEnabled
					|| ![dice getSelectedAtIndex: pos];
		}
	}
}

-(CacheImage*) imageCacheForDie: (SPDie*) die
{
	if (!die) {
		return nil;
	}
	NSString* path = [NSString stringWithFormat: @"assets/%@.png",
			  [die toString]];
	NSString* selpath = [NSString stringWithFormat:
			     @"assets/%@-selected.png", [die toString]];
	CacheImage* cache = [imageCache objectForKey: path];
	if (!cache) {
		cache = [[CacheImage alloc]
			 initWithImage: [UIImage imageNamed: path]
			 andSelected: [UIImage imageNamed: selpath]];
		if (cache) {
			[imageCache setObject: cache forKey: path];
		}
		[cache release];
	}
	return (cache);
}

-(int) diePositionForLocation: (CGPoint) loc
{
	float fwidth= self.frame.size.width;
	int col = loc.x / (fwidth/dicePerRow);
	int row = loc.y / rowHeight;
	int pos = row*dicePerRow + col;
	return (pos);
}

-(void) onChangeNotification: (NSNotification*) note
{
	[self updateDice];
	[self updateSelection];
}
@end

static NSMutableDictionary* globalImageCache = nil;
static int globalImageCacheRefCount = 0;

@implementation SPDiceView

- (id)initWithFrame:(CGRect)frame
{
	self = [super initWithFrame:frame];
	if (self) {
		if (globalImageCacheRefCount == 0) {
			globalImageCache = [[NSMutableDictionary alloc] init];
		}
		imageCache = globalImageCache;
		globalImageCacheRefCount++;
		dice = nil;
		dieImageCache = [[NSMutableArray alloc] init];
		imageViews = [[NSMutableArray alloc] init];
		selectedViews = [[NSMutableArray alloc] init];
		dicePerRow = 1;
		rowHeight = 20;
		touchBeganPos = 0;
		selectionEnabled = NO;
	}
	return self;
}

- (void)dealloc
{
	imageCache = nil;
	globalImageCacheRefCount--;
	if (globalImageCacheRefCount == 0) {
		[globalImageCache release];
	}
	[dice release];
	[dieImageCache release];
	[super dealloc];
}

-(SPSelectableDice*) dice
{
	return dice;
}

-(void) setDice: (SPSelectableDice*) dice_
{
	NSNotificationCenter* noteCenter = [NSNotificationCenter defaultCenter];
	[dice_ retain];
	SPSelectableDice* oldDice = dice;
	dice = dice_;
	[self updateDice];
	[self updateSelection];
	[noteCenter removeObserver: self name: @"added" object: oldDice];
	[noteCenter removeObserver: self name: @"removed" object: oldDice];
	[noteCenter removeObserver: self name: @"replaced" object: oldDice];
	[noteCenter removeObserver: self name: @"selectedChanged"
			    object: oldDice];
	[oldDice release];
	[noteCenter addObserver: self
		       selector: @selector(onChangeNotification:)
			   name: @"added"
			 object: dice];
	[noteCenter addObserver: self
		       selector: @selector(onChangeNotification:)
			   name: @"removed"
			 object: dice];
	[noteCenter addObserver: self
		       selector: @selector(onChangeNotification:)
			   name: @"replaced"
			 object: dice];
	[noteCenter addObserver: self
		       selector: @selector(onChangeNotification:)
			   name: @"selectedChanged"
			 object: dice];
}

-(int) dicePerRow
{
	return (dicePerRow);
}

-(void) setDicePerRow: (int) dpr
{
	dicePerRow = dpr;
	[self updateDice];
}

-(float) rowHeight
{
	return (rowHeight);
}

-(void) setRowHeight: (float) rowHeight_
{
	rowHeight = rowHeight_;
	[self updateDice];
}

-(BOOL) selectionEnabled
{
	return selectionEnabled;
}

-(void) setSelectionEnabled: (BOOL) selectionEnabled_
{
	selectionEnabled = selectionEnabled_;
	[self updateDice];
}

/*
// Only override drawRect: if you perform custom drawing.
// An empty implementation adversely affects performance during animation.
- (void)drawRect:(CGRect)rect
{
	// Drawing code
}
*/

-(void) touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
	UITouch* touch = [touches anyObject];
	CGPoint loc = [touch locationInView: self];
	int pos = [self diePositionForLocation: loc];
	touchBeganPos = pos;
}

-(void) touchesEnded:(NSSet *)touches withEvent:(UIEvent *)event
{
	UITouch* touch = [touches anyObject];
	CGPoint loc = [touch locationInView: self];
	int pos = [self diePositionForLocation: loc];
	if (pos == touchBeganPos && pos < [dice count]) {
		[dice setSelected: ![dice getSelectedAtIndex:pos] atIndex:pos];
		[self updateSelection];
		NSNumber* posnumber = [NSNumber numberWithInt: pos];
		[touchUpInsideTarget performSelector: touchUpInsideAction
					  withObject: self
					  withObject: posnumber];
	}
}

-(void) setTouchUpInsideDieTarget: (id) target
			andAction: (SEL) action
{
	touchUpInsideTarget = target;
	touchUpInsideAction = action;
}

@end
