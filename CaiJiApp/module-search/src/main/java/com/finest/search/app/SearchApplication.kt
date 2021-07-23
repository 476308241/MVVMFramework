package com.finest.search.app

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
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
open class SearchApplication : Application() {

    companion object {
        @JvmStatic
        private lateinit var instance: SearchApplication

        fun get(): SearchApplication {
            return instance
        }
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
        ARouter.init(this);
        initTimber()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        LiveEventBus.config()
        Thread.setDefaultUncaughtExceptionHandler(MyCrashHandler(applicationContext))
    }
    /**
     * 初始化日志
     */
    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

}
