import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'plugin_sample_platform_interface.dart';

/// An implementation of [PluginSamplePlatform] that uses method channels.
class MethodChannelPluginSample extends PluginSamplePlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('plugin_sample');

  @override
  Future<String?> getPlatformVersion() async {
    methodChannel.setMethodCallHandler((call) async {
      if (call.method == 'validate') {
        final text = call.arguments as String;
        print("Validating $text from Dart code...");
        return text.isNotEmpty;
      }
      return null;
    });

    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
