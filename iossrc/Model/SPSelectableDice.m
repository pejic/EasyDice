//
//  SPSelectableDice.m
//  viewimagetouchtest
//
//  Created by Slobodan Pejic on 11-10-19.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "SPSelectableDice.h"

@interface SPSelectableDice (Private)
-(void) postNotification: (NSString*) msg
		   index: (int) i;
@end

@implementation SPSelectableDice (Private)
-(void) postNotification: (NSString*) msg
		   index: (int) i
{
	NSDictionary* dict = nil;
	if (index >= 0) {
		dict =[NSDictionary
			dictionaryWithObject: [NSNumber numberWithInt: i]
				      forKey: @"index"];
	}
	else {
		dict = [NSDictionary dictionary];
	}
	[[NSNotificationCenter defaultCenter]
			postNotificationName: msg
			              object: self
			            userInfo: dict];
}
@end

@implementation SPSelectableDice

-(id) init
{
	if (![super init]) {
		return nil;
	}
	dice = [[NSMutableArray alloc] init];
	selected = [[NSMutableArray alloc] init];
	return self;
}

-(void) dealloc
{
	[dice release];
	[selected release];
	[super dealloc];
}

-(int) count
{
	return ([dice count]);
}

-(void) addDie: (SPDie*) die
{
	[dice addObject: die];
	[selected addObject: [NSNumber numberWithBool: TRUE]];
	[self postNotification: @"added" index: [dice count] - 1];
}

-(void) addDie: (SPDie*) die
      selected: (BOOL) selected_
{
	[self addDie: die];
	[self setSelected: selected_ atIndex: [dice count] - 1];
}

-(void) removeDieAtIndex: (int) i
{
	[dice removeObjectAtIndex: i];
	[selected removeObjectAtIndex: i];
	[self postNotification: @"removed" index: i];
}

-(void) setSelected: (BOOL) selected_
	    atIndex: (int) i
{
	[selected replaceObjectAtIndex: i
			    withObject: [NSNumber numberWithBool: selected_]];
	[self postNotification: @"selectedChanged" index: i];
}

-(SPDie*) getDieAtIndex: (int) i;
{
	return ([dice objectAtIndex: i]);
}

-(BOOL) getSelectedAtIndex: (int) i
{
	NSNumber* sel = [selected objectAtIndex: i];
	return ([sel boolValue]);
}

-(void) setDie: (SPDie*) die
       atIndex: (int) i
{
	if (i < 0 || i >= [dice count]) {
		return;
	}
	else {
		[dice replaceObjectAtIndex: i
				withObject: die];
		[self postNotification: @"replaced"
				 index: i];
	}
}

-(void) rollSelected
{
	int i;
	for (i = 0; i < [self count]; i++) {
		if (![self getSelectedAtIndex: i]) {
			continue;
		}
		SPDie* die = [self getDieAtIndex: i];
		[dice replaceObjectAtIndex: i
				withObject: [SPDie dieWithRollDie: die]];
	}
	if ([self count] > 0) {
		[self postNotification: @"reset"
				 index: -1];
	}
}

-(void) removeUnselected
{
	int i;
	for (i = [self count]-1; i >= 0; i--) {
		if ([self getSelectedAtIndex: i]) {
			continue;
		}
		[self removeDieAtIndex: i];
	}
}

-(int) sumSelected
{
	int i;
	int sum = 0;
	for (i = 0; i < [self count]; i++) {
		if (![self getSelectedAtIndex: i]) {
			continue;
		}
		SPDie* die = [self getDieAtIndex: i];
		sum += [die value];
	}
	return sum;
}

-(int) sum
{
	int i;
	int sum = 0;
	for (i = 0; i < [self count]; i++) {
		SPDie* die = [self getDieAtIndex: i];
		sum += [die value];
	}
	return sum;
}

-(void) removeAllDice
{
	[dice removeAllObjects];
	[selected removeAllObjects];
	[self postNotification: @"reset"
			 index: -1];
}

@end
