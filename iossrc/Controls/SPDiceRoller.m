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
	[rollingView.dice removeUnselected];
}
-(void) updateMetaData
{
	metaData.text = [NSString stringWithFormat: @"Sum: %d",
			 [rollingView.dice sum]];
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
		
		remove = [[UIButton buttonWithType: UIButtonTypeRoundedRect]
				retain];
		[self addSubview: remove];
		[remove addTarget: self
			   action: @selector(onRemoveTouchedUpInside:)
		 forControlEvents: UIControlEventTouchUpInside];
		[remove setTitle: @"Remove"
			forState: UIControlStateNormal];
		
		metaData = [[UITextField alloc] initWithFrame: frame];
		[self addSubview: metaData];
		[metaData setContentMode: UIViewContentModeCenter];
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
	CGRect rbframe = CGRectMake(
				    frame.size.width - 100,
				    aframe.origin.y - 32,
				    100,
				    32);
	CGRect remframe = CGRectMake(
				     0,
				     rbframe.origin.y,
				     100,
				     32);
	static const int mdvmargin = 6;
	static const int mdhmargin = 3;
	CGRect mdframe = CGRectMake(
				    remframe.origin.x + remframe.size.width
				      + mdhmargin,
				    remframe.origin.y + mdvmargin,
				    rbframe.origin.x
				      -(remframe.origin.x + remframe.size.width)
				      - mdhmargin * 2,
				    32 - mdvmargin * 2);
	
	rollingView.frame = rframe;
	availableView.frame = aframe;
	roll.frame = rbframe;
	remove.frame = remframe;
	metaData.frame = mdframe;
	
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
