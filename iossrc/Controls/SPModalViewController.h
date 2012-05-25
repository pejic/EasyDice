//
//  SPModalViewController.h
//  Easy Dice
//
//  Created by Slobodan Pejic on 12-05-23.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>

@class SPModalViewController;

@protocol SPModalViewControllerDelegate <NSObject>

-(void)onModalDone:(SPModalViewController *)sender;

@end

@interface SPModalViewController : UIViewController

/**
 * The navbar with the done button.
 */
@property (nonatomic, retain) UINavigationBar *navBar;

/**
 * The view that is to be surrounded by a modal border.
 */
@property (nonatomic, retain) UIView *modalView;

/**
 * The delegate that receives the done signal.
 */
@property (nonatomic, retain) id<SPModalViewControllerDelegate> delegate;

@end
