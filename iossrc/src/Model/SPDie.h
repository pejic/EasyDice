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
//  SPDie.h
//  viewimagetouchtest
//
//  Created by Slobodan Pejic on 11-10-19.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>

/**
 * Description of a single die.
 */
@interface SPDie : NSObject {
	@private
	int dieSize;
	int numFacing;
	int multiplier;
	int valueOffset;
}

-(id) initWithSize: (int) size
     andFacingSide: (int) facing;

+(id) dieWithSize: (int) size
    andFacingSide: (int) facing;

-(id) initWithSize: (int) size
     andFacingSide: (int) facing
     andMultiplier: (int) multiplier
    andValueOffset: (int) offset;

+(id) dieWithSize: (int) size
    andFacingSide: (int) facing
    andMultiplier: (int) multiplier
   andValueOffset: (int) offset;

-(id) initWithRollDie: (SPDie*) die;

+(id) dieWithRollDie: (SPDie*) die;

-(int) dieSize;
-(int) numFacing;
-(int) value;

-(NSString*) toString;

@end
