package com.flo.leaflet.sdk.lib

import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

/**
 * 地图瓦片请求的日志拦截器
 */
object LeafletHttpLogsInterceptorFactory {

    @JvmStatic
    fun newLoggingInterceptor(path: String = ""): HttpLoggingInterceptor {
        val legalPath =
            if (path.isEmpty()) false else
                try {
                    File(path).run { exists() || mkdirs() }
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
        val loggingInterceptor = if (!legalPath)
            HttpLoggingInterceptor()
        else
            HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                val filename =
                    "${SimpleDateFormat("yyyy_MM_dd", Locale.CHINA).format(Date())}_log.txt"
                File(path, filename).appendText("\n$message")
            })

        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }


}