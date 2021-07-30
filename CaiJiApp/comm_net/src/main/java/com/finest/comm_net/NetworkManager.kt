package com.finest.comm_net



import com.finest.comm_net.okhttp.FastJsonConverterFactoryKt
import com.finest.comm_net.okhttp.MyOkHttpClient
import com.finest.comm_net.okhttp.TIME_OUT_LONG
import com.finest.comm_net.request.HttpRequest
import com.finest.comm_net.request.URLManager
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit


/**
 * API初始化类
 */


class NetworkManager {
    companion object {
        //是否打印debug信息
        var isDebug = true

        fun getRequest(baseUrl: String = URLManager.BASE_URL): HttpRequest {
            val client = MyOkHttpClient.mInstance
            // 初始化Retrofit
            return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactoryKt.create())
                .build().create(HttpRequest::class.java)
        }

        fun getRequestLong(baseUrl: String = URLManager.BASE_URL): HttpRequest {
            val client = MyOkHttpClient.mInstance.newBuilder()
                .connectTimeout(TIME_OUT_LONG.toLong(), TimeUnit.SECONDS)
                .readTimeout(TIME_OUT_LONG.toLong(), TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_LONG.toLong(), TimeUnit.SECONDS)
                .build()
            // 初始化Retrofit
            return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(FastJsonConverterFactoryKt.create())
                .build().create(HttpRequest::class.java)
        }
    }

}
