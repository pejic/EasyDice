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
				retain];;
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
		[metaData setTextAlignment: UITextAlignmentCenter];
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
	int rowHeight = dieDimAvailable.height + 2;
	CGRect frame = self.frame;
	CGRect rframe = frame;
	rframe.origin.x = 0;
	rframe.origin.y = 0;
	rframe.size.height -= rowHeight;
	CGRect aframe = frame;
	aframe.origin.x = 0;
	aframe.origin.y = rframe.size.height;
	aframe.size.height = rowHeight;
	static const int buttonHeight = 48;
	static const int buttonWidth = 100;
	CGRect rbframe = CGRectMake(
				    frame.size.width - buttonWidth,
				    aframe.origin.y - buttonHeight,
				    buttonWidth,
				    buttonHeight);
	CGRect remframe = CGRectMake(
				     0,
				     rbframe.origin.y,
				     buttonWidth,
				     buttonHeight);
	static const int mdvmargin = 48/2 - 12;
	static const int mdhmargin = 3;
	CGRect mdframe = CGRectMake(
				    remframe.origin.x + remframe.size.width
				      + mdhmargin,
				    remframe.origin.y + mdvmargin,
				    rbframe.origin.x
				      -(remframe.origin.x + remframe.size.width)
				      - mdhmargin * 2,
				    buttonHeight - mdvmargin * 2);
	
	rollingView.frame = rframe;
	availableView.frame = aframe;
	roll.frame = rbframe;
	remove.frame = remframe;
	metaData.frame = mdframe;
	
	int numPerRowRoll = frame.size.width / dieDimRolling.width;
	int numPerRowAvail = frame.size.width / dieDimAvailable.width;
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
