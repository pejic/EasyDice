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
 * Sends notifications "added", "removed", "replaced", "reset",
 * "selectedChanged".
 */
@interface SPSelectableDice : NSObject {
	@private
	NSMutableArray* dice;
	NSMutableArray* selected;
}

-(id) init;
-(int) count;
-(void) addDie: (SPDie*) die;
-(void) addDie: (SPDie*) die
      selected: (BOOL) selected;
-(void) removeDieAtIndex: (int) i;
-(void) setSelected: (BOOL) selected
	    atIndex: (int) i;
-(SPDie*) getDieAtIndex: (int) i;
-(BOOL) getSelectedAtIndex: (int) i;
-(void) setDie: (SPDie*) die
       atIndex: (int) i;
-(void) rollSelected;
-(void) removeUnselected;
-(int) sumSelected;
-(int) sum;
-(void) removeAllDice;

@end
