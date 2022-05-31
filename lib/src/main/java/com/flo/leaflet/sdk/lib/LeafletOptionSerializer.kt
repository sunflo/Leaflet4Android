package com.flo.leaflet.sdk.lib

import com.google.gson.*
import java.lang.reflect.Type

class LeafletOptionSerializer : JsonSerializer<LeafletOption> {
    override fun serialize(
        src: LeafletOption,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonObject().apply {
            add("lat", JsonPrimitive(src.mapCenter.lat))
            add("lng", JsonPrimitive(src.mapCenter.lng))
            add("epsg", Gson().toJsonTree(src.epsg))
            add("zoom", JsonPrimitive(src.zoom))
            add("minZoom", JsonPrimitive(src.minZoom))
            add("maxZoom", JsonPrimitive(src.maxZoom))
            add("show", JsonPrimitive(src.console))
            add("token", JsonPrimitive(src.token))
            add("version", JsonPrimitive(src.version))
        }
    }
}