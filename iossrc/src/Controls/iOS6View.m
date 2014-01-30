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
//  iOS6View.m
//  Easy Dice
//
//  Created by Slobodan Pejic on 1/3/2014.
//
//

#import "iOS6View.h"

@implementation iOS6View

- (id)initWithView:(UIView *)view
{
	self = [super initWithFrame:view.frame];
	if (self) {
		[self addSubview:view];
	}
	return self;
}

- (void)layoutSubviews {
	NSArray *versionCompatibility = [[UIDevice currentDevice].systemVersion componentsSeparatedByString:@"."];
	CGRect bounds = self.bounds;
	CGRect subFrame = bounds;
	if ([versionCompatibility[0] intValue] >= 7) {
		subFrame.origin.y += 20;
		subFrame.size.height -= 20;
	}
	for (UIView *view in self.subviews) {
		view.frame = subFrame;
	}
}

@end
