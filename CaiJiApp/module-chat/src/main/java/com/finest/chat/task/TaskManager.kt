package com.finest.chat.task

import android.util.Log
import com.finest.chat.config.ClassNameConfig
import com.finest.chat.config.LiveEventKey
import com.finest.chat.config.LiveEventValue
import com.finest.chat.data.repository.LoginRepository
import com.finest.comm_base.db.dao.DispatcherInfoDao
import com.finest.comm_base.db.dao.UserDao
import com.finest.comm_base.db.entity.DispatcherInfo

import com.finest.comm_base.util.UIState
import com.jeremyliao.liveeventbus.LiveEventBus
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by liangjiangze on 2021/1/20.
 */
@Singleton
class TaskManager @Inject constructor(var loginRepository: LoginRepository, var userDao: UserDao, var dispatcherInfoDao: DispatcherInfoDao){

    fun loginMore(){
        GlobalScope.launch(CoroutineExceptionHandler { _, e ->
            Timber.e(e.message)
        }) {
            loginRepository!!.loginMore("","").collect {
                when(it){
                    is UIState.Success ->{
                        var list = userDao.getAllUsers()
                        Timber.e("保存数据库${list.size}")
//                        LiveEventBus.get(ClassNameConfig.ChatFragment).post(LiveEventValue(LiveEventKey.have_new_msg,""))
                    }
                    is UIState.Error ->{
                    }
                    else -> {

                    }

                }
            }
        }
    }
    //同步指挥云所有数据
    fun syncPoliceDispatchBuzz(){
        GlobalScope.launch(CoroutineExceptionHandler { _, e ->
            Timber.e(e.message)
        }) {
            loginRepository!!.loginMore("","").collect {
                when(it){
                    is UIState.Success ->{
                        //如果存在新的指挥云警情调度

                        //如果存在新的指挥云警情会话，进去同步警情会话请求
                         syncPoliceDispatchMessage()

                        //如果存在新的嫌疑人审批状态
                    }
                    is UIState.Error ->{
                    }
                    else -> {

                    }

                }
            }
        }
    }
    //同步警情会话
   private fun syncPoliceDispatchMessage(){
        GlobalScope.launch(CoroutineExceptionHandler { _, e ->
            Timber.e(e.message)
        }) {
            loginRepository!!.loginMore("","").collect {
                when(it){
                    is UIState.Success ->{
                        var dispatcherInfo = DispatcherInfo()
                        var xdbh = "111"
                        var policeNo ="222"
                        var groudId = "333"
                        var deptCode = "3333"
                        var state = 0

                        dispatcherInfo.groupSid = groudId
                        dispatcherInfo.caseNo ="111111"
                        dispatcherInfo.xdbh = xdbh
                        dispatcherInfo.policeNo =policeNo
                        dispatcherInfo.orgCode = deptCode
                        dispatcherInfo.status  =state

                        dispatcherInfo.content = UUID.randomUUID().toString()+"随机"
                        dispatcherInfo.infoType = "0"
                        dispatcherInfo.infoFlow = "0"

                        //保存到数据库
                        dispatcherInfoDao.insert(dispatcherInfo)
                        Log.e("TEST","保存数据库成功"+dispatcherInfoDao!!.getDispatcherInfoByGroudId()!!.size)
                        LiveEventBus.get(ClassNameConfig.ChatFragment).post(LiveEventValue(LiveEventKey.have_new_msg,""))
                    }
                    is UIState.Error ->{
                    }
                    else -> {

                    }

                }
            }
        }
    }
}