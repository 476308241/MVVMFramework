package com.finest.caijiapp.app

import android.app.Application
import android.content.Context
import android.os.Debug
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.finest.caijiapp.BuildConfig
import com.finest.comm_base.app.MyApplication
import com.finest.comm_base.app.MyCrashHandler
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * 主应用
 *
 * Created by liangjiangze on 2020/05/08.
 */
@HiltAndroidApp
class MainApplication : Application() {

    companion object {
        @JvmStatic
        private lateinit var instance: Application

        fun get(): Application {
            return instance
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        if(com.finest.comm_base.BuildConfig.DEBUG){
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
        initTimber()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LiveEventBus.config()
        Thread.setDefaultUncaughtExceptionHandler(MyCrashHandler(applicationContext))
    }
    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }


}
