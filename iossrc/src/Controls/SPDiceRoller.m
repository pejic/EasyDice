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
//  SPDiceRoller.m
//  Easy Dice
//
//  Created by Slobodan Pejic on 11-10-20.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SPDiceRoller.h"

@interface SPDiceRoller (Private)

-(void) onAvailableDiePushed: (id) sender
			 die: (NSNumber*) pos;
-(void) onRollTouchedUpInside: (id) sender;
-(void) onRemoveTouchedUpInside: (id) sender;
-(void) onHelpTouchedUpInside: (id) sender;
-(void) updateMetaData;
-(void) skinButton: (UIButton*) button;

@end

@implementation SPDiceRoller (Private)

-(void) onAvailableDiePushed: (id) sender
			 die: (NSNumber*) pos
{
	SPSelectableDice* rdice = rollingView.dice;
	SPSelectableDice* adice = availableView.dice;
	[rdice addDie: [adice getDieAtIndex: [pos intValue]]];
}

-(void) onRollTouchedUpInside: (id) sender
{
	[rollingView.dice rollSelected];
	[self updateMetaData];
}

-(void) onRemoveTouchedUpInside: (id) sender
{
	[rollingView.dice removeAllDice];
}

-(void) onHelpTouchedUpInside: (id) sender
{
	[helpDelegate performSelector: @selector(onHelp:) withObject: self];
}

-(void) updateMetaData
{
	metaData.text = [NSString stringWithFormat: @"Sum: %d",
			 [rollingView.dice sum]];
}

-(void) skinButton: (UIButton*) button
{
	[button setTitleColor: [UIColor whiteColor]
		     forState: UIControlStateNormal];
	[button setBackgroundImage:
		[UIImage imageNamed: @"assets/buttonNormal.png"]
			  forState: UIControlStateNormal];
	[button setBackgroundImage:
		[UIImage imageNamed: @"assets/buttonHighlighted.png"]
			  forState: UIControlStateHighlighted];
	[button setAdjustsImageWhenHighlighted: NO];
}

@end


@implementation SPDiceRoller

@synthesize  helpDelegate;

- (id)initWithFrame:(CGRect)frame
{
	self = [super initWithFrame:frame];
	if (self) {
		rollingView = [[SPDiceView alloc] initWithFrame: frame];
		[self addSubview: rollingView];
		availableView = [[SPDiceView alloc] initWithFrame: frame];
		[self addSubview: availableView];
		[availableView setTouchUpInsideDieTarget: self
					       andAction:
				@selector(onAvailableDiePushed:die:)];
		rollingView.selectionEnabled = YES;
		
		roll = [[UIButton buttonWithType: UIButtonTypeCustom]
				retain];
		[self addSubview: roll];
		[roll addTarget: self
			 action: @selector(onRollTouchedUpInside:)
	       forControlEvents: UIControlEventTouchUpInside];
		[roll setTitle: @"Roll"
		      forState: UIControlStateNormal];
		[self skinButton: roll];
		
		remove = [[UIButton buttonWithType: UIButtonTypeCustom]
				retain];
		[self addSubview: remove];
		[remove addTarget: self
			   action: @selector(onRemoveTouchedUpInside:)
		 forControlEvents: UIControlEventTouchUpInside];
		[remove setTitle: @"Reset"
			forState: UIControlStateNormal];
		[self skinButton: remove];
		
		metaData = [[UITextField alloc] initWithFrame: frame];
		[self addSubview: metaData];
		[metaData setTextColor: [UIColor whiteColor]];
		[metaData setTextAlignment: UITextAlignmentLeft];
		[metaData setEnabled: NO];

		help = [[UIButton buttonWithType: UIButtonTypeCustom]
			retain];
		[self addSubview: help];
		[help addTarget: self
			 action: @selector(onHelpTouchedUpInside:)
	       forControlEvents: UIControlEventTouchUpInside];
		[help setTitle: @""
		      forState: UIControlStateNormal];
		[help setBackgroundImage:
		 [UIImage imageNamed: @"assets/questionMark.png"]
			    forState: UIControlStateNormal];
	}
	return self;
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
	[rollingView release];
	[availableView release];
	[roll release];
	[super dealloc];
}

