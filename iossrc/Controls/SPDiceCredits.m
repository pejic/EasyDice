//
//  SPDiceCredits.m
//  Easy Dice
//
//  Created by Slobodan Pejic on 11-11-02.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SPDiceCredits.h"
#import <QuartzCore/QuartzCore.h>


@implementation SPDiceCredits

- (id)initWithFrame:(CGRect)frame
{
	self = [super initWithFrame:frame];
	if (self) {
		creditsTitle = [[UITextField alloc] init];
		credits = [[UITextView alloc] init];
		helpTitle = [[UITextField alloc] init];
		help = [[UITextView alloc] init];

		creditsTitle.textColor = [UIColor whiteColor];
		creditsTitle.enabled = NO;
		creditsTitle.textAlignment = UITextAlignmentCenter;
		credits.textColor = [UIColor whiteColor];
		credits.backgroundColor = [UIColor colorWithWhite: 0 alpha: 0.3];
		credits.font = [UIFont fontWithName: help.font.fontName size: 14];
		credits.editable = NO;
		credits.layer.cornerRadius = 12;
		helpTitle.textColor = [UIColor whiteColor];
		helpTitle.enabled = NO;
		helpTitle.textAlignment = UITextAlignmentCenter;
		help.textColor = [UIColor whiteColor];
		help.backgroundColor = [UIColor colorWithWhite: 0 alpha: 0.3];
		help.font = [UIFont fontWithName: help.font.fontName size: 14];
		help.editable = NO;
		help.layer.cornerRadius = 12;

		creditsTitle.text = @"Credits";
		credits.text =
			@"\u00A9 2011-2014 Slobodan Pejic\n";
		helpTitle.text = @"Help";
		help.text =
			@"1. To roll the dice at the top push the roll"
			" button.  Only dice with a red border will be"
			" rolled.\n"
			"2. To add different dice, push the dice at"
			" the bottom.\n"
			"3. To clear all dice, push the reset button.\n"
			"4. To change which dice are rolled, touch the"
			" dice at the top.   This will toggle the red"
			" border.\n"
		;
		background =[[UIImageView alloc] initWithImage:
			     [UIImage imageNamed: @"assets/background.png"]];
		CGRect bgFrame;
		bgFrame.origin = CGPointMake(0, 0);
		bgFrame.size = frame.size;
		background.frame = bgFrame;

		[self addSubview: background];
		[self addSubview: creditsTitle];
		[self addSubview: credits];
		[self addSubview: helpTitle];
		[self addSubview: help];
		self.backgroundColor = [UIColor darkGrayColor];
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

-(void) layoutSubviews
{
	CGRect frame = self.frame;
	CGFloat titleHeight = 20;
	CGFloat helpHeight = 200;
	CGFloat sectionMargin = 34;
	CGFloat creditsHeight = 36;
	CGFloat width = frame.size.width;
	CGFloat margin = 20;
	CGFloat titlePaddingBottom = 8;
	CGRect ctframe = CGRectMake(margin,
				    margin
					+ helpHeight
					+ sectionMargin
					+ titleHeight,
				    width
					- margin*2,
				    titleHeight);
	CGRect cframe = CGRectMake(margin,
				   margin
					+ helpHeight
					+ sectionMargin
					+ titleHeight
					+ titleHeight
					+ titlePaddingBottom,
				   width
					- margin*2,
				   creditsHeight);
	CGRect htframe = CGRectMake(margin,
				    margin,
				    width
					- margin*2,
				    titleHeight);
	CGRect hframe = CGRectMake(margin,
				   margin
				        + titleHeight
				        + titlePaddingBottom,
				   width
					- margin*2,
				   helpHeight);
	creditsTitle.frame = ctframe;
	credits.frame = cframe;
	helpTitle.frame = htframe;
	help.frame = hframe;
}

- (void)dealloc
{
	[creditsTitle release];
	[credits release];
	[helpTitle release];
	[help release];
	[background release];
	[super dealloc];
}

@end
