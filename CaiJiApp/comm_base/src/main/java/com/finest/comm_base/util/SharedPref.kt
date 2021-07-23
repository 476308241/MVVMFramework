package com.finest.comm_base.util


import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.text.TextUtils
import com.alibaba.fastjson.JSON
import java.util.*

/**
 * SharedPreference配置管理
 */
@Suppress("DEPRECATION")
class SharedPref private constructor(mContext: Context) {

    companion object {
        lateinit var sharedPreferences: SharedPreferences
        private var instance: SharedPref? = null
        fun get(context: Context): SharedPref {
            if (null == instance) {
                synchronized(SharedPref::class.java) {
                    if (null == instance) {
                        instance =
                            SharedPref(context)

                    }
                }
            }
            return instance!!
        }
    }

    init {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
    }

    /**
     * 获得参数
     *
     * @param name
     * @return String
     */
    fun getString(name: String, defaultValue: String): String {
        return sharedPreferences.getString(name, defaultValue)!!
    }

    /**
     * 设置参数
     *
     * @param name
     * @param value
     */
    fun setString(name: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(name, value)
        editor.apply()
    }

    /**
     * 获得参数
     *
     * @param name
     * @return int
     */
    fun getInt(name: String, defaultValue: Int): Int {
        val value = getString(name, defaultValue.toString())
        return if (TextUtils.isEmpty(value))
            0
        else
            Integer.parseInt(value)
    }

    /**
     * 设置参数
     *
     * @param name
     * @param value
     */
    fun setInt(name: String, value: Int) {
        setString(name, value.toString())
    }

    /**
     * 设置参数
     *
     * @param name
     * @param value
     */
    fun setLong(name: String, value: Long) {
        setString(name, value.toString())
    }

    /**
     * 获得参数
     *
     * @param name
     * @return long
     */
    fun getLong(name: String, defaultValue: Long): Long {
        val value = getString(name, defaultValue.toString())
        return if (TextUtils.isEmpty(value)) 0 else java.lang.Long.valueOf(value)
    }

    /**
     * 获得参数
     *
     * @param name
     * @return float
     */
    fun getFloat(name: String, defaultValue: Float): Float {
        val value = getString(name, defaultValue.toString())
        return if (TextUtils.isEmpty(value)) 0f else java.lang.Float.valueOf(value)
    }

    /**
     * 设置参数
     *
     * @param name
     * @param value
     */
    fun setFloat(name: String, value: Float?) {
        setString(name, value.toString())
    }

    fun getBoolean(name: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(name, defaultValue)
    }

    fun setBoolean(name: String, defaultValue: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(name, defaultValue)
        editor.apply()
    }

    /**
     * 保存List
     * @param tag
     * @param dataList
     */
    fun <T> setDataList(tag: String, dataList: List<T>?) {

        val editor = sharedPreferences.edit()
        //转换成json数据，再保存
        val strJson = JSON.toJSONString(dataList)
        if (null == dataList || dataList.isEmpty()) {
            editor.putString(tag, "")
        } else {
            editor.putString(tag, strJson)
        }
        editor.apply()
    }

    /**
     * 获取List
     * @param tag
     * @return
     */
    fun <T> getDataList(tag: String, clazz: Class<T>): List<T> {
        var datalist: List<T> = ArrayList()
        val strJson = sharedPreferences.getString(tag, null) ?: return datalist
        if (strJson.isNotEmpty()) {
            datalist = JSON.parseArray(strJson, clazz)
        }

        return datalist
    }


}
