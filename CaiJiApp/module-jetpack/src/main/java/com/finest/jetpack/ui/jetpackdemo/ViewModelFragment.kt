package com.finest.jetpack.ui.jetpackdemo

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.alibaba.android.arouter.facade.annotation.Route
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.comm_base.util.RoutePath
import com.finest.jetpack.R
import com.finest.jetpack.databinding.FragmentViewModelBinding
import com.finest.jetpack.viewmodel.SharedViewModel

/**
 * Created by liangjiangze on 2020/12/24.
 */
@Route(path = RoutePath.JETPACK_VIEWMODEL)
class ViewModelFragment: BaseMVVMFragment()  {

    override fun getLayoutResId(): Int {
        return R.layout.fragment_view_model
    }

    override fun getActivityTitle(): String {
        return ""
    }

    override fun initView() {
        val model = ViewModelProvider(this)[SharedViewModel::class.java]
        var dataBinding = mViewDataBinding as FragmentViewModelBinding
        dataBinding.viewModel = model

//        dataBinding.tvClick.setOnClickListener {
//            model.sharedName.value = "anrikuwen55555"
//            LiveEventBus.get(LiveEventKey.mainTabFragment_change_tab).post("")
//            findNavController().navigateUp()
//        }
        model.sharedName.observe(this, Observer {
            dataBinding.viewModel = model
        })

//       tv_click.setOnClickListener {
//           showToast("haha")
//       }
    }


}