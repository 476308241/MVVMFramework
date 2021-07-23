package com.finest.comm_base.app

import android.content.Context
import com.blankj.utilcode.util.ThrowableUtils
import com.blankj.utilcode.util.TimeUtils
import com.finest.comm_base.bean.CrashMsgBean
import com.finest.comm_base.config.SPConfig
import com.finest.comm_base.util.SharedPref
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by liangjiangze on 2020/05/08.
 */
class MyCrashHandler(var context:Context) :Thread.UncaughtExceptionHandler{
    override fun uncaughtException(t: Thread?, e: Throwable?) {
        /**
         * 把崩溃信息存在sp列表里面
         */
        var newList = ArrayList<CrashMsgBean>()
            var crashList = SharedPref.get(context).getDataList(
                SPConfig.CRASH_MSG_LIST,
                CrashMsgBean::class.java) as ArrayList
            var bean = CrashMsgBean()

            if (e!!.message!!.isEmpty()){
                bean.createTime = TimeUtils.millis2String(Date().time)
                bean.fullStackTrace = ThrowableUtils.getFullStackTrace(e)
                bean.msg = "没有捕获到崩溃信息"
                newList.add(bean)
            }else{
                bean.createTime = TimeUtils.millis2String(Date().time)
                bean.msg = e.message!!
                bean.fullStackTrace = ThrowableUtils.getFullStackTrace(e)
                newList.add(bean)
            }
            if(crashList.size>20){
                //把最后一条删除，这样加一条删除一条，保持数量为20
                crashList.removeAt(crashList.size-1)
            }
            newList.addAll(crashList)
         SharedPref.get(context).setDataList(SPConfig.CRASH_MSG_LIST,newList)
        //延时0.5秒，避免SharedPref保存操作还没成功，app就退出了
        Thread.sleep(500)
    }

}