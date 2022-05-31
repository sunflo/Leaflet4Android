package com.flo.leaflet.sdk.lib

import com.google.gson.GsonBuilder

open class Polyline : Element() {
    var geometry: MutableList<Position> = mutableListOf()


    var color: String = "#287BDE"
    var weight: Int = 1
    var opacity: Float = 1.0f

    open fun toJSon(): String {
        return GsonBuilder()
            .registerTypeAdapter(Position::class.java, PositionSerializer())
//            .registerTypeAdapter(Polyline::class.java, PolylineSerializer())
            .create()
            .toJson(this)
    }


}