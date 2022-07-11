import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'plugin_sample_method_channel.dart';

abstract class PluginSamplePlatform extends PlatformInterface {
  /// Constructs a PluginSamplePlatform.
  PluginSamplePlatform() : super(token: _token);

  static final Object _token = Object();

  static PluginSamplePlatform _instance = MethodChannelPluginSample();

  /// The default instance of [PluginSamplePlatform] to use.
  ///
  /// Defaults to [MethodChannelPluginSample].
  static PluginSamplePlatform get instance => _instance;
  
  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [PluginSamplePlatform] when
  /// they register themselves.
  static set instance(PluginSamplePlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }
}
