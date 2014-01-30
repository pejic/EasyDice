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
	if (!(self = [super init])) {
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
	[availableDice addDie: [SPDie dieWithSize: 12 andFacingSide: 12]
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
