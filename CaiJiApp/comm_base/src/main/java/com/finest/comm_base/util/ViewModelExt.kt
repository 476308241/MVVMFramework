package com.finest.comm_base.util

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


/**
 * Created by liangjiangze on 2021/1/5.
 */

fun ViewModel.launchScope(
    block: suspend CoroutineScope.() -> Unit ={},
    onError: (e: Throwable) -> Unit = {}
):Job {
    return viewModelScope.launch(CoroutineExceptionHandler { _, e -> onError(e) }) {
         block.invoke(this)
     }
}

fun LifecycleOwner.launchScope(block: suspend CoroutineScope.() -> Unit ={},
                           onError: (e: Throwable) -> Unit = {}){
    lifecycleScope.launch(CoroutineExceptionHandler { _, e -> onError(e) }) {
        block.invoke(this)
    }
}



