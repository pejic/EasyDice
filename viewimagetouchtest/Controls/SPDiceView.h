//
//  MyClass.h
//  viewimagetouchtest
//
//  Created by Slobodan Pejic on 11-10-19.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "../Model/SPDie.h"
#import "../Model/SPSelectableDice.h"

@interface SPDiceView : UIView {
	@private
	/** Keeps references to dice images that can be reused. */
	NSMutableDictionary* imageCache;

	/** Array of SPDie objects indicating which are to be displayed. */
	SPSelectableDice* dice;

	/** Mapping from die index to imageCache */
	NSMutableArray* dieImageCache;

	/** Array of UIImageView objects.  These views are used to display the
	 * die Images. */
	NSMutableArray* imageViews;

	/** Array of UIImageView objects.  These views are used to display the
	 * red highlight behind the dice. */
	NSMutableArray* selectedViews;

	/** Number of dice to display per row. */
	int dicePerRow;

	/** Height of each row of dice. */
	float rowHeight;

	/** Marks where a touch began. */
	int touchBeganPos;
}

@property (nonatomic, retain) SPSelectableDice* dice;
@property (nonatomic) int dicePerRow;
@property (nonatomic) float rowHeight;

@end
