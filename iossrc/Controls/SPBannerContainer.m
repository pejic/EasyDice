

#import "SPBannerContainer.h"

static const float animationDuration = 0.5;

@interface SPBannerContainer (Private)
-(void) makeBanner;
-(void) layout: (BOOL) animated;
@end

@implementation SPBannerContainer (Private)
-(void) makeBanner
{
	NSString* contentSize = nil;
	if (ADBannerContentSizeIdentifierPortrait != nil) {
		contentSize = ADBannerContentSizeIdentifierPortrait;
	}
	else {
		contentSize = ADBannerContentSizeIdentifier320x50;
	}
	CGRect frame;
	frame.size = [ADBannerView sizeFromBannerContentSizeIdentifier:
		ADBannerContentSizeIdentifierPortrait];
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

-(void) layout: (BOOL) animated
{
	CGRect bounds = self.view.bounds;
	CGRect bframe;
	CGRect vframe;
	CGSize bsize = CGSizeMake(0, 0);
	if (banner.bannerLoaded) {
		bsize = [ADBannerView sizeFromBannerContentSizeIdentifier:
				ADBannerContentSizeIdentifierPortrait];
	}
	bframe.size = bsize;
	bframe.origin = CGPointMake(0, bounds.size.height - bsize.height);
	vframe.size = CGSizeMake(bounds.size.width - marginLeft - marginRight,
		bounds.size.height - bsize.height - marginTop - marginBottom);
	vframe.origin = CGPointMake(marginLeft, marginTop);

	float duration = animated ? animationDuration : 0.0;
	[UIView animateWithDuration: duration
			 animations: ^{
				childView.frame = vframe;
				banner.frame = bframe;
			 }];
}
@end

@implementation SPBannerContainer

-(id) init
{
	if (![super init]) {
		return nil;
	}

	static const float animationDuration = 0.5;
	return (self);
}

-(void) dealloc
{
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
	[childView removeFromSuperview];
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
