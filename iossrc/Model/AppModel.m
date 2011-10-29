//
//  AppModel.m
//  Easy Dice
//
//  Created by Slobodan Pejic on 11-10-20.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "AppModel.h"

@implementation AppModel

+(AppModel*) sharedAppModel
{
	static AppModel* sharedAppModel = nil;
	if (!sharedAppModel) {
		sharedAppModel = [[AppModel alloc] init];
	}
	return (sharedAppModel);
}

-(id) init
{
	if (![super init]) {
		return nil;
	}
	dice = [[SPSelectableDice alloc] init];
	[dice addDie: [SPDie dieWithSize: 6 andFacingSide: 1]];
	availableDice = [[SPSelectableDice alloc] init];
	[availableDice addDie: [SPDie dieWithSize: 4 andFacingSide: 4]
		     selected: NO];
	[availableDice addDie: [SPDie dieWithSize: 6 andFacingSide: 6]
		     selected: NO];
	[availableDice addDie: [SPDie dieWithSize: 8 andFacingSide: 8]
		     selected: NO];
	[availableDice addDie: [SPDie dieWithSize: 10 andFacingSide: 10]
		     selected: NO];
	[availableDice addDie: [SPDie dieWithSize: 10 andFacingSide: 1
				    andMultiplier: 10 andValueOffset: -10]
		     selected: NO];
	[availableDice addDie: [SPDie dieWithSize: 20 andFacingSide: 20]
		     selected: NO];
	return self;
}

-(void) dealloc
{
	[dice release];
	[super dealloc];
}

-(SPSelectableDice*) dice
{
	return dice;
}

-(SPSelectableDice*) availableDice
{
	return availableDice;
}

@end
