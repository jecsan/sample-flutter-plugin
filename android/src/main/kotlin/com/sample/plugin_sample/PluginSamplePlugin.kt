package com.sample.plugin_sample

import android.util.Log
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/** PluginSamplePlugin */
class PluginSamplePlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "plugin_sample")
        channel.setMethodCallHandler(this)
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "getPlatformVersion") {
           result.success("Android ${android.os.Build.VERSION.RELEASE}")

            val validation = object : Validation {
                override fun validate(value: String): Boolean {

                    Log.d("Validation", "Validating $value from Android")

                    val validationResult =
                        runBlocking {
                        return@runBlocking suspendCoroutine<Boolean> {
                            channel.invokeMethod("validate", value, object : MethodChannel.Result {
                                override fun success(p0: Any?) {
                                    Log.d("Validation Result", "$p0")
                                    it.resume(p0 as Boolean)

                                }

                                override fun error(p0: String, p1: String?, p2: Any?) {
                                    Log.d("Validation Result", "Error $p0")

                                }

                                override fun notImplemented() {
                                    Log.d("Validation Result", "Not implemented")

                                }

                            })
                        }
                    }

                    Log.d("Validation", "Validation completed, result = $validationResult")


                    return validationResult
                }

            }

            validation.validate("Sample Input")


        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}


interface Validation {
    fun validate(value: String): Boolean
}