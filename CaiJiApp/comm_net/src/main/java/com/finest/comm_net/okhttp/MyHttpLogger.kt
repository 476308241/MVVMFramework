package com.finest.comm_net.okhttp

import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import timber.log.Timber
import java.io.EOFException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.charset.Charset

/**
 *Create by H.W.J  2020/1/1
 */
class MyHttpLogger : Interceptor {

    val UTF8 = Charset.forName("UTF-8")

    var LOG: Boolean = true
    var mLogHeaders = false

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()
        if (!LOG) {
            return try {
                chain.proceed(request)
            } catch (e: Exception) {
                val errMessage: String = when (e) {
                    is SocketTimeoutException -> "网络超时，请检查网络"
                    is ConnectException -> "网络连接出错，请检查网络"
                    is SocketException -> "网络异常，请检查网络"
                    is UnknownHostException -> "网络连接出错，请检查网络"
                    else -> "网络异常，请检查网络"
                }
                Timber.i("<-- HTTP FAILED: $errMessage")
                throw IOException(errMessage)  //注意：使用协程时，OKHttp拦截器中不能使用RuntimeException抛出异常,proceed方法抛出的是IOException
            }
        }
        Timber.i("${request.method} ${request.url}")
        if (mLogHeaders) {

            val headers = request.headers
            for (i in 0 until headers.size) {
                Timber.i("Header:${headers.name(i)}:${headers.value(i)}")
            }
        }
        val requestBody = request.body
        if (null != requestBody) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset = UTF8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(UTF8)
            }
            if (null != contentType && contentType.subtype == "json" && isPlaintext(buffer)) {
                val bodyStr = buffer.readString(charset)
                if (bodyStr.length > 3000) {
                    val segmentInt = bodyStr.length / 3000
                    for (i in 0..segmentInt) {
                        if (i == segmentInt) {
                            Timber.i(bodyStr.substring(i * 3000))
                        } else {
                            Timber.i(bodyStr.substring(i * 3000, (i + 1) * 3000))
                        }
                    }
                } else {
                    Timber.i(buffer.readString(charset))
                }
            } else {
                Timber.i("--> END " + request.method + " (binary " + requestBody.contentLength() + "-byte body omitted)")
            }
        } else {
            Timber.i("No RequestBody")
        }
        val response: Response
        response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            val errMessage: String = when (e) {
                is SocketTimeoutException -> "网络超时，请检查网络"
                is ConnectException -> "网络连接出错，请检查网络"
                is SocketException -> "网络异常，请检查网络"
                is UnknownHostException -> "网络连接出错，请检查网络"
                else -> e.message ?: "网络异常，请检查网络"
            }
            Timber.i("<-- HTTP FAILED: $errMessage")
            throw IOException(errMessage) //注意：使用协程时，OKHttp拦截器中不能使用RuntimeException抛出异常,proceed方法抛出的是IOException
        }
        val responseBody = response.body
        val contentLength = responseBody!!.contentLength()
        val responseStatue =
            "\n Response:${response.code} ${if (response.message.isEmpty()) "" else ' '.toString()} ${response.message} ${response.request.url} \n"
        val headers = response.headers
        val source = responseBody.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.
        var buffer = source.buffer
        if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
            GzipSource(buffer.clone()).use { gzippedResponseBody ->
                buffer = Buffer()
                buffer.writeAll(gzippedResponseBody)
            }
        }
        var charset = UTF8
        val contentType = responseBody.contentType()
        if (contentType != null) {
            charset = contentType.charset(UTF8)
        }
        if (!isPlaintext(buffer)) {
            Timber.i("<-- END HTTP (binary " + buffer.size + "-byte body omitted)")
            return response
        }
        Timber.i(responseStatue)
        if (contentLength != 0L) {
            val responseBodyStr = buffer.clone().readString(charset)
            if (responseBodyStr.length > 3000) {
                val segmentInt = responseBodyStr.length / 3000
                for(i in 0..segmentInt){
                    if(i == segmentInt){
                        Timber.i(responseBodyStr.substring(i * 3000))
                    }else{
                        Timber.i(responseBodyStr.substring(i*3000,(i+1)*3000))
                    }
                }
            }else{
                Timber.i(responseBodyStr)
            }
        }
        return response
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    fun isPlaintext(buffer: Buffer): Boolean {
        return try {
            val prefix = Buffer()
            val byteCount = if (buffer.size < 64) buffer.size else 64
            buffer.copyTo(prefix, 0, byteCount)
            for (i in 0..15) {
                if (prefix.exhausted()) {
                    break
                }
                val codePoint = prefix.readUtf8CodePoint()
                if (Character.isISOControl(codePoint) && !Character.isWhitespace(
                        codePoint
                    )
                ) {
                    return false
                }
            }
            true
        } catch (e: EOFException) {
            false // Truncated UTF-8 sequence.
        }
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return (contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
                && !contentEncoding.equals("gzip", ignoreCase = true))
    }
}