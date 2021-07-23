package com.finest.jetpack.ui.jetpackdemo

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.jetpack.R
import com.finest.jetpack.databinding.FragmentLifecycleBinding
import com.finest.jetpack.viewmodel.LifecycleViewModel

/**
 * Created by liangjiangze on 2020/12/24.
 */
class LifecycleFragment: BaseMVVMFragment() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_lifecycle
    }

    override fun getActivityTitle(): String {
        return ""
    }

    override fun initView() {
        val model = ViewModelProvider(this)[LifecycleViewModel::class.java]
        var dataBinding = mViewDataBinding as FragmentLifecycleBinding
        dataBinding.viewModel = model
        //在viewModle使用lifecycle
        lifecycle.addObserver(model)

        dataBinding.tvClick.setOnClickListener {
            model.sharedName.value = "anrikuwen55555"
        }
        model.sharedName.observe(this, Observer {
            dataBinding.viewModel = model
        })


    }
}