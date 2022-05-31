package com.flo.leaflet.sdk.lib

import androidx.annotation.FloatRange
import com.google.gson.Gson
import java.util.*

class Marker(val lat: Double, val lng: Double) {

    @FloatRange(from = 0.0, to = 1.0)
    var opacity: Float = 1.0f
    var iconDesc: String = ""
    var zIndex: Int = 0
    var image: String = ""
    var width: Int = 20
    var height: Int = 20
    val markerId: String = UUID.randomUUID().toString()
    var snippet: String = ""
    var title: String = ""

    @FloatRange(from = 0.0, to = 1.0)
    val iconOffSet = floatArrayOf(0.5f, 1f)

    fun toJSon() = Gson().toJson(this)
}
