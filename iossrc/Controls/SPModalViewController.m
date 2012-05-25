//
//  SPModalViewController.m
//  Easy Dice
//
//  Created by Slobodan Pejic on 12-05-23.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "SPModalViewController.h"

@implementation SPModalViewController

@synthesize navBar = _navBar;
@synthesize modalView = _modalView;
@synthesize delegate = _delegate;

- (void)dealloc
{
	self.navBar = nil;
	self.modalView = nil;
	self.delegate = nil;
	[super dealloc];
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
	self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
	if (self) {
		// Custom initialization
	}
	return self;
}

- (void)didReceiveMemoryWarning
{
	// Releases the view if it doesn't have a superview.
	[super didReceiveMemoryWarning];
	
	// Release any cached data, images, etc that aren't in use.
}

#pragma mark - signal

- (void)onDone:(id)sender
{
	[self.delegate onModalDone:self];
}

#pragma mark - View lifecycle

// Implement loadView to create a view hierarchy programmatically, without using a nib.
- (void)loadView
{
	self.view = [[[UIView alloc] init] autorelease];
	self.view.frame = CGRectMake(0, 0, 320, 460);
	self.navBar = [[[UINavigationBar alloc]
			initWithFrame:CGRectMake(0, 0, 320, 44)] autorelease];
	self.navBar.barStyle = UIBarStyleBlackTranslucent;
	UINavigationItem *item = [[UINavigationItem alloc] init];
	[self.navBar pushNavigationItem:item
			       animated:NO];
	[item release];
	item = nil;
	[self.view addSubview:self.navBar];
}

// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad
{
	[super viewDidLoad];
	UIBarButtonItem *done =
		[[UIBarButtonItem alloc] initWithTitle:@"Done"
						 style:UIBarButtonItemStyleDone
						target:self
						action:@selector(onDone:)];
	self.navBar.topItem.rightBarButtonItem = done;
	self.navBar.topItem.title = self.title;
	[done release];
	done = nil;
    
	[self.view addSubview:self.modalView];
	self.modalView.frame = CGRectMake(0, 44, 320, 416);
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

@end
