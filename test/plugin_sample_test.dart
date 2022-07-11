import 'package:flutter_test/flutter_test.dart';
import 'package:plugin_sample/plugin_sample.dart';
import 'package:plugin_sample/plugin_sample_platform_interface.dart';
import 'package:plugin_sample/plugin_sample_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockPluginSamplePlatform 
    with MockPlatformInterfaceMixin
    implements PluginSamplePlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final PluginSamplePlatform initialPlatform = PluginSamplePlatform.instance;

  test('$MethodChannelPluginSample is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelPluginSample>());
  });

  test('getPlatformVersion', () async {
    PluginSample pluginSamplePlugin = PluginSample();
    MockPluginSamplePlatform fakePlatform = MockPluginSamplePlatform();
    PluginSamplePlatform.instance = fakePlatform;
  
    expect(await pluginSamplePlugin.getPlatformVersion(), '42');
  });
}
