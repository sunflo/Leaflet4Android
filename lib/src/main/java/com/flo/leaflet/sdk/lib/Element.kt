package com.flo.leaflet.sdk.lib

import java.util.*

/**
 * 地图绘制元素，提供统一id属性
 *
 */
open class Element {
    val elementId = UUID.randomUUID().toString()

    override fun equals(other: Any?): Boolean {
        return if (other !is Element) {
            false
        } else {
            other.elementId == elementId
        }

    }

    override fun hashCode(): Int {
        return elementId.hashCode()
    }
}