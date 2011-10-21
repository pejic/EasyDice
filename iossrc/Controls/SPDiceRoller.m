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
	SPSelectableDice* dice = rollingView.dice;
	[dice rollSelected];
}

@end


@implementation SPDiceRoller

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
		roll = [[UIButton buttonWithType: UIButtonTypeRoundedRect]
				retain];;
		[self addSubview: roll];
		[roll addTarget: self
			 action: @selector(onRollTouchedUpInside:)
	       forControlEvents: UIControlEventTouchUpInside];
		[roll setTitle: @"Roll"
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
	CGRect rbframe = frame;
	rbframe.origin.x = frame.size.width - 100;
	rbframe.origin.y = aframe.origin.y - 32;
	rbframe.size.width = 100;
	rbframe.size.height = 32;
	
	rollingView.frame = rframe;
	availableView.frame = aframe;
	roll.frame = rbframe;
	
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
