package com.finest.comm_net.okhttp

import okhttp3.Interceptor
import java.util.*

/**
 * 全局请求参数管理类
 * Created by w on 2017/11/2.
 */

class NetWorkCommonParams {
    val commonParams = HashMap<String, String>()

    /**
     * 获取全局请求公共参数，用于okhttp框架
     * @return  Interceptor  返回okhttp框架需要的参数
     */
    val interceptorParams: Interceptor
        get() = Interceptor { chain ->
            val original = chain.request()
            val originalHttpUrl = original.url
            val builer = originalHttpUrl.newBuilder()
            val iter = commonParams.entries.iterator()
            while (iter.hasNext()) {
                val entry = iter.next() as Map.Entry<*, *>
                val key = entry.key as String
                val `val` = entry.value as String

                builer.addQueryParameter(key, `val`)
            }

            val url = builer.build()
            val requestBuilder = original.newBuilder()
                .url(url)

            val request = requestBuilder.build()
            chain.proceed(request)
        }

    /**
     * 获取请求公共参数
     * @return  "？key1=val1&"
     */
    val commonParmasUrlString: String
        get() {

            var params = "?"
            val iter = commonParams.entries.iterator()
            while (iter.hasNext()) {
                val entry = iter.next() as Map.Entry<*, *>
                val key = entry.key
                val `val` = entry.value

                params = "$params$key=$`val`&"
            }

            return params
        }

    /**
     * 添加全局请求公共参数
     * @param key
     * @param val
     */
    fun addCommonParams(key: String, `val`: String) {
        commonParams[key] = `val`
    }

    fun getCommonParams(key: String): String {
        var value = ""
        val iter = commonParams.entries.iterator()
        while (iter.hasNext()) {
            val entry = iter.next() as Map.Entry<*, *>
            if (entry.key == key)
                value = entry.value as String
        }
        return value
    }

    /**
     * 移除全局请求公共参数
     * @param key  需要移除的参数名称
     * @return 成功true，没有该参数false
     */
    fun removeCommonParams(key: String): Boolean? {
        var b: Boolean? = false
        if (commonParams.containsKey(key)) {
            commonParams.remove(key)
            b = true
        }

        return b
    }

    /**
     * 清空全局请求公共参数
     * @return true
     */
    fun clearCommonParams(): Boolean? {
        commonParams.clear()
        return true
    }

    companion object {
        @Volatile
        private var mInstance: NetWorkCommonParams? = null


        /**
         * 获取 单例 工程文件管理工具类实例, 全局使用方法
         * @return
         */
        val instance: NetWorkCommonParams
            get() {
                if (null == mInstance) {
                    synchronized(NetWorkCommonParams::class.java) {
                        if (null == mInstance) {
                            mInstance = NetWorkCommonParams()
                        }
                    }
                }
                return mInstance!!
            }
    }

}
