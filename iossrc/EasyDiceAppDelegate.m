//
//  Easy_DiceAppDelegate.m
//  Easy Dice
//
//  Created by Slobodan Pejic on 11-10-20.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "EasyDiceAppDelegate.h"
#import "Controls/SPDiceRoller.h"
#import "Controls/SPDiceCredits.h"
#import "Controls/HidingNavigationController.h"
#import "Model/AppModel.h"
#import "iOS6View.h"

@implementation EasyDiceAppDelegate


@synthesize window=_window;

- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
	// Override point for customization after application launch.
	srandom((int)time(NULL));
	UIScreen* screen = [UIScreen mainScreen];
	CGRect scBounds = screen.bounds;
	UIWindow *window = [[UIWindow alloc] initWithFrame: scBounds];
	self.window = window;
	[self.window setBackgroundColor: [UIColor blackColor]];
	background =[[UIImageView alloc] initWithImage:
		     [UIImage imageNamed: @"assets/background.png"]];
	background.frame = scBounds;
	[self.window addSubview: background];
	[self.window makeKeyAndVisible];

	CGRect viewFrame = scBounds;
	viewFrame.origin.y = 20;
	viewFrame.size.height -= 20;

	SPDiceRoller* diceView = [[SPDiceRoller alloc]
				  initWithFrame: scBounds];
	[diceView setDieDimRolling: CGSizeMake(48, 48)];
	[diceView setDieDimAvailable: CGSizeMake(40, 48)];

	SPDiceCredits* creditsView = [[SPDiceCredits alloc]
				      initWithFrame: viewFrame];
	credits = [[UIViewController alloc] init];
	credits.title = @"Help and Credits";
	credits.view = creditsView;
	[credits viewDidLoad];

	AppModel* model = [AppModel sharedAppModel];

	diceView.rollingDice = model.dice;
	diceView.availableDice = model.availableDice;
	diceView.helpDelegate = self;
	UIView *rootView = [[[iOS6View alloc] initWithView:diceView] autorelease];

	dice = [[UIViewController alloc] init];
	dice.view = rootView;
	[dice viewDidLoad];

	[self.window addSubview:rootView];
	[self.window setRootViewController: dice];

	[diceView release];
	[creditsView release];
	[window release];

	return YES;
}

- (void)onModalDone:(SPModalViewController *)sender
{
	[dice dismissViewControllerAnimated:YES
	                         completion:nil];
}

- (IBAction) onHelp: (id) sender
{
	SPModalViewController *vc = [[SPModalViewController alloc] init];
	[vc loadView];
	vc.modalView = credits.view;
	vc.title = credits.title;
	vc.delegate = self;
	[vc viewDidLoad];
	[dice presentViewController:vc
	                   animated:YES
	                 completion:nil];
	[vc release];
}

- (void)applicationWillResignActive:(UIApplication *)application
{
	/*
	 Sent when the application is about to move from active to inactive state. This can occur for certain types of temporary interruptions (such as an incoming phone call or SMS message) or when the user quits the application and it begins the transition to the background state.
	 Use this method to pause ongoing tasks, disable timers, and throttle down OpenGL ES frame rates. Games should use this method to pause the game.
	 */
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
	/*
	 Use this method to release shared resources, save user data, invalidate timers, and store enough application state information to restore your application to its current state in case it is terminated later. 
	 If your application supports background execution, this method is called instead of applicationWillTerminate: when the user quits.
	 */
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
	/*
	 Called as part of the transition from the background to the inactive state; here you can undo many of the changes made on entering the background.
	 */
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
	/*
	 Restart any tasks that were paused (or not yet started) while the application was inactive. If the application was previously in the background, optionally refresh the user interface.
	 */
}

- (void)applicationWillTerminate:(UIApplication *)application
{
	/*
	 Called when the application is about to terminate.
	 Save data if appropriate.
	 See also applicationDidEnterBackground:.
	 */
}

- (void)dealloc
{
	[background release];
	[dice release];
	[credits release];
	[_window release];
	[super dealloc];
}

@end
