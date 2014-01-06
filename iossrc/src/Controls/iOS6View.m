//
//  iOS6View.m
//  Easy Dice
//
//  Created by Slobodan Pejic on 1/3/2014.
//
//

#import "iOS6View.h"

@implementation iOS6View

- (id)initWithView:(UIView *)view
{
	self = [super initWithFrame:view.frame];
	if (self) {
		[self addSubview:view];
	}
	return self;
}

- (void)layoutSubviews {
	NSArray *versionCompatibility = [[UIDevice currentDevice].systemVersion componentsSeparatedByString:@"."];
	CGRect bounds = self.bounds;
	CGRect subFrame = bounds;
	if ([versionCompatibility[0] intValue] >= 7) {
		subFrame.origin.y += 20;
		subFrame.size.height -= 20;
	}
	for (UIView *view in self.subviews) {
		view.frame = subFrame;
	}
}

@end
