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
}

-(id) initWithImage: (UIImage*) image;

-(UIImage*) image;

-(UIImage*) imageRetain;

-(void) imageRelease;

@end

@implementation CacheImage

-(id) initWithImage: (UIImage*) image_
{
	if (![super init]) {
		return nil;
	}
	image = [image_ retain];
	refcount = 0;
	return self;
}

-(void) dealloc
{
	[image release];
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

-(UIImage*) imageRetain
{
	refcount++;
	return (image);
}

-(void) imageRelease
{
	refcount--;
}
	
@end


@interface SPDiceView (Private)
-(void) updateDice: (NSArray*) oldDice;

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
		CacheImage* oldcache = [self imageCacheForDie: oldDie];
		CacheImage* cache = [self imageCacheForDie: die];
		UIImage* img = [cache imageRetain];
		imgview.image = img;
		[oldcache imageRelease];
		imgview.bounds = CGRectMake(0, 0,
					    img.size.width, img.size.height);
		int row = pos % dicePerRow;
		int col = pos / dicePerRow;
		float width = self.bounds.size.width;
		imgview.center = CGPointMake(
					     (width/dicePerRow)*(row+0.5),
					     (rowHeight)*(col + 0.5));
	}
}

-(CacheImage*) imageCacheForDie: (SPDie*) die
{
	if (!die) {
		return nil;
	}
	NSString* path = [NSString stringWithFormat: @"assets/%@.png",
			  [die toString]];
	CacheImage* cache = [imageCache objectForKey: path];
	if (!cache) {
		cache = [[CacheImage alloc]
			 initWithImage: [UIImage imageNamed: path]];
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
	[self updateDice: oldDice];
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

- (void)dealloc
{
	imageCache = nil;
	globalImageCacheRefCount--;
	if (globalImageCacheRefCount == 0) {
		[globalImageCache release];
	}
	[dice release];
	[super dealloc];
}

@end
