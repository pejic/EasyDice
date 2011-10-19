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
-(void) updateDice: (NSArray*) oldDice;
-(void) updateSelection;
-(CacheImage*) imageCacheForDie: (SPDie*) die;
@end

@implementation SPDiceView (Private)
-(void) updateDice: (NSArray*) oldDice
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
		SPDie* oldDie = nil;
		if (pos < [oldDice count]) {
			oldDie = [oldDice objectAtIndex: pos];
		}
		SPDie* die = nil;
		if (pos < [dice count]) {
			die = [dice objectAtIndex: pos];
		}
		if ([oldDie isEqual: die]) {
			continue;
		}
		UIImageView* imgview = [imageViews objectAtIndex: pos];
		UIImageView* selview = [selectedViews objectAtIndex: pos];
		CacheImage* oldcache = [self imageCacheForDie: oldDie];
		CacheImage* cache = [self imageCacheForDie: die];
		[cache imageRetain];
		UIImage* img = [cache image];
		UIImage* sel = [cache selected];
		imgview.image = img;
		selview.image = sel;
		[oldcache imageRelease];
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
		if (pos < [selectedDice count]) {
			NSNumber* selected = [selectedDice objectAtIndex: pos];
			selview.hidden = ![selected boolValue];
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
		imageViews = [[NSMutableArray alloc] init];
		selectedViews = [[NSMutableArray alloc] init];
		selectedDice = [[NSMutableArray alloc] init];
	}
	return self;
}


-(NSArray*) dice
{
	return dice;
}

-(void) setDice: (NSArray*) dice_
{
	[dice_ retain];
	NSArray* oldDice = dice;
	dice = dice_;
	int dicecount = [dice count];
	id* carray = malloc(sizeof(id) * dicecount);
	int i;
	for (i = 0; i < dicecount; i++) {
		carray[i] = [NSNumber numberWithBool: 0];
	}
	[selectedDice release];
	selectedDice = [[NSMutableArray alloc] initWithObjects: carray
							 count: [dice count]];
	free(carray);
	[self updateDice: oldDice];
	[self updateSelection];
	[oldDice release];
}

-(int) dicePerRow
{
	return (dicePerRow);
}

-(void) setDicePerRow: (int) dpr
{
	dicePerRow = dpr;
	[self updateDice: dice];
}

-(float) rowHeight
{
	return (rowHeight);
}

-(void) setRowHeight:(float)rowHeight_
{
	rowHeight = rowHeight_;
	[self updateDice: dice];
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
	float fwidth= self.frame.size.width;
	int col = loc.x / (fwidth/dicePerRow);
	int row = loc.y / rowHeight;
	int pos = row*dicePerRow + col;
	if (pos < [selectedDice count]) {
		NSNumber* selected = [selectedDice objectAtIndex: pos];
		[selectedDice replaceObjectAtIndex: pos withObject:
			[NSNumber numberWithBool: ![selected boolValue]]];
		[self updateSelection];
	}
}

- (void)dealloc
{
	imageCache = nil;
	globalImageCacheRefCount--;
	if (globalImageCacheRefCount == 0) {
		[globalImageCache release];
	}
	[dice release];
	[selectedDice release];
	[super dealloc];
}

@end
