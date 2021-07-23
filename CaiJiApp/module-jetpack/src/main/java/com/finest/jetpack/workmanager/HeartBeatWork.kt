package com.finest.jetpack.workmanager

import android.content.Context
import android.util.Log
import androidx.work.*

/**
 * Created by liangjiangze on 2021/1/4.
 */
class  HeartBeatWork(var context: Context, var params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        Log.e("TEST", "开始执行 任务1")
        val data = params.inputData.getString("key")
        data?.let {
            Log.e("TEST", "获取到传入的值 ->> $it")
        }
        val outData = Data.Builder().putString("dataKey", "传递的值任务1").build()
        return Result.success(outData)
    }


}