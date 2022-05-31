package com.flo.leaflet.sdk.lib

abstract class BaseTile(
    var url: String,
    var maxZoom: Int = 17,
    var minZoom: Int = 3,
    var errorTileUrl: String = "./image/blank_tile.png",
    var zIndex: Int = 0,
    var auth: Boolean = false
) {
    abstract fun toJSon(): String
}