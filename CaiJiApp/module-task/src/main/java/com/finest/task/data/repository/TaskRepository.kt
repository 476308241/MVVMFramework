package com.finest.task.data.repository

import com.finest.comm_base.di.NetworkModule
import com.finest.comm_base.util.GetJSONHelper
import com.finest.comm_base.util.UIState
import com.finest.comm_net.request.HttpRequest
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Created by liangjiangze on 2021/1/12.
 */
class TaskRepository @Inject constructor(@NetworkModule.WarnUrlRequest var request: HttpRequest){
//    suspend fun loginImpl(userName:String,psw:String):Flow<String> = flow { emit(request.testFLOW(55)) }
//    suspend fun login2(userName:String,psw:String): Flow<UIState<String>> = flow {
//        loginImpl(userName,psw).catch {
//            ToastUtils.showShort(it.message)
//            emit(UIState.error<String>(it.message.toString()))
//        }.collect {
//            GetJSONHelper.checkCode(it)
//            emit(UIState.success(it))
//        }
//    }
   fun login(userName:String,psw:String): Flow<UIState<String>> = flow {
        emit(UIState.loading())
        flow {
            emit(request.testFLOW(55))
        }.catch {
            emit(UIState.error<String>(it.message.toString()))
        }.collect {
         GetJSONHelper.checkCode(it)
         emit(UIState.success(it))
        }

    }

    fun loginMore(userName:String,psw:String): Flow<UIState<String>> = flow {
        emit(UIState.loading())
        flow {
            val it = request.testFLOW(55)
            GetJSONHelper.checkCode(it)
            val it2 = request.testFLOW(GetJSONHelper.getCode(it))
            GetJSONHelper.checkCode(it2)
            emit(it2)
        }.catch {
            emit(UIState.error<String>(it.message.toString()))
        }.collect {
            val code = GetJSONHelper.getCode(it)
            if (code == 200 || code == 1) {
                emit(UIState.success(it))
            } else {
                emit(UIState.error<String>(GetJSONHelper.getMsg(it)))
            }

        }

    }

    fun loginMore222(userName:String,psw:String): Flow<UIState<String>> = flow {
        emit(UIState.loading())
        flow {
            val it = request.testFLOW(55)
            GetJSONHelper.checkCode(it)
            val it2 = request.testFLOW(GetJSONHelper.getCode(it))
            GetJSONHelper.checkCode(it2)
            emit(it2)
        }.catch {
            emit(UIState.error<String>(it.message.toString()))
        }.collect {
            GetJSONHelper.checkCode(it)
            emit(UIState.success(it))
        }

    }


     fun getList(): Flow<UIState<String>> = flow {
        flow {
            emit(request.testFLOW(55))
        }.catch {
            emit(UIState.error<String>(it.message.toString()))
        }.collect {
            GetJSONHelper.checkCode(it)
            emit(UIState.success(it))
        }

    }





}