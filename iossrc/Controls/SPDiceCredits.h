//
//  SPDiceCredits.h
//  Easy Dice
//
//  Created by Slobodan Pejic on 11-11-02.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>


@interface SPDiceCredits : UIView {
	/** Label for the credits title. */
	UITextField* creditsTitle;
	/** Label for the credits. */
	UITextView* credits;
	/** Label for the help title. */
	UITextField* helpTitle;
	/** TextView for the help. */
	UITextView* help;

}

@end
