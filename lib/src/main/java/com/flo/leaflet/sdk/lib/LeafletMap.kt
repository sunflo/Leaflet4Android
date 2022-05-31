package com.flo.leaflet.sdk.lib

import android.graphics.PointF
import android.webkit.JavascriptInterface
import com.flo.leaflet.sdk.lib.ArcGisTile
import com.google.gson.JsonObject
import org.json.JSONArray
import timber.log.Timber
import wendu.dsbridge.DWebView
import wendu.dsbridge.OnReturnValue
import java.util.concurrent.CountDownLatch

/**
 * 地图对象，参照googleMap aMap等经典设计，使用此类作为地图操作的入口
 *
 */
class LeafletMap(private val webHandler: DWebView, mapOption: LeafletOption) {
    val markerContainer = hashMapOf<String, Marker>()
    val geometryContainer = mutableListOf<Any>()
    var mapCenter: Position = mapOption.mapCenter
    var northWest: Position = mapOption.mapCenter
    var southEast: Position = mapOption.mapCenter
    var currentZoom = 0
    var version = mapOption.version
    val path = mapOption.logPath


    init {
        /*      webHandler.post {
                  //注册webViewClient，用于拦截tile请求，增加鉴权，日志逻辑
                  webHandler.webViewClient = webClient
              }*/
        webHandler.addJavascriptObject(this, "map")
    }

    var onMapClickListener: OnMapClickListener? = null
    var onMapLongClickListener: OnMapLongClickListener? = null
    var onMarkerClickListener: OnMarkerClickListener? = null
    var cameraChangeListener: OnCameraChangeListener? = null

    fun addLayer(layer: TMSTile) {
        forwardAddLayer(layer, 0)
    }

    fun addLayer(layer: WMTSTile) {
        forwardAddLayer(layer, 1)
    }

    fun addLayer(layer: ArcGisTile) {
        forwardAddLayer(layer, 2)
    }

    fun addLayer(layer: WMSTile) {
        forwardAddLayer(layer, 3)
    }

    //做统一处理，用于处理拦截器业务
    private fun forwardAddLayer(layer: BaseTile, tag: Int) {
        webHandler.callHandler("addLayer", arrayOf(tag, layer.toJSon()))
    }

    fun addHeatMapLayer(data: List<Position>, option: JsonObject) {
        webHandler.callHandler(
            "heatMap.add",
            arrayOf(data.map { arrayOf(it.lat, it.lng) }, option.toString())
        )

    }

    fun removeHeatMapLayer() {
        webHandler.callHandler("heatMap.remove", arrayOf())

    }

    @JvmOverloads
    fun crs(
        name: String,
        param: String,
        origin: List<Double>?,
        resolution: List<Double>?
    ) {
        webHandler.callHandler("crs", arrayOf(name, param, origin, resolution))
    }


    /**
     * MARKER---START
     */
    fun addMarker(option: Marker): Marker {
        webHandler.callHandler(
            "marker.add", arrayOf(option.toJSon()),
            OnReturnValue<String> {
                Timber.d("marker added--> $it")
                markerContainer[option.markerId] = option
            })
        return option
    }

    fun removeMarker(option: Marker) {
        webHandler.callHandler("marker.remove", arrayOf(option.toJSon()),
            OnReturnValue<String> {
                Timber.d("marker removed--> $it")
                markerContainer.remove(option.markerId)
            })
    }

    fun addResource(iconId: Int, imageStr: String) {
        webHandler.callHandler("marker.bind", arrayOf(iconId.toString(), imageStr),
            OnReturnValue<String> {
                Timber.d("marker img bind-->")
            })
    }

    fun addOnMarkerClickListener(callback: OnMarkerClickListener) {
        onMarkerClickListener = callback
//        webHandler.callJs("addOnMarkerClickListener")
    }

    /**
     *由js层发起调用，禁止手动调用
     */
    @JavascriptInterface
    fun onMarkerClick(args: Any) {
        if (args is String) {
            markerContainer[args]?.let {
                webHandler.post {
                    onMarkerClickListener?.onMarkerClick(it)
                }
            }
        }
    }

    /**
     * MARKER---END
     */


    /**
     * GEOMETRY-DRAW-START
     */
    fun addPolyline(polyline: Polyline): Polyline {
        webHandler.callHandler("polyline.add", arrayOf(polyline.toJSon()),
            OnReturnValue<String> {
                Timber.d("add polyline--> $it")
            })
        geometryContainer.add(polyline)
        return polyline
    }

    fun removePolyline(polyline: Polyline) {
        webHandler.callHandler("polyline.remove", arrayOf(polyline.elementId),
            OnReturnValue<Boolean> {
                Timber.d("remove polyline--> $it")
                if (it) {
                    geometryContainer.remove(polyline)
                }
            })
    }

    fun addPolygon(polygon: Polygon): Polygon {
        webHandler.callHandler("polygon.add", arrayOf(polygon.toJSon()),
            OnReturnValue<String> {
                Timber.d("add polygon--> $it")
            })
        geometryContainer.add(polygon)
        return polygon
    }

