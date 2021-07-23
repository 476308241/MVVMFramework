package com.finest.jetpack.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.finest.comm_base.db.entity.User


/**
 * Created by liangjiangze on 2020/12/25.
 */
class WorkManagerViewModel:ViewModel() {
    var user: MutableLiveData<User> = MutableLiveData()

}