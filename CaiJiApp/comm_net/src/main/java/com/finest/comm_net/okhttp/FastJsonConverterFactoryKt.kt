package com.finest.comm_net.okhttp

import com.alibaba.fastjson.JSON
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type
import kotlin.jvm.Throws

/**
 * Fastjson转换工厂 kotlin
 *
 * @author txw
 * @date 2020-01-01
 */
class FastJsonConverterFactoryKt : Converter.Factory(){
    companion object {
        fun create(): FastJsonConverterFactoryKt {
            return FastJsonConverterFactoryKt()
        }
    }
    override fun responseBodyConverter(
        type: Type?,
        annotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody, *>? {
        return FastJsonResponseBodyConvertKt<Any>(
            type!!
        )
    }

    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation>?,
        methodAnnotations: Array<Annotation>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody>? {
        return FastJsonRequestBodyConvertKt<Any>()
    }


    internal class FastJsonRequestBodyConvertKt<T> : Converter<T, RequestBody> {

        override fun convert(value: T): RequestBody? {
            return RequestBody.create(MEDIA_TYPE, JSON.toJSONBytes(value))
        }

        companion object {

            private val MEDIA_TYPE = "application/json; charset=UTF-8".toMediaType()
        }

    }

    internal class FastJsonResponseBodyConvertKt<T>(private val type: Type) :
        Converter<ResponseBody, T> {

        @Throws(IOException::class)
        override fun convert(value: ResponseBody): T? {
            val bufferedSource = value.source() //使用value.source().buffer()过时方法会报Null is not a valid element
            val tempStr = bufferedSource.readUtf8()
            bufferedSource.close()
            return JSON.parseObject<T>(tempStr, type)
        }

    }
}