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
	/** Dimensions of a die. */
	CGSize dieDim;
	/** Button for rolling dice. */
	UIButton* roll;
	/** Button for removing dice. */
	UIButton* remove;
	/** Shows metadata about the last roll. */
	UILabel* metaData;
}

@property (nonatomic, retain) SPSelectableDice* rollingDice;
@property (nonatomic, retain) SPSelectableDice* availableDice;
@property (nonatomic) CGSize dieDim;

@end
