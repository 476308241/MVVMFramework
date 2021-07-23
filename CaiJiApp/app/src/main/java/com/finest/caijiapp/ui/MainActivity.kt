package com.finest.caijiapp.ui

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.finest.caijiapp.R
import com.finest.caijiapp.databinding.ActivityMainBinding
import com.finest.caijiapp.services.HeartBeatService
import com.finest.comm_base.config.LiveEventKey
import com.finest.comm_base.mvvm.BaseMVVMActivity
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber


@AndroidEntryPoint
class MainActivity : BaseMVVMActivity() {
    lateinit var  dataBinding: ActivityMainBinding
    lateinit var navController: NavController
    var exitTime = 0L
    override fun getLayoutResId(): Int {
        return R.layout.activity_main
    }

    override fun getActivityTitle(): String {
        return "main"
    }

    override fun initView() {
        dataBinding = mViewDataBinding as  ActivityMainBinding

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
             R.id.MainTabFragment->{
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
             }else->{
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
             }
            }
        }
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        initLiveEventObserve()
    }
    private fun initLiveEventObserve(){
        LiveEventBus.get(LiveEventKey.login_success).observe(this, Observer {
            Handler(Looper.getMainLooper()).postDelayed(Runnable {
                Timber.e("登录成功，启动心跳！")
                startService(Intent(MainActivity@this, HeartBeatService::class.java))
            },50)
        })
        LiveEventBus.get(LiveEventKey.set_title).observe(this, Observer {
            mToolbar?.visibility = View.VISIBLE
        })
    }
    override fun onBackPressed() {
        when (navController.currentDestination!!.id) {
            R.id.MainTabFragment -> {
                if (System.currentTimeMillis() - exitTime > 2000) {
                    ToastUtils.showShort("再按一次退出")
                    exitTime = System.currentTimeMillis()
                } else {
                   finish()
                }
            }
            R.id.LoginFragment -> {
               finish()
            }
            else -> {
                navController.navigateUp()
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(MainActivity@this, HeartBeatService::class.java))
    }
    //返回键监听
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        //返回false，内部fragment的onOptionsItemSelected方法可执行
        return false
    }
}