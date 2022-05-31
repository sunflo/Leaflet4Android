package com.flo.leaflet.sdk.droid

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * @author huangxz
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.tomap).setOnClickListener {
            startActivity(Intent(this@MainActivity, SimpleMapActivity::class.java))
        }
    }
}