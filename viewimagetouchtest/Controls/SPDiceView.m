//
//  MyClass.m
//  viewimagetouchtest
//
//  Created by Slobodan Pejic on 11-10-19.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SPDiceView.h"

@interface ImageCache : NSObject {
	@private
	int refcount;
	UIImage* image;
}

-(id) initWithImage: (UIImage*) image;

-(UIImage*) image;

-(UIImage*) imageRetain;

-(void) imageRelease;

@end

@implementation ImageCache

-(id) initWithImage: (UIImage*) image_
{
	if (![super init]) {
		return nil;
	}
	image = image_;
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
	if ([other isMemberOfClass: [ImageCache class]]) {
		ImageCache* o = other;
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


@implementation SPDiceView

- (id)initWithFrame:(CGRect)frame
{
	self = [super initWithFrame:frame];
	if (self) {
		imageCache = [[NSMutableDictionary alloc] init];
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
	[dice release];
	dice = dice_;
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
	[imageCache release];
	[dice release];
	[super dealloc];
}

@end
