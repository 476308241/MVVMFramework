package com.finest.caijiapp.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.finest.chat.task.TaskManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Create by liangjiangze  2020/06/17
 */
@AndroidEntryPoint
class HeartBeatService: Service() {
    private var mCount = 0
    private val scheduledThreadPool: ScheduledExecutorService =
        Executors.newScheduledThreadPool(5)
    @Inject
    lateinit var taskManager: TaskManager
    override fun onCreate() {
        super.onCreate()
        initCountTime()

    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        scheduledThreadPool.shutdown()
        super.onDestroy()
    }

    private fun initCountTime(){
        scheduledThreadPool.scheduleAtFixedRate(Runnable {
            mCount++
            if (mCount%30==0){
                Timber.d("30秒")

            }else if(mCount%10==0){
                Timber.d("30秒")
                taskManager.loginMore()
                //同步指挥云所有数据
                taskManager.syncPoliceDispatchBuzz()

            }

        },0,1, TimeUnit.SECONDS)
    }



}