package com.flo.leaflet.sdk.lib

import android.webkit.WebChromeClient
import android.webkit.WebView

class KWebChromeClient(var callback: OnHtmlLoadListener?) : WebChromeClient() {
    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (newProgress == 100) {
            callback?.onFullLoad()
            callback = null
        }
    }

}