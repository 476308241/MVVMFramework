package com.finest.jetpack.ui

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.finest.comm_base.config.ClassNameConfig.ChatFragment
import com.finest.comm_base.config.LiveEventKey
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.comm_base.util.RoutePath
import com.finest.comm_base.util.SharedPref
import com.finest.jetpack.R
import com.finest.jetpack.databinding.FragmentJetpackBinding
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by liangjiangze on 2020/12/24.
 */
@AndroidEntryPoint
@Route(path = RoutePath.JETPACK_JETPACK)
class JetpackFragment : BaseMVVMFragment() {
    lateinit var  dataBinding: FragmentJetpackBinding
    lateinit var adapter: MvvmMainAdapter
    @Inject
    lateinit var aRouter: ARouter
    var list = ArrayList<String>()
    var titles = arrayOf(
        "DataBinding",
        "ViewModel",
        "Paging3",
        "WorkManager",
        "Lifecycle",
        "LiveData",
        "Room",
        "Flow",
        "Kotlin 协程 - Suspend 函数",
        "Repository 不用",
        "dagger:hilt-android",
        "chat聊天",
        "test页面"
    )
    companion object {
        fun getInstance(): JetpackFragment {
            return JetpackFragment()
        }
    }
    override fun getLayoutResId(): Int {
        return R.layout.fragment_jetpack
    }


    override fun getActivityTitle(): String {
        return "JetpackFragment"
    }

    override fun initView() {
        dataBinding = mViewDataBinding as FragmentJetpackBinding

        list.clear()
        adapter =
            MvvmMainAdapter(
                requireContext(),
                list
            )
        dataBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        dataBinding.recyclerView.adapter = adapter
        titles.forEach {
            list.add(it)
        }
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                0 -> {
                    aRouter.build(RoutePath.JETPACK_LAUNCH_ACTIVITY)
                        .withString(RoutePath.Route_Destination,RoutePath.JETPACK_DATABINDING)
                        .navigation()
                }
                1 -> {
                    aRouter.build(RoutePath.JETPACK_LAUNCH_ACTIVITY)
                        .withString(RoutePath.Route_Destination,RoutePath.JETPACK_VIEWMODEL)
                        .navigation()
                }
                2 -> {
//                    navigateTo(R.id.TaskFragment)
                }
                3 -> {
                    navigateTo(R.id.WorkManagerFragment)
                }
                4 -> {
                    navigateTo(R.id.LifecycleFragment)
                }
                5 -> {
//                    navigateTo(R.id.SearchFragment)
//                    LiveEventBus.get(LiveEventKey.navTo).post("")
                }
                6 -> {
                    navigateTo(R.id.RoomFragment)
                }
                8 -> {
                    navigateTo(R.id.KotlinSuspendFragment)
                }
                10 -> {
                    navigateTo(R.id.DaggerHiltFragment)
                }
                11 -> {
                    ARouter.getInstance().build(RoutePath.CHAT_LAUNCH_ACTIVITY)
                        .withString(RoutePath.Route_Destination,RoutePath.CHAT_CHAT)
                        .navigation()
                }
                12->{
//                    navigateTo(R.id.TestFragment)
                }
                else -> {
//                    Toast.makeText(mContext,position.toString(), Toast.LENGTH_LONG).show()

                }
            }
        }
    }


    class MvvmMainAdapter(var mContext: Context, var mData: ArrayList<String>) :
        BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_mvvm_main, mData) {
        override fun convert(helper: BaseViewHolder, item: String) {
            val tvTitle = helper.getView<TextView>(R.id.tv_title)
            tvTitle.text = item

        }
    }
}