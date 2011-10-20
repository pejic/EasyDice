//
//  viewimagetouchtestViewController.h
//  viewimagetouchtest
//
//  Created by Slobodan Pejic on 11-10-19.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "../Controls/SPDiceView.h"

@interface viewimagetouchtestViewController : UIViewController {
	@private
	SPDiceView* diceView;
	SPSelectableDice* dice;
}

@property (nonatomic, retain) IBOutlet UITextField* textout;

@end
