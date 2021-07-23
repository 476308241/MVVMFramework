package com.finest.caijiapp.ui.crashmsg

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder
import com.finest.caijiapp.R
import com.finest.comm_base.bean.CrashMsgBean
import com.finest.comm_base.config.SPConfig
import com.finest.caijiapp.databinding.ItemCrashMsgBinding
import com.finest.comm_base.config.IntentConfig
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.comm_base.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crash_list.*

/**
 * Created by liangjiangze on 2020/05/08.
 */
@AndroidEntryPoint
class CrashListFragment : BaseMVVMFragment() {
    private lateinit var adapter: CrashListAdapter
    override fun getLayoutResId(): Int {
        return R.layout.fragment_crash_list
    }

    override fun getActivityTitle(): String {
        return "崩溃列表"
    }

    override fun initView() {
        initRecyclerView()
    }

    private fun initRecyclerView(){
        var crashList = SharedPref.get(mContext).getDataList(
            SPConfig.CRASH_MSG_LIST,
            CrashMsgBean::class.java) as ArrayList
        adapter = CrashListAdapter(crashList)
        recycler_view.layoutManager = LinearLayoutManager(context)
        recycler_view.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
              var bundle = Bundle()
              bundle.putInt(IntentConfig.INTENT_POSITION,position)
             navigateTo(R.id.CrashDetailFragment,bundle)
        }
    }

    inner class CrashListAdapter(data:ArrayList<CrashMsgBean>):
        BaseQuickAdapter<CrashMsgBean, BaseDataBindingHolder<ItemCrashMsgBinding>>(R.layout.item_crash_msg,data){
        override fun convert(holder: BaseDataBindingHolder<ItemCrashMsgBinding>, item: CrashMsgBean) {
            item.createTime = (holder.layoutPosition+1).toString()+"）时间："+item!!.createTime
            item.msg =  "     原因："+item!!.msg
            holder.dataBinding!!.item = item
        }

    }
}