-(void) setFrame: (CGRect) rect
{
	[super setFrame: rect];
	[self layoutSubviews];
}

-(void) setBounds: (CGRect) rect
{
	[super setBounds: rect];
	[self layoutSubviews];
}

-(void) layoutSubviews
{
	static const int MARGIN = 15;
	static const int MARGIN_TOP = -5;
	static const int buttonHeight = 48;
	static const int buttonWidth = 100;
	static const int hWidth = 30;
	static const int hHeight = 30;
	static const int hMarginHoriz = 5;
	static const int hMarginVert = 0;
	int rowHeight = dieDimAvailable.height + 4;
	static const int statusBarHeight = 24;
	static const int statusMargin = 3;
	CGRect frame = self.frame;
	int height = frame.size.height;
	int width = frame.size.width;
	CGRect rframe = CGRectMake(MARGIN,
				   MARGIN + MARGIN_TOP,
				   width
					- MARGIN * 2,
				   height
					- rowHeight
					- statusBarHeight
					- buttonHeight
					- MARGIN*2
					- MARGIN_TOP);
	CGRect aframe = CGRectMake(MARGIN,
				   height
					- statusBarHeight
					- rowHeight
					- MARGIN,
				   width
					- MARGIN * 2,
				   rowHeight);
	CGRect rbframe = CGRectMake(
				    width - buttonWidth - MARGIN,
				    height
					- statusBarHeight
					- rowHeight
					- buttonHeight
					- MARGIN,
				    buttonWidth,
				    buttonHeight);
	CGRect remframe = CGRectMake(
				     MARGIN,
				     height
					- statusBarHeight
					- rowHeight
					- buttonHeight
					- MARGIN,
				     buttonWidth,
				     buttonHeight);
	CGRect mdframe = CGRectMake(
				    MARGIN,
				    height
					- statusBarHeight
					+ statusMargin
					- MARGIN,
				    width
					- hWidth
					- MARGIN * 2,
				    statusBarHeight
					- statusMargin);
	CGRect hframe = CGRectMake(width
					- hWidth
					- hMarginHoriz
					- MARGIN,
				   height
					- statusBarHeight
					+ hMarginVert
					- MARGIN,
				   hWidth,
				   hHeight);
	
	rollingView.frame = rframe;
	availableView.frame = aframe;
	roll.frame = rbframe;
	remove.frame = remframe;
	metaData.frame = mdframe;
	help.frame = hframe;
	
	int numPerRowRoll = (frame.size.width - MARGIN * 2)
		/ dieDimRolling.width;
	int numPerRowAvail = (frame.size.width - MARGIN * 2)
		/ dieDimAvailable.width;
	rollingView.dicePerRow = numPerRowRoll;
	availableView.dicePerRow = numPerRowAvail;
	
	rollingView.rowHeight = rowHeight;
	availableView.rowHeight = rowHeight;
}

-(SPSelectableDice*) rollingDice;
{
	return rollingView.dice;
}

-(void) setRollingDice: (SPSelectableDice*) dice
{
	rollingView.dice = dice;
}

-(SPSelectableDice*) availableDice;
{
	return availableView.dice;
}

-(void) setAvailableDice: (SPSelectableDice*) dice
{
	availableView.dice = dice;
}

-(CGSize) dieDimRolling
{
	return dieDimRolling;
}

-(void) setDieDimRolling: (CGSize) size
{
	dieDimRolling = size;
	[self layoutSubviews];
}

-(CGSize) dieDimAvailable
{
	return dieDimAvailable;
}

-(void) setDieDimAvailable: (CGSize) size
{
	dieDimAvailable = size;
	[self layoutSubviews];
}

@end
