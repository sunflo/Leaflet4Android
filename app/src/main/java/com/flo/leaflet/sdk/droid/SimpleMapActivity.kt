package com.flo.leaflet.sdk.droid

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.flo.leaflet.sdk.lib.*
import kotlin.concurrent.thread

/**
 * @author huangxz
 */
class SimpleMapActivity : AppCompatActivity() {
    var mMap: LeafletMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple)

        val mapView: MapView = findViewById(R.id.mapview)
        mapView.getMapAsync(object : OnMapReadyListener {
            override fun mapReady(map: LeafletMap) {
                Log.e("flo", "getMap async")
                mMap = map
                map.crs(
                    "EPSG:4326", "",
                    listOf(-180.0, 90.0),
                    listOf(
                        1.4078260157100582,
                        0.703913007855028,
                        0.35156249999999994,
                        0.17578124999999997,
                        0.08789062500000014,
                        0.04394531250000007,
                        0.021972656250000007,
                        0.01098632812500002,
                        0.00549316406250001,
                        0.0027465820312500017,
                        0.0013732910156250009,
                        0.000686645507812499,
                        0.0003433227539062495,
                        0.00017166137695312503,
                        0.00008583068847656251,
                        0.000042915344238281406,
                        0.000021457672119140645,
                        0.000010728836059570307,
                        0.000005364418029785169
                    )
                )
                map.addLayer(
                    TMSTile(
                        "http://t2.tianditu.gov.cn/DataServer?T=vec_c&x={x}&y={y}&l={z}&tk=256f647aaffe22c1da0f4c0cae2dd806",
                        id = "bg"
                    )
                )
                map.addLayer(
                    TMSTile(
                        url = "http://t3.tianditu.gov.cn/DataServer?T=cva_c&x={x}&y={y}&l={z}&tk=256f647aaffe22c1da0f4c0cae2dd806",
                        id = "bg"
                    )
                )

            }
        })

    }

    fun addSingleClickListener(view: View) {
        mMap?.addOnMapClickListener(object : LeafletMap.OnMapClickListener {
            override fun onMapClick(target: Position): Boolean {
                Log.e("flo", target.toString())
                return false
            }
        })
    }

    fun removeSingleClickListener(view: View) {
        mMap?.removeOnMapClickListener()
    }

    fun addLongClickListener(view: View) {}
    fun removeLongClickListener(view: View) {}
    fun getZoom(view: View) {
        val zoom = mMap?.getZoom()
        Log.e("flo", "current zoom $zoom")
    }

    fun zoomOut(view: View) {
        mMap?.zoomOut()
    }

    fun zoomInt(view: View) {
        mMap?.zoomIn()
    }


    var marker: Marker? = null
    fun addMarker(view: View) {
        marker = mMap?.addMarker(Marker(31.2313, 120.12355).apply {
            image =
                "data:image/png;base64,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"
            width = 86
            height = 41
            opacity = 0.8f

        })
    }

    fun removeMarker(view: View) {
        marker?.run {
            mMap?.removeMarker(this)
        }
    }

    fun addPolyline(view: View) {
        mMap?.addPolyline(Polyline().apply {
            geometry = mutableListOf(
                Position(31.292821213813752, 120.52303146884537),
                Position(31.292171106265613, 120.52401782988523),
                Position(31.292930824529336, 120.52457956912804)
            )
        })
    }

    fun screenToPoint(view: View) {
        thread {
            val p = mMap?.screenToLocation(0f, 0f)
            Log.e("flo", p.toString())
        }
    }

    fun pointToScreen(view: View) {
        thread {
            val p = mMap?.run {
                locationToScreen(mapCenter.lat, mapCenter.lng)
            }
            Log.e("flo", p.toString())
        }

    }
}