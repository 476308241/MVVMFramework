package com.finest.comm_base.mvvm

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.finest.comm_base.mvvm.interfaces.IUI
import com.finest.comm_base.widget.view.CommonToolbar
import timber.log.Timber
import java.lang.Exception

/**
 * Created by liangjiangze on 2020/12/24.
 */
abstract class BaseMVVMActivity: AppCompatActivity() , IUI {
    val TAG = javaClass.simpleName
    val mContext: BaseMVVMActivity
        get() = this
    var mToolbar: CommonToolbar? = null
    lateinit var mViewDataBinding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            mViewDataBinding =  DataBindingUtil.setContentView(this, getLayoutResId())
        }catch (e:Exception){
            Timber.d("页面的xml需要以layout为根布局,否则绑定出现异常,不属于MVVM模式")
            setContentView(getLayoutResId())
        }
        //始终保持竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initView()
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        if (toolbar is CommonToolbar){
            mToolbar = toolbar
        }
    }

}