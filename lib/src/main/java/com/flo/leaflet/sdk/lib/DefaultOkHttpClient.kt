package com.flo.leaflet.sdk.lib

import android.annotation.SuppressLint
import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

object DefaultOkHttpClient {
    var client: OkHttpClient? = null
    fun client(logPath: String): OkHttpClient {
        if (client == null) {
            client = OkHttpClient.Builder().apply {
                connectTimeout(30, TimeUnit.SECONDS)
                readTimeout(30, TimeUnit.SECONDS)
                writeTimeout(30, TimeUnit.SECONDS)
                if (logPath.isNotEmpty())
                    addInterceptor(LeafletHttpLogsInterceptorFactory.newLoggingInterceptor(logPath))
                hostnameVerifier { _, _ -> true }

                val trustAll = object : X509TrustManager {
                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkClientTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    override fun checkServerTrusted(
                        chain: Array<out X509Certificate>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }

                }
                sslSocketFactory(
                    SSLContext.getInstance("TLS").run {
                        init(
                            emptyArray(), arrayOf<X509TrustManager>(trustAll),
                            SecureRandom()
                        )
                        socketFactory
                    }, trustAll
                )
            }.build()
        }
        return client!!
    }

}