package com.finest.jetpack.ui.jetpackdemo

import androidx.fragment.app.viewModels
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.jetpack.R
import com.finest.jetpack.databinding.FragmentDemoBinding
import com.finest.jetpack.viewmodel.DemoViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by liangjiangze on 2021/1/13.
 * 用于新页面复制过去修改
 */
@AndroidEntryPoint
class DemoFragment : BaseMVVMFragment(){
    private val viewModel by viewModels<DemoViewModel>()
    private val  dataBinding = mViewDataBinding  as FragmentDemoBinding
    override fun getLayoutResId(): Int {
        return R.layout.fragment_demo
    }

    override fun getActivityTitle(): String {
        return ""
    }

    override fun initView() {
        dataBinding.viewModel = viewModel
    }

}