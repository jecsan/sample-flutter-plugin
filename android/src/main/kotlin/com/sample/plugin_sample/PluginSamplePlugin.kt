package com.sample.plugin_sample

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.NonNull
import io.flutter.embedding.engine.FlutterEngine

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/** PluginSamplePlugin */
class PluginSamplePlugin : FlutterPlugin, MethodCallHandler {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private lateinit var channel: MethodChannel
    var engine: FlutterEngine? = null

    override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "plugin_sample")
        channel.setMethodCallHandler(this)

    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")

            val validation = object : Validation {
                override fun validate(value: String): Boolean {
                    return invokeMethod("validate", value) as Boolean
                }

            }

            Executors.newSingleThreadExecutor().execute {
                val result = validation.validate("Sample Input")
                Log.d("Validation", "Result:$result")

            }



        } else {
            result.notImplemented()
        }
    }

    @Synchronized
    fun invokeMethod(method: String, arguments: Any?): Any? {
        var callResult = CallResult()
        Handler(Looper.getMainLooper()).post {
            channel.invokeMethod(method, arguments, object : MethodChannel.Result {
                override fun success(result: Any?) {
                    callResult.value = result
                    callResult.completed = true
                }

                override fun error(p0: String, p1: String?, p2: Any?) {
                    callResult.value = null
                    callResult.completed = true
                }

                override fun notImplemented() {
                    callResult.value = null
                    callResult.completed = true
                }
            })
        }
        try {
            while (!callResult.completed) {
                Log.d("Validation","Waiting for result...")
                // block
            }
        } catch (e: InterruptedException) {
            return null
        }
        return callResult.value
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }


    private class CallResult(
        @Volatile var completed: Boolean = false,
        @Volatile var value: Any? = null
    )
}


interface Validation {
    fun validate(value: String): Boolean
}