    fun removePolygon(polygon: Polygon) {
        webHandler.callHandler("polygon.remove", arrayOf(polygon.elementId),
            OnReturnValue<Boolean> {
                Timber.d("remove polygon--> $it")
                if (it) {
                    geometryContainer.remove(polygon)
                }
            })
    }

    /**
     * GEOMETRY-DRAW-END
     */

    fun zoomIn() {
        webHandler.callJs("zoomIn")
    }

    fun zoomOut() {
        webHandler.callJs("zoomOut")
    }

    fun moveCamera(p: Position, animate: Boolean, level: Float = 0f) {
        webHandler.callHandler("moveCamera", arrayOf(p.lat, p.lng, animate, level))
    }

    fun translate(p: PointF, anim: Boolean) {
        webHandler.callHandler("translate", arrayOf(p.x, p.y, anim))
    }

    fun zoomTo(level: Float) {
        webHandler.callHandler("zoomTo", arrayOf(level))
    }

    fun mapCenter(): Position {
        return mapCenter
    }

    fun addOnMapClickListener(listener: OnMapClickListener) {
        webHandler.callJs("addOnMapClickListener")
        this.onMapClickListener = listener
    }

    fun removeOnMapClickListener() {
        webHandler.callJs("removeMapClickListener")
        onMapClickListener = null
    }


    fun addOnMapLongClickListener(listener: OnMapLongClickListener) {
        webHandler.callJs("addOnMapLongClickListener")
        this.onMapLongClickListener = listener
    }

    fun removeOnMapLongClickListener() {
        onMapLongClickListener = null
    }

    fun setOnCameraChangeListener(listener: OnCameraChangeListener) {
        cameraChangeListener = listener
    }

    /**
     * 需要子线程调用
     */
    fun locationToScreen(lat: Double, lng: Double): PointF {
        val result = PointF(0f, 0f)
        val latch = CountDownLatch(1)
        webHandler.callHandler("projection.toPoint", arrayOf(lat, lng), OnReturnValue<JSONArray> {
            result.x = it.getDouble(0).toFloat()
            result.y = it.getDouble(1).toFloat()
            latch.countDown()
        })

        latch.await()
        return result
    }

    /**
     * 需要子线程调用
     */
    fun screenToLocation(x: Float, y: Float): Position {

        val result = Position(0.0, 0.0)
        val latch = CountDownLatch(1)
        webHandler.callHandler("projection.fromPoint", arrayOf(x, y), OnReturnValue<JSONArray> {
            result.lat = it.getDouble(0)
            result.lng = it.getDouble(1)
            latch.countDown()
        })
        latch.await()
        return result
    }

    fun getZoom(): Float {
        return currentZoom.toFloat()
    }

    fun getCenter(): Position {
        return mapCenter
    }


    /**
     *由js层发起调用，禁止手动调用
     */
    @JavascriptInterface
    fun onMapClick(args: Any) {
        if (args is JSONArray) {
            val lat = args.getDouble(0)
            val lng = args.getDouble(1)
            webHandler.post {
                onMapClickListener?.onMapClick(Position(lat, lng))
            }
        }
    }

    @JavascriptInterface
    fun onMapLongClick(args: Any) {
        if (args is JSONArray) {
            val lat = args.getDouble(0)
            val lng = args.getDouble(1)
            webHandler.post {
                onMapLongClickListener?.onMapLongClick(Position(lat, lng))
            }
        }
    }


    @JavascriptInterface
    fun onMapCameraChange(params: Any) {
        if (params is JSONArray) {
            val event = params.getString(0)
            val zoom = params.getInt(1)
            val west = params.getDouble(2)
            val north = params.getDouble(3)
            val east = params.getDouble(4)
            val south = params.getDouble(5)
            val centerX = params.getDouble(6)
            val centerY = params.getDouble(7)
            when (event) {
                "moveEnd" -> {
                    currentZoom = zoom
                    mapCenter = Position(centerX, centerY)
                    southEast = Position(south, east)
                    northWest = Position(north, west)
                    webHandler.post {
                        cameraChangeListener?.onCameraChangeFinish(Position(centerX, centerY))
                    }
                }
                "moveStart" -> {
                    webHandler.post {
                        cameraChangeListener?.onCameraChangeStart(Position(centerX, centerY))
                    }
                }

                "mapInit" -> {
                    currentZoom = zoom
                    mapCenter = Position(centerX, centerY)
                    southEast = Position(south, east)
                    northWest = Position(north, west)
                }

            }
        }

    }


    interface OnMapClickListener {
        fun onMapClick(target: Position): Boolean
    }

    interface OnMapLongClickListener {
        fun onMapLongClick(target: Position): Boolean
    }

    interface OnMarkerClickListener {
        fun onMarkerClick(target: Marker)
    }

    interface OnCameraChangeListener {
        fun onCameraChangeStart(p: Position)
        fun onCameraChange(p: Position)
        fun onCameraChangeFinish(p: Position)
    }
}

fun DWebView.callJs(tag: String) {
    callHandler(tag, emptyArray())
}

