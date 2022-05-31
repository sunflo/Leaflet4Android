package com.flo.leaflet.sdk.lib

import com.google.gson.*
import java.lang.reflect.Type

class PolylineSerializer : JsonSerializer<Polyline> {
    override fun serialize(
        src: Polyline,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonObject().apply {
            add("color", JsonPrimitive(src.color))
            add("weight", JsonPrimitive(src.weight))
            add("opacity", JsonPrimitive(src.opacity))
            add("geometry", JsonPrimitive(Gson().toJson(src.geometry)))
        }
    }
}