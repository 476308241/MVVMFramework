package com.finest.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finest.comm_net.NetworkManager

import kotlin.random.Random

/**
 * Created by liangjiangze on 2020/12/25.
 */
class DaggerHiltViewModel:ViewModel() {
    var page = 1
//    var taskList: MutableLiveData<ArrayList<TaskBean>> = MutableLiveData()
//   fun getList() {
//       launchScope({
//           val it = NetworkManager.getRequest("https://api.apiopen.top/").testApi(14)
//           var list =  ArrayList<TaskBean>()
//           for (i in 0..2){
//               var bean = TaskBean()
//               bean.type = Random.nextInt(100).toString()+"Type"
//               bean.taskName = Random.nextInt(100).toString()+"千万元"
//               bean.url = "https://www.baidu.com/img/PCtm_d9c8750bed0b3c7d089fa7d55720d6cf.png"
//               list.add(bean)
//           }
//           taskList.value = list
//       },{
//           taskList.value = null
//       })
//
//    }
}