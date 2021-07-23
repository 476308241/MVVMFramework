package com.finest.task.ui

import android.content.Context
import android.os.Handler
import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.finest.comm_base.util.PictureSelectorHelper
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.comm_base.util.RoutePath
import com.finest.comm_base.util.UIState
import com.finest.task.R
import com.finest.task.bean.TaskBean
import com.finest.task.databinding.FragmentTaskBinding
import com.finest.task.databinding.ItemTaskBinding
import com.finest.task.viewmodel.TaskViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import kotlin.random.Random

/**
 * Created by liangjiangze on 2020/12/24.
 */
@AndroidEntryPoint
@Route(path = RoutePath.TASK_TASK)
class TaskFragment : BaseMVVMFragment() {
    private val viewModel by viewModels<TaskViewModel>()
    lateinit var  dataBinding: FragmentTaskBinding
    lateinit var adapter: TaskAdapter
    var list = ArrayList<TaskBean>()
    lateinit var pictureSelectorHelper: PictureSelectorHelper
    companion object {
        fun getInstance(): TaskFragment {
            return TaskFragment()
        }
    }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_task
    }

    override fun getActivityTitle(): String {
        return "TaskFragment"
    }

    override fun initView() {
        dataBinding = mViewDataBinding as FragmentTaskBinding
        dataBinding.viewModel = viewModel

         pictureSelectorHelper = PictureSelectorHelper(requireActivity())
        pictureSelectorHelper.initRecyclerView(dataBinding.recyclerView)
        var pictureSelectorHelper2 = PictureSelectorHelper(requireActivity())
        pictureSelectorHelper2.initRecyclerView(dataBinding.recyclerView2)
        initAdapter()
    }

    private fun initAdapter(){
        list.clear()
        adapter =
            TaskAdapter(
                requireContext(),
                list
            )
        dataBinding.recyclerViewList.layoutManager = LinearLayoutManager(requireContext())
        dataBinding.recyclerViewList.adapter = adapter

        dataBinding.tvGet.setOnClickListener {
            displayLoadingProgressBar()
            onRefreshView()

        }
        dataBinding.srl.setOnRefreshListener {
            displayLoadingProgressBar()
            list.clear()
            onRefreshView()
            Handler().postDelayed({
                dataBinding.srl.isRefreshing = false
            }, 100)
        }
        adapter.loadMoreModule.setOnLoadMoreListener {
            onRefreshView()
        }
        viewModel.taskList.observe(this, Observer {
//            adapterDataSetChanged()
            hideLoadingProgressBar()
            when(it){
                is UIState.Success ->{
                   var beanList =  ArrayList<TaskBean>()
                   for (i in 0..2){
                   var bean = TaskBean()
                   bean.type = Random.nextInt(100).toString()+"Type"
                   bean.taskName = Random.nextInt(100).toString()+"千万元"
                   bean.url = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"
                       beanList.add(bean)
                     }
                    list.addAll(beanList)
                    if (list.size>20){
                        adapter.loadMoreModule.loadMoreEnd()
                    }else{
                        adapter.loadMoreModule.loadMoreComplete()
                    }
                    adapter.notifyDataSetChanged()
                }
                is UIState.Error ->{
                    if (viewModel.page==2){
                        showRefreshText()
                    }else{
                        adapter.loadMoreModule.loadMoreFail()
                        adapter.notifyDataSetChanged()
                    }
                }
                else ->{

                }
            }
        })


    }

    override fun onRefreshView() {
        viewModel.getList()
    }

    fun onRightClick(){
        showToast("点击右键")
        var filed  = pictureSelectorHelper.javaClass.getDeclaredField("haha")
        filed.isAccessible = true
        Log.e("test",  filed.get(pictureSelectorHelper).toString())
        var method =   pictureSelectorHelper.javaClass.getDeclaredMethod("haha")
        method.isAccessible = true
        method.invoke(pictureSelectorHelper)
    }

    class TaskAdapter(var mContext: Context, var mData: ArrayList<TaskBean>) :
        BaseQuickAdapter<TaskBean, BaseDataBindingHolder<ItemTaskBinding>>(R.layout.item_task, mData),LoadMoreModule {
        override fun convert(helper: BaseDataBindingHolder<ItemTaskBinding>, item: TaskBean) {
                helper.dataBinding!!.item = item
        }
    }
}