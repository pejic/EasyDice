/** \brief Wraps another UIView with a banner at the bottom.
 *
 * SPBannerContainer wraps a view with a banner at the bottom.
 *
 */

#import <UIKit/UIKit.h>
#import <iAd/iAd.h>

@interface SPBannerContainer : UIViewController <ADBannerViewDelegate> {
	UIView* childView;
	ADBannerView* banner;
	CGFloat marginTop;
	CGFloat marginLeft;
	CGFloat marginRight;
	CGFloat marginBottom;
}

@property (nonatomic, retain) IBOutlet UIView* childView;
@property (nonatomic) CGFloat marginTop;
@property (nonatomic) CGFloat marginLeft;
@property (nonatomic) CGFloat marginRight;
@property (nonatomic) CGFloat marginBottom;

@end

