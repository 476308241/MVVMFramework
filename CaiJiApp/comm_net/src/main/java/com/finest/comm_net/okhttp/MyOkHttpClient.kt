package com.finest.comm_net.okhttp

import com.finest.comm_net.NetworkManager
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.security.SecureRandom
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

const val TIME_OUT = 15  //读写超时事件
const val TIME_OUT_LONG = 30  //读写超时事件--长时间
const val TIME_ONLINE_CACHE = 20 //在线缓存（秒）

object MyOkHttpClient {
    //手动创建一个OkHttpClient并设置超时时间缓存等设置
    //打印POST参数
    // 在线缓存,单位:秒
    // 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
    //设置读取超时时间
    //设置写入超时时间
    val mInstance: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
            .addInterceptor(NetWorkCommonParams.instance.interceptorParams)
        builder.addInterceptor { chain ->
            val request = chain.request()
            val originalResponse = chain.proceed(request)
            printParams(request)
            val maxAge = TIME_ONLINE_CACHE
            originalResponse.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=$maxAge")
                .build()
        }
        builder.connectTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT.toLong(), TimeUnit.SECONDS)
            .sslSocketFactory(createSSLSocketFactory()!!,TrustAllCerts())
            .hostnameVerifier(TrustAllHostnameVerifier())
        if (NetworkManager.isDebug) {
            builder.addInterceptor(httpLoggingInterceptor)
        }
        mInstance = builder.build()
    }

    /**
     * 日志输出
     * 自行判定是否添加
     *
     * @return
     */
    //日志显示级别
    //新建log拦截器
    private val httpLoggingInterceptor: Interceptor
        get() {
            return MyHttpLogger().apply { mLogHeaders = true }
        }

    /**
     * 创建自定义sslsocket工厂
     * @return
     */
    private fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null
        try {
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(TrustAllCerts()), SecureRandom())
            ssfFactory = sc.socketFactory
        } catch (e: Exception) {
        }

        return ssfFactory
    }

    /**
     * 信任所有主机
     */
    internal class TrustAllHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String, session: SSLSession): Boolean {
            // TODO 安全优化
            return true
        }
    }

    //打印POST参数
    private fun printParams(request: Request) {
        val method = request.method
        if ("POST" == method) {
            val sb = StringBuilder()
            if (request.body is FormBody) {
                val body = request.body as FormBody?
                for (i in 0 until body!!.size) {
                    //输出为json格式的，方便查看
                    sb.append("\"" + body.name(i) + "\"" + ":" + "\"" + body.value(i) + "\"" + ",")
                }
                sb.delete(sb.length - 1, sb.length)
                Timber.e("HttpRequest--->请求参数：%s", sb)
            }
        }
    }

}
