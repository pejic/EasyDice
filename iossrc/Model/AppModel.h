//
//  AppModel.h
//  Easy Dice
//
//  Created by Slobodan Pejic on 11-10-20.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <Foundation/Foundation.h>
#import "SPSelectableDice.h"

@interface AppModel : NSObject {
	@private
	SPSelectableDice* dice;
	SPSelectableDice* availableDice;
}

+(AppModel*) sharedAppModel;

-(id) init;

-(SPSelectableDice*) dice;

-(SPSelectableDice*) availableDice;

@end
