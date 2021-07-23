package com.finest.caijiapp.viewmodel

import android.annotation.SuppressLint
import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*

import com.finest.chat.data.repository.LoginRepository
import com.finest.comm_base.config.SPConfig
import com.finest.comm_base.di.NetworkModule
import com.finest.comm_base.util.SharedPref
import com.finest.comm_base.util.UIState
import com.finest.comm_net.request.HttpRequest
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * Created by liangjiangze on 2020/12/25.
 */
class LoginViewModel @ViewModelInject constructor(var loginRepository: LoginRepository, @NetworkModule.WarnUrlRequest var request: HttpRequest,
                                                  var sharedPref: SharedPref
): ViewModel() {
    val userName = ObservableField<String>()
    val password = ObservableField<String>()
    var isSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var txt: MutableLiveData<String> = MutableLiveData()
    var loginData = MutableLiveData<UIState<String>>()
    private var loginJob: Job? = null

    @SuppressLint("CheckResult")
    fun login() {
        val isRemember =sharedPref.getBoolean(SPConfig.IS_REMEMBER_PSW, false)
//       NetWorkManager.getRequest("http://113.108.133.227:28084").login(
//           "wwk", "1",
//           System.currentTimeMillis().toString()
//       ).subscribeOn(Schedulers.io())
//           .observeOn(AndroidSchedulers.mainThread())
//           .subscribe({
//               val code = GetJSONHelper.getCode(it)
//               if (code == 1) {
//                   isSuccess.value = true
//                   txt.value = "--登录成功--"
//                   val token = GetJSONHelper.getKeyString(it, arrayOf(ConstantConfig.FINEST_TOKEN))
//                   NetWorkCommonParams.instance.addCommonParams(ConstantConfig.FINEST_TOKEN, token)
//               } else {
//                   throw Exception()
//               }
//           }, {
//               isSuccess.value = false
//               txt.value = "--登录成功--"
//               Log.e("TEST","跑了--->"+it.message)
//           })
//   }

//       GlobalScope.launch {
//           try {
//               Log.e("TEST","跑了")
//               var result = NetWorkManager.getRequest("http://113.108.133.227:28084").login(
//                   "wwk", "1",
//                   System.currentTimeMillis().toString()
//               ).await()
//               val code = GetJSONHelper.getCode(result)
//               Log.e("TEST","跑了"+result)
//               if (code == 1) {
//                   isSuccess.value = true
//                   txt.value = "--登录成功--"
//                   val token = GetJSONHelper.getKeyString(result, arrayOf(ConstantConfig.FINEST_TOKEN))
//                   NetWorkCommonParams.instance.addCommonParams(ConstantConfig.FINEST_TOKEN, token)
//               } else {
//                   throw Exception()
//               }
//           } catch (e: Exception) {
//               Log.e("TEST","跑了"+e.message)
//               isSuccess.value = true
//               txt.value = "--登录成功--"
//           }
//       }

//       GlobalScope.launch {
//           var result = withContext(Dispatchers.IO) {
//               try {
//                   NetWorkManager.getRequest("http://113.108.133.227:28084").login(
//                       "wwk", "1",
//                       System.currentTimeMillis().toString()
//                   ).await()
//               }catch (e:Exception){
//                   Log.e("TEST","跑了"+e.message)
//               }
//
//           }
//           Log.e("TEST","跑了--->"+result)
//       }
//   }
//       var job =  GlobalScope.launch((Dispatchers.Main)){
//           try {
//               var it = NetWorkManager.getRequest("https://api.apiopen.top/").testApi(14)
//               val code = GetJSONHelper.getCode(it)
//               if (code==1){
//                   var it2 = NetWorkManager.getRequest("https://api.apiopen.top/").testApi(14)
//                   val code2 = GetJSONHelper.getCode(it2)
//                   if (code2 ==1){
//
//                   }else{
//                       val msg = GetJSONHelper.getMsg(it2)
//                       throw RuntimeException(msg)
//                   }
//               }else{
//                   val msg = GetJSONHelper.getMsg(it)
//                   throw RuntimeException(msg)
//               }
//
////               if (code == 1) {
//////                   isSuccess.value = true
//////                   txt.value = "--登录成功--"
////                   val token = GetJSONHelper.getKeyString(it, arrayOf(ConstantConfig.FINEST_TOKEN))
//////                   NetWorkCommonParams.instance.addCommonParams(ConstantConfig.FINEST_TOKEN, token)
////
////               } else {
////                   Log.e("TEST","跑了--->Exception")
////                   throw RuntimeException("出错了")
////               }
//
//           } catch (e: Exception) {
//               isSuccess.value = true
//               txt.value = "--登录成功--"
//               Log.e("TEST","跑了--->"+e.message)
//           }
//       }

//       viewModelScope.launch {
//           try {
//               val it = NetWorkManager.getRequest("https://api.apiopen.top/").testApi(14)
//               val code = GetJSONHelper.getCode(it)
//               if (code == 200) {
//                   isSuccess.value = true
//                   txt.value = "--登录成功--"
//                   val token = GetJSONHelper.getKeyString(it, arrayOf(ConstantConfig.FINEST_TOKEN))
//                   NetWorkCommonParams.instance.addCommonParams(ConstantConfig.FINEST_TOKEN, token)
//               } else {
//                   val msg = GetJSONHelper.getMsg(it)
//                   throw RuntimeException(msg)
//               }
//           } catch (e: Exception) {
//               ToastUtils.showShort(e.message)
//               isSuccess.value = false
//               Log.e("TEST","跑了--->"+e.message)
//           }
//       }

//       viewModelScope.launch(CoroutineExceptionHandler{
//           _,e->
//               ToastUtils.showShort(e.message)
//               isSuccess.value = false
//               Log.e("TEST","跑了--->"+e.message)
//       }) {
//               val it = NetWorkManager.getRequest("https://api.apiopen.top/").testApi(14)
//               val code = GetJSONHelper.getCode(it)
//               if (code == 200) {
//                   isSuccess.value = true
//                   txt.value = "--登录成功--"
//                   val token = GetJSONHelper.getKeyString(it, arrayOf(ConstantConfig.FINEST_TOKEN))
//                   NetWorkCommonParams.instance.addCommonParams(ConstantConfig.FINEST_TOKEN, token)
//               } else {
//                   val msg = GetJSONHelper.getMsg(it)
//                   throw RuntimeException(msg)
//               }
//       }
//
//        launchScope({
//            val it = NetworkManager.getRequest("https://api.apiopen.top/").testApi(14)
//            GetJSONHelper.checkCode(it)
//            val token = GetJSONHelper.getKeyString(it, arrayOf(ConstantConfig.FINEST_TOKEN))
//            NetWorkCommonParams.instance.addCommonParams(ConstantConfig.FINEST_TOKEN, token)
//            val code = GetJSONHelper.getCode(it)
//            val it2 = NetworkManager.getRequest("https://api.apiopen.top/").testApi(code)
//            GetJSONHelper.checkCode(it2)
//            isSuccess.value = false
//            txt.value = "--登录成功--"
//        }, {
//            ToastUtils.showShort(it.message)
//            isSuccess.value = false
//        })

//            launchScope({
//
//        }, {
//
//        })
//        viewModelScope.launch {
//            flow {
//                val it = loginRepository.login(userName.get().toString(),password.get().toString()) as LiveData<String>
//                GetJSONHelper.checkCode(it.value!!)
//                val it2 = loginRepository.login(userName.get().toString(),password.get().toString()) as LiveData<String>
//                GetJSONHelper.checkCode(it2.value!!)
//                emit(it2)
//            }
//        }

//        viewModelScope.launch {
//            flow {
//                emit(UIState.loading<String>())
//                flow {
//                    val it = request.testFLOW(55)
//                    GetJSONHelper.checkCode(it)
//                    val it2 = request.testFLOW(GetJSONHelper.getCode(it))
//                    GetJSONHelper.checkCode(it2)
//                    emit(it2)
//                }.catch {
//                    emit(UIState.error<String>(it.message.toString()))
//                }.collect {
//                    GetJSONHelper.checkCode(it)
//                    emit(UIState.success(it))
//                }
//            }.collect {
//                loginData.value = it
//            }
//        }


         var job = viewModelScope.launch {
              loginRepository.loginMore(userName.get().toString(),password.get().toString()).collect{
                loginData.value = it
              }
         }
//         var t = runBlocking {
//             loginRepository.loginMore(userName.get().toString(),password.get().toString()).collect{
//                 loginData.value = it
//             }
//         }

         var async = viewModelScope.async{

         }

//
//       loginJob?.cancel()
//       loginJob = launchScope({
//            val it = request.testApi(14)
//            GetJSONHelper.checkCode(it)
//            val token = GetJSONHelper.getKeyString(it, arrayOf(ConstantConfig.FINEST_TOKEN))
//            NetWorkCommonParams.instance.addCommonParams(ConstantConfig.FINEST_TOKEN, token)
//            val code = GetJSONHelper.getCode(it)
//            val it2 = request.testApi(code)
//            GetJSONHelper.checkCode(it2)
//            ToastUtils.showShort(isRemember.toString())
//            isSuccess.value = true
//            txt.value = "--登录成功--"
//        }, {
//            ToastUtils.showShort(it.message)
//            isSuccess.value = false
//        })

//          getLoginData()
//          var initFlow = flow {
//              var it = NetWorkManager.getRequest("https://api.apiopen.top/").testFLOW(14)
//              emit(it)
//           }

//           var it = getValueForm(NetWorkManager.getRequest("https://api.apiopen.top/").testFLOW(14))
//            GetJSONHelper.checkCode(it)
//            val token = GetJSONHelper.getKeyString(it, arrayOf(ConstantConfig.FINEST_TOKEN))
//            NetWorkCommonParams.instance.addCommonParams(ConstantConfig.FINEST_TOKEN, token)
//            val code = GetJSONHelper.getCode(it)
//            val it2 = NetWorkManager.getRequest("https://api.apiopen.top/").testApi(code)
//            GetJSONHelper.checkCode(it2)
//            isSuccess.value = true
//            txt.value = "--登录成功--"

    }

//     fun getLoginData() {
//         //FLOW使用  emit多个suspend函数，是结果是按顺序返回的，也就是前面的没返回之前，后面的不会返回，
//         //前面的出错,会影响后面的emit   如果不用flow，很难实现这种效果。
//         //使用场景，多个ui请求，有一个出错，就不更新UI ,用协程的asyn
//         // https://copyfuture.com/blogs-details/202011121235317609k2ima64muqxs7o
//         viewModelScope.launch {
//             flow {
//                 emit(haha())
//                 emit(request.testFLOW(201))
//                 emit(request.testFLOW(202))
//                 emit(request.testFLOW(203))
//                 emit(request.testFLOW(204))
//                 emit(request.testFLOW(205))
//                 emit(request.testFLOW(206))
//                 emit(request.testFLOW(207))
//                 emit(request.testFLOW(208))
//                 emit(request.testFLOW(209))
//                 emit(request.testFLOW(210))
//                 emit(haha())
//                 emit(request.testFLOW(211))
//                 emit(request.testFLOW(212))
//                 emit(request.testFLOW(213))
//                 emit(request.testFLOW(214))
//                 emit(request.testFLOW(215))
//                 emit(request.testFLOW(216))
//                 emit(request.testFLOW(217))
//                 emit(request.testFLOW(218))
//                 emit(request.testFLOW(219))
//                 emit(request.testFLOW(220))
//                 emit(request.testFLOW(221))
//                 emit(request.testFLOW(222))
//                 emit(request.testFLOW(223))
//                 emit(request.testFLOW(224))
//                 emit(request.testFLOW(225))
//                 emit(request.testFLOW(226))
//                 emit(request.testFLOW(227))
//                 emit(request.testFLOW(228))
//                 emit(request.testFLOW(229))
//                 emit(request.testFLOW(230))
//             }.catch {
//                     ToastUtils.showShort(it.message)
//                     isSuccess.value = false
//                 }.collect {
////                     GetJSONHelper.checkCode(it)
////                     val token = GetJSONHelper.getKeyString(it, arrayOf(ConstantConfig.FINEST_TOKEN))
////                     NetWorkCommonParams.instance.addCommonParams(ConstantConfig.FINEST_TOKEN, token)
////                     val code = GetJSONHelper.getCode(it)
////                     val it2 = request.testApi(code)
////                     GetJSONHelper.checkCode(it2)
////                     isSuccess.value = true
////                     txt.value = "--登录成功--"
//
//                 Log.e("TEST",it.toString())
//                 isSuccess.value = false
//
//                 }
//         }

//         launchScope({
////            val it = async {NetworkManager.getRequest("https://api.apiopen.top/").testApi(14)}
////            GetJSONHelper.checkCode(it)
////            val it2 = async {NetworkManager.getRequest("https://api.apiopen.top/").testApi(200)}
////            GetJSONHelper.checkCode(it2)
////            isSuccess.value = true
////            txt.value = "--登录成功--"
//        }, {
//            ToastUtils.showShort(it.message)
//            isSuccess.value = false
//        })

//         viewModelScope.launch {
//             testFlow()
//         }
//    }
//    suspend fun haha():String{
//        return "我的运行不耗时"
//    }
//
    /**
     * 1.网络请求的返回值不是Flow<>,而是返回结果转化为FLow
     */
//    suspend fun testFlow():Flow<String> = flow {
//       emit(request.testFLOW(55))
//
//    }



}