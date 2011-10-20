//
//  viewimagetouchtestViewController.m
//  viewimagetouchtest
//
//  Created by Slobodan Pejic on 11-10-19.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "viewimagetouchtestViewController.h"

@implementation viewimagetouchtestViewController

@synthesize textout;

- (void)dealloc
{
	[dice release];
	[diceView release];
	[textout release];
	[super dealloc];
}

- (void)didReceiveMemoryWarning
{
	// Releases the view if it doesn't have a superview.
	[super didReceiveMemoryWarning];

	// Release any cached data, images, etc that aren't in use.
}

#pragma mark - View lifecycle

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
	[super viewDidLoad];
	
	UIImageView* imgview = [[UIImageView alloc] initWithImage:
				[UIImage imageNamed: @"assets/d10-10.png"]];
	[self.view addSubview: imgview];
	CGPoint imgcenter = CGPointMake(160, 340);
	imgview.center = imgcenter;
	
	self.textout = [[UITextField alloc]
			initWithFrame: CGRectMake(0, 420, 320, 30)];
	[self.textout release];
	[self.textout setText: @"text"];
	[self.view addSubview: self.textout];
	
	dice = [[SPSelectableDice alloc] init];
	[dice addDie: [SPDie dieWithSize: 6 andFacingValue: 6]];

	diceView = [[SPDiceView alloc]
		    initWithFrame: CGRectMake(0, 0, 320, 240)];
	diceView.dicePerRow = 5;
	diceView.rowHeight = 42;
	diceView.dice = dice;
	[self.view addSubview: diceView];
}

- (void)viewDidUnload
{
	[super viewDidUnload];
	// Release any retained subviews of the main view.
	// e.g. self.myOutlet = nil;
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
	// Return YES for supported orientations
	return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

- (void)touchesBegan:(NSSet *)touches withEvent:(UIEvent *)event
{
	UITouch* touch = [touches anyObject];
	CGPoint loc = [touch locationInView: self.view];
	textout.text = [NSString stringWithFormat: @"(%f, %f)", loc.x, loc.y];

	static int testnum = 0;
	if (testnum == 0) {
		[dice addDie: [SPDie dieWithSize: 10 andFacingValue: 2]];
		[dice addDie: [SPDie dieWithSize: 10 andFacingValue: 4]];
		[dice addDie: [SPDie dieWithSize: 10 andFacingValue: 5]];
		[dice addDie: [SPDie dieWithSize: 10 andFacingValue: 1]];
	}
	else {
		[dice removeDieAtIndex: [dice count] - 1];
		[dice removeDieAtIndex: [dice count] - 1];
		[dice removeDieAtIndex: [dice count] - 1];
	}
	testnum = (testnum + 1) % 2;

}

@end
