

#import "SPBannerContainer.h"

@interface SPBannerContainer (Private)
-(void) makeBanner;
-(void) layout;
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

-(void) layout
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
	childView.frame = vframe;
	banner.frame = bframe;
}
@end

@implementation SPBannerContainer

-(id) init
{
	if (![super init]) {
		return nil;
	}
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
	[self layout];
}

-(CGFloat) marginTop
{
	return (marginTop);
}

-(void) setMarginTop: (CGFloat) top
{
	marginTop = top;
	[self layout];
}

-(CGFloat) marginLeft
{
	return (marginLeft);
}

-(void) setMarginLeft: (CGFloat) top
{
	marginLeft = top;
	[self layout];
}

-(CGFloat) marginRight
{
	return (marginRight);
}

-(void) setMarginRight: (CGFloat) top
{
	marginRight = top;
	[self layout];
}

-(CGFloat) marginBottom
{
	return (marginBottom);
}

-(void) setMarginBottom: (CGFloat) top
{
	marginBottom = top;
	[self layout];
}

-(void) bannerViewDidLoadAd: (ADBannerView*) ad
{
	[self layout];
}

-(void) bannerView: (ADBannerView*) ad
didFailToReceiveAdWithError: (NSError*) error
{
	[self layout];
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
