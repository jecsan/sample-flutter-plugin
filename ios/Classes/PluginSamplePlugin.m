#import "PluginSamplePlugin.h"
#if __has_include(<plugin_sample/plugin_sample-Swift.h>)
#import <plugin_sample/plugin_sample-Swift.h>
#else
// Support project import fallback if the generated compatibility header
// is not copied when this plugin is created as a library.
// https://forums.swift.org/t/swift-static-libraries-dont-copy-generated-objective-c-header/19816
#import "plugin_sample-Swift.h"
#endif

@implementation PluginSamplePlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftPluginSamplePlugin registerWithRegistrar:registrar];
}
@end
