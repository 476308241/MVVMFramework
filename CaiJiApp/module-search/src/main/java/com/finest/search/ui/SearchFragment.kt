package com.finest.search.ui

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.facade.annotation.Route
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.comm_base.util.RoutePath
import com.finest.search.R
import com.finest.search.databinding.FragmentSearchBinding
import com.finest.search.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random

/**
 * Created by liangjiangze on 2020/12/24.
 */
@AndroidEntryPoint
@Route(path = RoutePath.SEARCH_SEARCH)
class SearchFragment : BaseMVVMFragment() {
    lateinit var viewModel: SearchViewModel
    lateinit var  dataBinding: FragmentSearchBinding

    companion object {
        fun getInstance(): SearchFragment {
            return SearchFragment()
        }
    }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_search
    }
    override fun getActivityTitle(): String {
        return "SearchFragment"
    }
    override fun initView() {
        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]
        dataBinding = mViewDataBinding as FragmentSearchBinding
        dataBinding.viewModel = viewModel

        //run语法使用
        viewModel.run {
            userBean.observe(viewLifecycleOwner, Observer {

            })
        }
        viewModel.userBean.observe(this, Observer {
            dataBinding.viewModel = viewModel
        })
        dataBinding.tvLogout.setOnClickListener {
         viewModel.getUserBean22()
        }
//        tv_logout.text = Random.nextInt(100).toString()
        dataBinding.tvLogout.text = Random.nextInt(100).toString()+"共享数据："+"ViewModelProvider(context),由this改为context即可共享LiveData"
//        loginViewModel.isSuccess.observe(this, Observer {
//            tv_logout.text = Random.nextInt(100).toString()+"共享数据："+loginViewModel.isSuccess.value+"ViewModelProvider(context),由this改为context即可共享LiveData"
//        })
    }
}