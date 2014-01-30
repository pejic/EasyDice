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
//  MyClass.h
//  viewimagetouchtest
//
//  Created by Slobodan Pejic on 11-10-19.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "../Model/SPDie.h"
#import "../Model/SPSelectableDice.h"

@interface SPDiceView : UIView {
	@private
	/** Keeps references to dice images that can be reused. */
	NSMutableDictionary* imageCache;

	/** Array of SPDie objects indicating which are to be displayed. */
	SPSelectableDice* dice;

	/** Mapping from die index to imageCache */
	NSMutableArray* dieImageCache;

	/** Array of UIImageView objects.  These views are used to display the
	 * die Images. */
	NSMutableArray* imageViews;

	/** Array of UIImageView objects.  These views are used to display the
	 * red highlight behind the dice. */
	NSMutableArray* selectedViews;

	/** Number of dice to display per row. */
	int dicePerRow;

	/** Height of each row of dice. */
	float rowHeight;

	/** Marks where a touch began. */
	int touchBeganPos;

	/** Whether dice can be selected. */
	BOOL selectionEnabled;

	/** Target for touch up inside die. */
	id touchUpInsideTarget;

	/** Action on touch up inside target. */
	SEL touchUpInsideAction;
}

@property (nonatomic, retain) SPSelectableDice* dice;
@property (nonatomic) int dicePerRow;
@property (nonatomic) float rowHeight;
@property (nonatomic) BOOL selectionEnabled;

/**
 * Adds an event listener for touch up inside on dice.  The event is triggered
 * when the user touches down and up in the same die.
 *
 * \param target The target object that the action is called on.
 * \param action A method that takes an SPDiceView* (the sender) and an
 *	NSNumber (the index of the die that was pushed). E.g.
 *	[target onTouchUpInsideDie: (SPDiceView*) sender
 *			     index: (NSNumber) dieIndex];
 */
-(void) setTouchUpInsideDieTarget: (id) target
			andAction: (SEL) action;

@end
