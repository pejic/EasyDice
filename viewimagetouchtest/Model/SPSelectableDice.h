//
//  SPSelectableDice.h
//  viewimagetouchtest
//
//  Created by Slobodan Pejic on 11-10-19.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SPDie.h"

/** \brief Keeps track of an array of dice and their selection.
 *
 * Sends notifications "added", "removed", "selectedChanged".
 */
@interface SPSelectableDice : NSObject {
	@private
	NSMutableArray* dice;
	NSMutableArray* selected;
}

-(id) init;
-(int) count;
-(void) addDie: (SPDie*) die;
-(void) removeDieAtIndex: (int) i;
-(void) setSelected: (BOOL) selected
	    atIndex: (int) i;
-(SPDie*) getDieAtIndex: (int) i;
-(BOOL) getSelectedAtIndex: (int) i;

@end
