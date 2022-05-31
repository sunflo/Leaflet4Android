package com.flo.leaflet.sdk.lib

/**
 * EPSG描述类
 *
 */
class EPSG(
    val name: String,
    val param: String? = null,
    val origin: DoubleArray? = null,
    val resolution: DoubleArray? = null
) {
    companion object {
        fun default() = EPSG("EPSG:4326")
    }
}