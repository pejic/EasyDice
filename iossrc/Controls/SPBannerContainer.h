/** \brief Wraps another UIView with a banner at the bottom.
 *
 * SPBannerContainer wraps a view with a banner at the bottom.
 *
 */

#import <UIKit/UIKit.h>
#import <iAd/iAd.h>

/** Specifies the positions of the ad banner within an SPBannerContainer.
 */
typedef enum {
	/** The ad banner is placed at the top. */
	SPBannerContainerPositionTop,
	/** The ad banner is placed at the bottom. */
	SPBannerContainerPositionBottom
} SPBannerContainerPosition;

@interface SPBannerContainer : UIViewController <ADBannerViewDelegate> {
	@private
	UIView* childView;
	ADBannerView* banner;
	CGFloat marginTop;
	CGFloat marginLeft;
	CGFloat marginRight;
	CGFloat marginBottom;
	SPBannerContainerPosition bannerPosition;
}

@property (nonatomic, retain) IBOutlet UIView* childView;
@property (nonatomic) CGFloat marginTop;
@property (nonatomic) CGFloat marginLeft;
@property (nonatomic) CGFloat marginRight;
@property (nonatomic) CGFloat marginBottom;
@property (nonatomic) SPBannerContainerPosition bannerPosition;

@end

