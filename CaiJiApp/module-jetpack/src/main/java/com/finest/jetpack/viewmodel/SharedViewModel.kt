package com.finest.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by liangjiangze on 2020/12/23.
 */
class SharedViewModel : ViewModel() {
    var sharedName:MutableLiveData<String> = MutableLiveData()

    init {
        sharedName.value = "anriku"
    }


}