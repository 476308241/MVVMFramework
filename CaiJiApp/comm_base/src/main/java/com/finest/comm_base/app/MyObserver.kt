package com.finest.comm_base.app

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import timber.log.Timber


/**
 * Created by liangjiangze on 2021/1/4.
 */
class MyObserver:LifecycleObserver {

    var tag="MyObserver"
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun create(owner: LifecycleOwner) {
        Timber.d(tag+"类"+"onCreate()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start(owner: LifecycleOwner) {
        Timber.d(tag+"类"+"onStart()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun resume(owner: LifecycleOwner) {
        Timber.d(tag+"类"+"onResume()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pause(owner: LifecycleOwner) {
        Timber.d(tag+"类"+"onPause()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stop(owner: LifecycleOwner) {
        Timber.d(tag+"类"+"onStop()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy(owner: LifecycleOwner) {
        Timber.d(tag+"类"+"onDestroy()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    fun event(owner: LifecycleOwner, event: Lifecycle.Event) {
        Timber.d(tag+"类"+event.name)
    }

}