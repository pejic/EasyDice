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
//  SPDiceRoller.h
//  Easy Dice
//
//  Created by Slobodan Pejic on 11-10-20.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "SPDiceView.h"
#import "../Model/SPSelectableDice.h"

@interface SPDiceRoller : UIView {
	/** Shows the dice that are being rolled. */
	SPDiceView* rollingView;
	/** Shows available dice. */
	SPDiceView* availableView;
	/** Dimensions of a die being rolled. */
	CGSize dieDimRolling;
	/** Dimensions of a die in the available list. */
	CGSize dieDimAvailable;
	/** Button for rolling dice. */
	UIButton* roll;
	/** Button for removing dice. */
	UIButton* remove;
	/** Shows metadata about the last roll. */
	UILabel* metaData;
	/** Help button. */
	UIButton* help;
	/** Help delegate. */
	id helpDelegate;
}

@property (nonatomic, retain) SPSelectableDice* rollingDice;
@property (nonatomic, retain) SPSelectableDice* availableDice;
@property (nonatomic) CGSize dieDimRolling;
@property (nonatomic) CGSize dieDimAvailable;
@property (nonatomic, assign) id helpDelegate;

@end
