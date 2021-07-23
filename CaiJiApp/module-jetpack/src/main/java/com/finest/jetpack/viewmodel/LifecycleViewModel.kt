package com.finest.jetpack.viewmodel

import androidx.lifecycle.*
import timber.log.Timber

/**
 * Created by liangjiangze on 2020/12/23.
 */
class LifecycleViewModel : ViewModel() , LifecycleObserver {
    var sharedName:MutableLiveData<String> = MutableLiveData()

    init {
        sharedName.value = "anriku"
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun event(owner: LifecycleOwner, event: Lifecycle.Event) {
        sharedName.value = "LifecycleViewModel："+event.name
        Timber.d("LifecycleViewModel："+event.name)
    }
}