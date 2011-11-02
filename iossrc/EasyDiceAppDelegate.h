//
//  Easy_DiceAppDelegate.h
//  Easy Dice
//
//  Created by Slobodan Pejic on 11-10-20.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
@class SPBannerContainer;

@interface EasyDiceAppDelegate : NSObject <UIApplicationDelegate> {
	SPBannerContainer* bannerContainer;
	UIViewController* credits;
	UIView* rootView;
	UIImageView* background;
	UINavigationController* navController;

}

@property (nonatomic, retain) IBOutlet UIWindow *window;

- (IBAction) onHelp: (id) sender;

@end
