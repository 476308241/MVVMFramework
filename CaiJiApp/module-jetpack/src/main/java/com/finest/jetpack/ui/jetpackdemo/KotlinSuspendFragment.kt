package com.finest.jetpack.ui.jetpackdemo

import android.util.Log
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.comm_net.NetworkManager
import com.finest.jetpack.databinding.FragmentKotlinSuspendBinding
import com.finest.jetpack.viewmodel.KotlinSuspendViewModel
import kotlinx.coroutines.*
import com.finest.jetpack.R
import dagger.hilt.android.AndroidEntryPoint


/**
 * Created by liangjiangze on 2020/12/24.
 *
 * 主要解决1.顺序执行多个网络请求，更新UI，不需要回调嵌套（launch方法）   2.异步执行多个请求，更新UI，不需要回调嵌套（async方法）
 * 3.顺序执行和异步执行在一起的复杂多个请求（launch,async混用）
 * 参考https://blog.csdn.net/NJP_NJP/article/details/103524778
 * 参考https://baijiahao.baidu.com/s?id=1618694818136024116&wfr=spider&for=pc
 * lifecycleScope用于Activity或Fragment
 *  viewModelScope用于ViewModel中
 *  GlobalScope是生命周期是process级别的，即使Activity或Fragment已经被销毁，协程仍然在执行，尽量不用
 *
 *  FLOW使用：https://www.jianshu.com/p/0696c7252b50
 *  Flow 是对 Kotlin 协程的扩展，让我们可以像运行同步代码一样运行异步代码，使得代码更加简洁，提高了代码的可读性
 */
@AndroidEntryPoint
class KotlinSuspendFragment: BaseMVVMFragment() {
    override fun getLayoutResId(): Int {
        return R.layout.fragment_kotlin_suspend
    }

    override fun getActivityTitle(): String {
        return ""
    }
    override fun initView() {
        val model by viewModels<KotlinSuspendViewModel>()
        var dataBinding = mViewDataBinding as FragmentKotlinSuspendBinding
        dataBinding.viewModel = model

        dataBinding.tvClick.setOnClickListener {
//          runBlocking {
//              repeat(8){
//                  launch {
//                    //开启协程
//                      delay(1000L)
//                     Log.e("TEST","22")
//                  }
//              }
//          }
     netWorkTest()
        }
        model.sharedName.observe(this, Observer {
            dataBinding.viewModel = model
        })

    }

    fun test(){
        var job = GlobalScope.launch {
         val result = async {
             taskFun()
         }

        }
        job.cancel()
    }
    fun taskFun(){
        //耗时操作
    }

    fun netWorkTest(){
        GlobalScope.launch {
            val result = async {
                //同步网络请求
            }
            //拿到结果直接做处理
           result.await()
        }
//        NetWorkManager.getRequest("http://113.108.133.227:28084").login(
//            "wwk", "1",
//            System.currentTimeMillis().toString()
//        )   .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                val code = GetJSONHelper.getCode(it)
//                if (code == 1) {
//                    isSuccess.value = true
//                    txt.value = "--登录成功--"
//                    val token = GetJSONHelper.getKeyString(it, arrayOf(ConstantConfig.FINEST_TOKEN))
//                    NetWorkCommonParams.instance.addCommonParams(ConstantConfig.FINEST_TOKEN, token)
//                } else {
//                    throw Exception()
//                }
//            }, {
//                isSuccess.value = true
//                txt.value = "--登录成功--"
//            })


//                lifecycleScope.launch {
//               try {
//                   val result = NetWorkManager.getRequest("https://api.apiopen.top/").testApi(14)
//                   Log.e("TEST","跑了成功"+result)
//               }catch (e:Exception){
//                   Log.e("TEST","跑了失败"+e.message)
//               }

//       }

//        launchScope({
//            val result = NetworkManager.getRequest("https://api.apiopen.top/").testApi(14)
//            Log.e("TEST","跑了成功"+result)
//        },{
//            Log.e("TEST","跑了失败"+it.message)
//        })

    }



}