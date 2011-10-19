//
//  MyClass.h
//  viewimagetouchtest
//
//  Created by Slobodan Pejic on 11-10-19.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "../Model/SPDie.h"

@interface SPDiceView : UIView {
	@private
	/** Keeps references to dice images that can be reused. */
	NSMutableDictionary* imageCache;

	/** Array of SPDie objects indicating which are to be displayed. */
	NSArray* dice;

	/** Array of UIImageView objects.  These views are used to display the
	 * die Images. */
	NSMutableArray* imageViews;

	/** Number of dice to display per row. */
	int dicePerRow;

	/** Height of each row of dice. */
	float rowHeight;
}

@property (nonatomic, retain) NSArray* dice;
@property (nonatomic) int dicePerRow;
@property (nonatomic) float rowHeight;

@end
