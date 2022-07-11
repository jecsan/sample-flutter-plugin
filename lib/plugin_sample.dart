
import 'plugin_sample_platform_interface.dart';

class PluginSample {
  Future<String?> getPlatformVersion() {
    return PluginSamplePlatform.instance.getPlatformVersion();
  }
}
