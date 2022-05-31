package com.flo.leaflet.sdk.lib

import com.google.gson.*
import java.lang.reflect.Type
import java.math.BigDecimal
import java.math.RoundingMode

class PositionSerializer : JsonSerializer<Position> {
    override fun serialize(
        src: Position,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val result = JsonArray()

        val lat = BigDecimal.valueOf(src.lat)
        val latString = lat.setScale(7, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()

        val lon = BigDecimal.valueOf(src.lng)
        val lonString = lon.setScale(7, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString()

        result.add(JsonPrimitive(latString.toDouble()))
        result.add(JsonPrimitive(lonString.toDouble()))
        return result
    }
}