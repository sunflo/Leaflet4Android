package com.flo.leaflet.sdk.lib

import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.widget.Toast
import org.json.JSONArray
import wendu.dsbridge.DWebView

class MapView(
    context: Context,
    private val mapOption: LeafletOption = LeafletOption(),
    attrs: AttributeSet?,
    defStyleAttr: Int
) : DWebView(context, attrs), OnHtmlLoadListener {
    constructor(context: Context, attrs: AttributeSet?) : this(context, LeafletOption(), attrs, 0)
    constructor(context: Context, mapOption: LeafletOption) : this(context, mapOption, null, 0)

    var leafletMap: LeafletMap? = null
    var tempCallback: OnMapReadyListener? = null

    init {
        initView()
    }

    private fun initView() {
        webChromeClient = KWebChromeClient(this)
        setWebContentsDebuggingEnabled(true)
        addJavascriptObject(this, "view")
        loadUrl(mapOption.initUrl)

    }

    //页面load完成注入初始化参数
    override fun onFullLoad() {
        callHandler("initParams", arrayOf(mapOption.toUrlParam()))
    }

    fun getMapAsync(callback: OnMapReadyListener) {
        leafletMap?.also {
            post { callback.mapReady(it) }
        } ?: run {
            tempCallback = callback
        }
    }

    @JavascriptInterface
    fun ready(params: Any) {
        leafletMap = LeafletMap(this, mapOption).also {
            tempCallback?.run {
                if (params is JSONArray) {
                    val toast = params.getString(0)
                    val zoom = params.getInt(1)
                    val west = params.getDouble(2)
                    val north = params.getDouble(3)
                    val east = params.getDouble(4)
                    val south = params.getDouble(5)
                    val centerX = params.getDouble(6)
                    val centerY = params.getDouble(7)
                    it.currentZoom = zoom
                    it.mapCenter = Position(centerX, centerY)
                    it.southEast = Position(south, east)
                    it.northWest = Position(north, west)
                }
                post {
                    mapReady(it)
                }
            }
            tempCallback = null
        }

    }

    @JavascriptInterface
    fun toast(args: Any) {
        Toast.makeText(context, "js echo:[${args.toString()}]", Toast.LENGTH_LONG).show()
    }

    fun onCreate() {}
    fun onDestroy() {}


}