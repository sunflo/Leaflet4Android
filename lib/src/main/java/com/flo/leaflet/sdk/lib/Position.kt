package com.flo.leaflet.sdk.lib

class Position(var lat: Double, var lng: Double) {
    override fun toString(): String {
        return "Position(lat=$lat, lng=$lng)"
    }

    companion object {
        fun fromString(s: String): Position {
            val temp = s.split(",")
            return Position(temp[0].toDouble(), temp[1].toDouble())
        }

        fun fromArray(array: Array<Double>): Position {
            return Position(array[0], array[1])
        }

    }
}