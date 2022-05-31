package com.flo.leaflet.sdk.lib

import com.google.gson.GsonBuilder

class Polygon : Polyline() {
    var solidColor: String = "#287BDE"

    override fun toJSon(): String {
        return GsonBuilder()
            .registerTypeAdapter(Position::class.java, PositionSerializer())
//            .registerTypeAdapter(Polyline::class.java, PolylineSerializer())
            .create()
            .toJson(this)
    }
}