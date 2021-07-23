package com.finest.comm_base.util

import com.alibaba.fastjson.JSON
import timber.log.Timber
import java.lang.RuntimeException

/**
 * Created by liangjiangze on 2019/11/12.
 * 快速解析json数据的某个字段，只解析有用的字段，减少bean实体的编写(注意：该字段不能是列表)
 */
object GetJSONHelper {

    fun getKeyString(result: String, keys: Array<String>): String {
        try {
            when (keys.size) {
                1 -> {
                    val maps = JSON.parse(result) as Map<*, *>
                    val data = maps[keys[0]] as Object?
                    return data.toString()
                }
                2 -> {
                    val maps = JSON.parse(result) as Map<*, *>
                    val data = maps[keys[0]] as Object?
                    val dataMaps = JSON.parse(data.toString()) as Map<*, *>
                    val pageInfo = dataMaps[keys[1]] as Object?
                    return pageInfo.toString()
                }
                3 -> {
                    val maps = JSON.parse(result) as Map<*, *>
                    val data = maps[keys[0]] as Object?
                    val dataMaps = JSON.parse(data.toString()) as Map<*, *>
                    val pageInfo = dataMaps[keys[1]] as Object?
                    val pageInfoMaps = JSON.parse(pageInfo.toString()) as Map<*, *>
                    val keyStr = pageInfoMaps[keys[2]] as Object?
                    return keyStr.toString()
                }
                else -> {
                    return ""
                }
            }
        } catch (e: Exception) {
            Timber.e(e)
            return ""
        }


    }

    fun getCode(result: String): Int {
        return try {
            val maps = JSON.parse(result) as Map<*, *>
            val data = maps["code"] as Object?
            data.toString().toInt()
        }catch (e: Exception){
            Timber.e(e)
            -100
        }

    }

    fun getMsg(result: String): String {
        return try {
            val maps = JSON.parse(result) as Map<*, *>
            val data = maps["message"] as Object?
            data.toString()
        }catch (e:Exception){
            "服务器没有提供错误消息"
        }

    }

    fun checkCode(it:String){
        val code = getCode(it)
        if (code == 200 || code == 1) {

        } else {
            val msg = getMsg(it)
            throw RuntimeException(msg)
        }
    }
}
