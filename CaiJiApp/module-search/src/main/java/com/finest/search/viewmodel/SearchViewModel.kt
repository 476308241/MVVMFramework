package com.finest.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finest.search.bean.UserBean
import kotlin.random.Random

/**
 * Created by liangjiangze on 2020/12/25.
 */
class SearchViewModel:ViewModel() {

    var userBean: MutableLiveData<UserBean> = MutableLiveData()

    fun getUserBean22(){
        var bean = UserBean()
        bean.name = "name"+ Random.nextInt(100).toString()
        bean.bm = "bm"+ Random.nextInt(100).toString()
        userBean.value  = bean
    }

}