package com.flo.leaflet.sdk.lib

import com.google.gson.Gson
import com.google.gson.JsonObject

class WMSTile(
    url: String,
    maxZoom: Int = 17,
    minZoom: Int = 3,
    val matrixSet: String = "EPSG:3857",
    val matrixIds: List<Int>? = null,
    val params: JsonObject? = null
) : BaseTile(url, maxZoom, minZoom) {
    override fun toJSon(): String = Gson().toJson(this)
}

