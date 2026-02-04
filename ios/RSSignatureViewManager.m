#import "RSSignatureViewManager.h"
#import <React/RCTBridgeModule.h>
#import <React/RCTBridge.h>
#import <React/RCTEventDispatcher.h>

#ifdef RCT_NEW_ARCH_ENABLED
#import <React/RCTConversions.h>
#import <React/RCTFabricComponentsPlugins.h>
#import "RSSignatureView.h"
#endif

@implementation RSSignatureViewManager

@synthesize bridge = _bridge;
@synthesize signView;

RCT_EXPORT_MODULE()

RCT_EXPORT_VIEW_PROPERTY(rotateClockwise, BOOL)
RCT_EXPORT_VIEW_PROPERTY(square, BOOL)
RCT_EXPORT_VIEW_PROPERTY(showBorder, BOOL)
RCT_EXPORT_VIEW_PROPERTY(showNativeButtons, BOOL)
RCT_EXPORT_VIEW_PROPERTY(showTitleLabel, BOOL)
RCT_EXPORT_VIEW_PROPERTY(backgroundColor, UIColor)
RCT_EXPORT_VIEW_PROPERTY(strokeColor, UIColor)


-(dispatch_queue_t) methodQueue
{
	return dispatch_get_main_queue();
}

-(UIView *) view
{
	self.signView = [[RSSignatureView alloc] init];
	self.signView.manager = self;
	return signView;
}

// Both of these methods needs to be called from the main thread so the
// UI can clear out the signature.
RCT_EXPORT_METHOD(saveImage:(nonnull NSNumber *)reactTag) {
	dispatch_async(dispatch_get_main_queue(), ^{
		[self.signView saveImage];
	});
}

RCT_EXPORT_METHOD(resetImage:(nonnull NSNumber *)reactTag) {
	dispatch_async(dispatch_get_main_queue(), ^{
		[self.signView erase];
	});
}

#ifdef RCT_NEW_ARCH_ENABLED
- (void)handleCommand:(nonnull UIView *)view commandName:(nonnull NSString *)commandName args:(nullable NSArray *)args
{
  if ([commandName isEqualToString:@"saveImage"]) {
    [(RSSignatureView *)view saveImage];
  } else if ([commandName isEqualToString:@"resetImage"]) {
    [(RSSignatureView *)view erase];
  }
}
#endif

-(void) publishSaveImageEvent:(NSString *) aTempPath withEncoded: (NSString *) aEncoded {
	[self.bridge.eventDispatcher
	 sendDeviceEventWithName:@"onSaveEvent"
	 body:@{
					@"pathName": aTempPath,
					@"encoded": aEncoded
					}];
}

-(void) publishDraggedEvent {
	[self.bridge.eventDispatcher
	 sendDeviceEventWithName:@"onDragEvent"
	 body:@{@"dragged": @YES}];
}

@end
