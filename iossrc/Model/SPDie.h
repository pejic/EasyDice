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
}


-(id) initWithSize: (int) size
    andFacingValue: (int) facing;

+(id) dieWithSize: (int) size
   andFacingValue: (int) facing;

-(int) dieSize;
-(int) numFacing;

-(NSString*) toString;

@end
