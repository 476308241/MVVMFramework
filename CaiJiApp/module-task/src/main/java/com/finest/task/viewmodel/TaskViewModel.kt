package com.finest.task.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.finest.comm_base.util.UIState
import com.finest.task.data.repository.TaskRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * Created by liangjiangze on 2020/12/25.
 */
class TaskViewModel @ViewModelInject constructor(var taskRepository: TaskRepository):ViewModel() {
    var page = 1
    var taskList: MutableLiveData<UIState<String>> = MutableLiveData()
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

     fun getList(){
         viewModelScope.launch {
             taskRepository.getList().collect{
                 taskList.value = it
             }
         }
     }
}