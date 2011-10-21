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
