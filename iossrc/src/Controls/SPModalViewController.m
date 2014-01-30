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
//  SPModalViewController.m
//  Easy Dice
//
//  Created by Slobodan Pejic on 12-05-23.
//  Copyright (c) 2012 __MyCompanyName__. All rights reserved.
//

#import "SPModalViewController.h"
#import "iOS6View.h"

@implementation SPModalViewController {
	UIView *view;
}

@synthesize navBar = _navBar;
@synthesize modalView = _modalView;
@synthesize delegate = _delegate;

- (void)dealloc
{
	[view release];
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
	view = [[[UIView alloc] init] autorelease];
	view.frame = CGRectMake(0, 0, 320, 460);
	self.view = [[[iOS6View alloc] initWithView:view] autorelease];
	self.navBar = [[[UINavigationBar alloc]
			initWithFrame:CGRectMake(0, 0, 320, 44)] autorelease];
	self.navBar.barStyle = UIBarStyleBlackTranslucent;
	UINavigationItem *item = [[UINavigationItem alloc] init];
	[self.navBar pushNavigationItem:item
			       animated:NO];
	[item release];
	item = nil;
	[view addSubview:self.navBar];
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
    
	[view addSubview:self.modalView];
	self.modalView.frame = CGRectMake(0, 44, 320, 416);
}

- (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation
{
	// Return YES for supported orientations
	return (interfaceOrientation == UIInterfaceOrientationPortrait);
}

@end
