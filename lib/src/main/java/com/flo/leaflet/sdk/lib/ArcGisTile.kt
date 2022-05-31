package com.flo.leaflet.sdk.lib

import com.google.gson.Gson

class ArcGisTile(
    url: String,
    maxZoom: Int = 17,
    minZoom: Int = 3,
    val arcGisToken: String = ""
) : BaseTile(url, maxZoom, minZoom) {
    override fun toJSon(): String = Gson().toJson(this)
}