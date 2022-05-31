package com.flo.leaflet.sdk.lib

import androidx.annotation.FloatRange
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/**
 *
 * 地图初始化配置项
 */
class LeafletOption {
    @FloatRange(from = 3.0, to = 17.0)
    var zoom: Float = 15.0F
    var mapCenter: Position = Position(31.2939019, 120.5232847)
    var epsg: EPSG = EPSG.default()
    var initUrl = "file:///android_asset/index.html"
    var console = false
    var token = ""
    var minZoom = 3
    var maxZoom = 17
    var version = "3.0"
    var logPath = ""

    fun toJson(): String {
        return Gson().toJson(this)
    }

    fun toUrlParam(): String {
//        return "{\"lat\":${mapCenter.lat},\"lng\":${mapCenter.lng},\"epsg\":\"$epsg\",\"zoom\":$zoom,\"show\":$console}"
        return GsonBuilder()
            .registerTypeAdapter(LeafletOption::class.java, LeafletOptionSerializer())
            .create()
            .toJson(this)
    }

}