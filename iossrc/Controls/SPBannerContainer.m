

#import "SPBannerContainer.h"

static const float animationDuration = 0.5;

@interface SPBannerContainer (Private)
-(void) makeBanner;
-(void) layout: (BOOL) animated;
+(NSString *)bannerPortrait;
+(NSString *)bannerLandscape;
+(BOOL)useDeprecated;
@end

@implementation SPBannerContainer (Private)
-(void) makeBanner
{
	static NSString* const bannerClass = @"ADBannerView";
	if (NSClassFromString(bannerClass) != nil) {
		NSString* contentSize = nil;
		contentSize = [SPBannerContainer bannerPortrait];
		CGRect frame;
		frame.size = [ADBannerView sizeFromBannerContentSizeIdentifier:
			      contentSize];
		frame.origin = CGPointMake(0, CGRectGetMaxY(self.view.bounds));

		banner = [[ADBannerView alloc] initWithFrame: frame];
		banner.delegate = self;
		banner.autoresizingMask = UIViewAutoresizingFlexibleWidth
				| UIViewAutoresizingFlexibleHeight
				| UIViewAutoresizingFlexibleTopMargin;
		banner.requiredContentSizeIdentifiers =
		[NSSet setWithObjects: contentSize, nil];
		[self.view addSubview: banner];
	}
}

-(void) layout: (BOOL) animated
{
	CGRect bounds = self.view.bounds;
	CGRect bframe;
	CGRect vframe;
	CGSize bsize = CGSizeMake(0, 0);
	if (banner != nil && banner.bannerLoaded) {
		bsize = [ADBannerView sizeFromBannerContentSizeIdentifier:
				[SPBannerContainer bannerPortrait]];
	}
	bframe.size = bsize;
	vframe.size = CGSizeMake(bounds.size.width - marginLeft - marginRight,
				 bounds.size.height - bsize.height
					- marginTop - marginBottom);
	if (bannerPosition == SPBannerContainerPositionTop) {
		bframe.origin = CGPointMake(0, 0);
		if (!(banner != nil && banner.bannerLoaded)) {
			bframe.origin = CGPointMake(0, -50);
		}
		vframe.origin = CGPointMake(marginLeft,
					    marginTop + bsize.height);
	}
	else {
		bframe.origin = CGPointMake(0,
					    bounds.size.height - bsize.height);
		vframe.origin = CGPointMake(marginLeft, marginTop);
	}

	float duration = animated ? animationDuration : 0.0;
	[UIView animateWithDuration: duration
			 animations: ^{
				childView.frame = vframe;
				 if (banner != nil) {
					 banner.frame = bframe;
				 }
			 }];
}

+(NSString *)bannerPortrait
{
	if ([SPBannerContainer useDeprecated]) {
		return ADBannerContentSizeIdentifier320x50;
	}
	else {
		return ADBannerContentSizeIdentifierPortrait;
	}
}

+(NSString *)bannerLandscape
{
	if ([SPBannerContainer useDeprecated]) {
		return ADBannerContentSizeIdentifier480x32;
	}
	else {
		return ADBannerContentSizeIdentifierLandscape;
	}
}

+ (BOOL)useDeprecated
{
	BOOL useDeprecated = NO;
	NSString *devVersion = [[UIDevice currentDevice] systemVersion];
	NSArray *devVersions = [devVersion componentsSeparatedByString:@"."];
	int major = [[devVersions objectAtIndex:0] intValue];
	int minor = 0;
	if ([devVersions count] > 1) {
		minor = [[devVersions objectAtIndex:1] intValue];
	}
	if (major < 4 || (major == 4 && minor < 2)) {
		useDeprecated = YES;
	}
	return useDeprecated;
}
@end

@implementation SPBannerContainer

-(id) init
{
	if (![super init]) {
		return nil;
	}
	bannerPosition = SPBannerContainerPositionBottom;
	return (self);
}

-(void) dealloc
{
	[childView release];
	[banner release];
	[super dealloc];
}

-(void) viewDidLoad 
{
	[self makeBanner];
}

-(UIView*) childView
{
	return (childView);
}

-(void) setChildView: (UIView*) view
{
	[view retain];
	[childView removeFromSuperview];
	[childView release];
	childView = view;
	if (childView) {
		[self.view addSubview: childView];
	}
	[self layout: NO];
}

-(CGFloat) marginTop
{
	return (marginTop);
}

-(void) setMarginTop: (CGFloat) top
{
	marginTop = top;
	[self layout: NO];
}

-(CGFloat) marginLeft
{
	return (marginLeft);
}

-(void) setMarginLeft: (CGFloat) top
{
	marginLeft = top;
	[self layout: NO];
}

-(CGFloat) marginRight
{
	return (marginRight);
}

-(void) setMarginRight: (CGFloat) top
{
	marginRight = top;
	[self layout: NO];
}

-(CGFloat) marginBottom
{
	return (marginBottom);
}

-(void) setMarginBottom: (CGFloat) top
{
	marginBottom = top;
	[self layout: NO];
}

-(SPBannerContainerPosition) bannerPosition
{
	return (bannerPosition);
}

-(void) setBannerPosition: (SPBannerContainerPosition) bannerPosition_
{
	bannerPosition = bannerPosition_;
	[self layout: NO];
}

-(void) bannerViewDidLoadAd: (ADBannerView*) ad
{
	[self layout: YES];
}

-(void) bannerView: (ADBannerView*) ad
didFailToReceiveAdWithError: (NSError*) error
{
	[self layout: YES];
}

-(BOOL) bannerViewActionShouldBegin: (ADBannerView*) ad
	       willLeaveApplication: (BOOL) willLeave
{
	return YES;
}

-(void) bannerViewActionDidFinish: (ADBannerView*) ad
{
}

@end
