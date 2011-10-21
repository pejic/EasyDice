//
//  SPDiceRoller.m
//  Easy Dice
//
//  Created by Slobodan Pejic on 11-10-20.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SPDiceRoller.h"


@implementation SPDiceRoller

- (id)initWithFrame:(CGRect)frame
{
	self = [super initWithFrame:frame];
	if (self) {
		rollingView = [[SPDiceView alloc] initWithFrame: frame];
		[self addSubview: rollingView];
		availableView = [[SPDiceView alloc] initWithFrame: frame];
		[self addSubview: availableView];
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
	[super dealloc];
}

-(void) layoutSubviews
{
	int rowHeight = dieDim.height + 2;
	CGRect frame = self.frame;
	CGRect rframe = frame;
	rframe.origin.x = 0;
	rframe.origin.y = 0;
	rframe.size.height -= rowHeight;
	CGRect aframe = frame;
	aframe.origin.x = 0;
	aframe.origin.y = rframe.size.height;
	aframe.size.height = rowHeight;
	rollingView.frame = rframe;
	availableView.frame = aframe;
	
	int numPerRow = frame.size.width / dieDim.width;
	rollingView.dicePerRow = numPerRow;
	availableView.dicePerRow = numPerRow;
	
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

-(CGSize) dieDim
{
	return dieDim;
}

-(void) setDieDim: (CGSize) size
{
	dieDim = size;
	[self layoutSubviews];
}

@end
