/*
 * Easy Dice - An application for rolling dice of your choosing.
 * Copyright (C) 2011-2014 Slobodan Pejic (slobo@pejici.net)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
