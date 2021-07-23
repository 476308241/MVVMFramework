package com.finest.jetpack.ui.jetpackdemo

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.viewModels
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ToastUtils
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.comm_base.util.RoutePath
import com.finest.jetpack.R
import com.finest.jetpack.databinding.FragmentDatabindingBinding
import com.finest.jetpack.viewmodel.SharedViewModel

/**
 * Created by liangjiangze on 2020/12/24.
 */
@Route(path = RoutePath.JETPACK_DATABINDING)
class DataBindingTestFragment: BaseMVVMFragment() {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_databinding
    }

    override fun getActivityTitle(): String {
        return "DataBinding"
    }

    override fun initView() {
        val viewModel by viewModels<SharedViewModel>()
        var dataBinding = mViewDataBinding as FragmentDatabindingBinding
        dataBinding.viewModel = viewModel
        dataBinding.tvClick.setOnClickListener {
            viewModel.sharedName.value = "anrikuwen"
            dataBinding.viewModel = viewModel
            ToastUtils.showShort("haha")
        }
//        viewModel.sharedName.observe(this, Observer {
//            tv_click.text = it
//        })
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        requireActivity().menuInflater.inflate(R.menu.menu_common_tool_bar, menu);

    }
    override fun onPrepareOptionsMenu(menu: Menu) {
        var  menuItem = menu!!.findItem(R.id.menu_save)
        menuItem.title = "databing"
        menuItem.isVisible = true
        super.onPrepareOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> navigateTo(R.id.ViewModelFragment)
            else->{

            }
        }
        return super.onOptionsItemSelected(item)
    }


}