#import "PPSSignatureView.h"
#import <UIKit/UIKit.h>
#ifdef RCT_NEW_ARCH_ENABLED
#import <React/RCTViewComponentView.h>
#else
#import <React/RCTView.h>
#endif
#import <React/RCTBridge.h>

@class RSSignatureViewManager;

#ifdef RCT_NEW_ARCH_ENABLED
@interface RSSignatureView : RCTViewComponentView
#else
@interface RSSignatureView : RCTView
#endif
@property (nonatomic, strong) PPSSignatureView *sign;
@property (nonatomic, strong) RSSignatureViewManager *manager;
-(void) onSaveButtonPressed;
-(void) onClearButtonPressed;
-(void) saveImage;
-(void) erase;
@end
