package com.finest.chat.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finest.chat.data.repository.LoginRepository
import com.finest.chatlibrary.entity.MessageBean
import com.finest.comm_base.util.UIState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * Created by liangjiangze on 2020/12/25.
 */
class BaseChatViewModel @ViewModelInject constructor(var loginRepository: LoginRepository):ViewModel() {
    var testData = MutableLiveData<UIState<String>>()
    fun sendTextToServer(mes: MessageBean){
        viewModelScope.launch {
            loginRepository.testTextMsg(mes).collect{
                testData.value = it
            }
        }
    }

    fun sendImageToServer(mes: MessageBean){
        viewModelScope.launch {
            loginRepository.testImageMsg(mes).collect{
                testData.value = it
            }
        }
    }
}