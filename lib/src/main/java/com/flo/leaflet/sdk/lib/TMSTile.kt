package com.flo.leaflet.sdk.lib

import com.google.gson.Gson

class TMSTile(
    url: String,
    maxZoom: Int = 17,
    minZoom: Int = 3,
    var attribution: String = "@LeafletDroid",
    val id: String,
    var tileSize: Int = 256,
    var offset: Int = 0
) : BaseTile(url, maxZoom, minZoom) {
    override fun toJSon(): String = Gson().toJson(this)
}