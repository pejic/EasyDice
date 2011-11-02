//
//  HidingNavigationController.m
//  src
//
//  Created by Slobodan Pejic on 11/05/10.
//  Copyright 2010 __MyCompanyName__. All rights reserved.
//

#import "HidingNavigationController.h"
#import <QuartzCore/QuartzCore.h>

#define ALWAYS_HIDE 0

@interface UINavigationController (Animations)
/* from stackoverflow:
 * http://stackoverflow.com/questions/3332383/animating-uinavigationcontrollers-back-button
 */
-(UIViewController*) altAnimatePopViewControllerAnimated: (BOOL) animated;
@end

@implementation UINavigationController (Animations)
/* from stackoverflow:
 * http://stackoverflow.com/questions/3332383/animating-uinavigationcontrollers-back-button
 */
-(UIViewController*) altAnimatePopViewControllerAnimated: (BOOL) animated
{
	[CATransaction begin];

	CATransition *transition;
	transition = [CATransition animation];
	transition.type = kCATransitionPush;
		// Use any animation type and subtype you like
	transition.subtype = kCATransitionFromTop;
	transition.duration = 0.3;

	CATransition *fadeTrans = [CATransition animation];
	fadeTrans.type = kCATransitionFade;
	fadeTrans.duration = 0.3;


	[CATransaction setValue:(id)kCFBooleanTrue
			 forKey:kCATransactionDisableActions];

	[[[[self.view subviews] objectAtIndex:0] layer]
				 addAnimation:transition forKey:nil];
	[[[[self.view subviews] objectAtIndex:1] layer]
				 addAnimation:fadeTrans forKey:nil];


	UIViewController* uivc =
		[self popViewControllerAnimated: YES];
	[CATransaction commit];
	return(uivc);
}
@end

@interface HidingNavigationController (Private)

-(void) updateHiding;

@end

@implementation HidingNavigationController (Private)

-(void) updateHiding
{
	NSArray* vcs = self.viewControllers;
	int hidden = ([vcs count] <= 1);
	if (!ALWAYS_HIDE) {
		[self setNavigationBarHidden: hidden
				    animated: 1];
	}
	else {
		[self setNavigationBarHidden: 1
				    animated: 1];
	}
}

@end


@implementation HidingNavigationController

-(void) pushViewController: (UIViewController*) vc
		  animated: (BOOL) animated
{
	[super pushViewController: vc
			 animated: animated];
	[self updateHiding];
}

-(UIViewController*) popViewControllerAnimated: (BOOL) animated
{
	UIViewController* uivc = [super popViewControllerAnimated: animated];
	[self updateHiding];
	return (uivc);
}

-(NSArray* ) popToRootViewControllerAnimated: (BOOL) animated
{
	NSArray* ar = [super popToRootViewControllerAnimated: animated];
	[self updateHiding];
	return (ar);
}

-(NSArray*) popToViewController: (UIViewController*) vc
		       animated: (BOOL) animated
{
	NSArray* ar = [super popToViewController: vc
					animated: animated];
	[self updateHiding];
	return (ar);
}

-(void) dealloc {
	[super dealloc];
}

@end
