package com.flo.leaflet.sdk.lib

import android.util.Log
import android.webkit.JavascriptInterface

object JavaScriptInvocationDispatcher {
    private val TAG = "JSIDispatcher"
    private val mHandlers: HashMap<String, JSCallHandler> = hashMapOf()

    fun addHandler(tag: String, handler: JSCallHandler) {
        mHandlers[tag] = handler
    }

    @JavascriptInterface
    fun call(handler: String, params: String) {
        Log.d(TAG, "receive call from js bridge,methodName:[$handler]&param:{$params}")
        val split = handler.split("_")
        if (split.size != 2) {
            Log.e(TAG, "IGNORE ILLEGAL CALL from js bridge,methodName:[$handler]&param:{$params}")
        } else {
            mHandlers[split[0]]?.apply {
                dispatch(split[1], params)
            } ?: run {
                Log.e(TAG, "NO HANDLER FOUND,methodName:[$handler]&param:{$params}")
            }
        }
    }
}