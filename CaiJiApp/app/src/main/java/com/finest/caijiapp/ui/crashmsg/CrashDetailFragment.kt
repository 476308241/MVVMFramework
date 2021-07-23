package com.finest.caijiapp.ui.crashmsg


import com.finest.caijiapp.R
import com.finest.comm_base.bean.CrashMsgBean
import com.finest.comm_base.config.IntentConfig
import com.finest.comm_base.config.SPConfig
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.comm_base.util.SharedPref
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_crash_detail.*
import javax.inject.Inject

@AndroidEntryPoint
class CrashDetailFragment : BaseMVVMFragment(){
    @Inject
    lateinit var sharedPref: SharedPref
    override fun getLayoutResId(): Int {
        return R.layout.fragment_crash_detail
    }

    override fun getActivityTitle(): String {
        return "崩溃详情"
    }

    override fun initView() {
        var crashList = sharedPref.getDataList(
            SPConfig.CRASH_MSG_LIST,
            CrashMsgBean::class.java) as ArrayList
       var pos =  mContext.intent.getIntExtra(IntentConfig.INTENT_POSITION,0)
        refreshView(crashList[pos])
    }
    private fun refreshView(bean: CrashMsgBean){
        tv_create_time.text = bean.createTime
        tv_fullStackTrace.text = bean.fullStackTrace
    }

}
