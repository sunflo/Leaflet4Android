package com.flo.leaflet.sdk.lib

import com.google.gson.Gson

class WMTSTile(
    url: String,
    maxZoom: Int = 17,
    minZoom: Int = 3,
    val layer: String,
    val style: String,
    val matrixSet: String = "EPSG:3857",
    val format: String = "image/png",
    val matrixIds: List<Int>? = null
) : BaseTile(url, maxZoom, minZoom) {
    override fun toJSon(): String = Gson().toJson(this)
}
