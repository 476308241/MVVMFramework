package com.finest.jetpack.ui.jetpackdemo

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.work.*
import com.finest.comm_base.db.entity.User
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.jetpack.databinding.FragmentWorkManagerBinding
import com.finest.jetpack.viewmodel.WorkManagerViewModel
import com.finest.jetpack.workmanager.MyWork
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import com.finest.jetpack.R


/**
 * Created by liangjiangze on 2020/12/24.
 * 可参考https://blog.csdn.net/LitterLy/article/details/105454655
 * https://www.jianshu.com/p/4889e374a9f7?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation
 */
@AndroidEntryPoint
class WorkManagerFragment: BaseMVVMFragment() {
    lateinit var viewModel : WorkManagerViewModel
    lateinit var dataBinding : FragmentWorkManagerBinding

    private var constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED) //联网状态
        .setRequiresBatteryNotLow(true) //低电量不操作
        .setRequiresCharging(true) //充电时候才开始
        //     .setRequiresDeviceIdle(true)//待机状态下才执行，api 23 以上
        .setRequiresStorageNotLow(true) //存储空间不能太小
        .build()
    override fun getLayoutResId(): Int {
        return R.layout.fragment_work_manager
    }

    override fun getActivityTitle(): String {
        return ""
    }

    override fun initView() {
        viewModel= ViewModelProvider(this)[WorkManagerViewModel::class.java]
        dataBinding = mViewDataBinding as FragmentWorkManagerBinding
        initWork()
    }
    private fun initWork(){
        WorkManager.getInstance(mContext).enqueue(getOneTimeRequest())
    }
    private fun getOneTimeRequest(): OneTimeWorkRequest {
        val sendData = Data.Builder().putString("key", "1234").build()
        var oneTimeRequest = OneTimeWorkRequest.Builder(MyWork::class.java)
            .setConstraints(constraints) //添加环境约束 ，比如网络、电量等
            .setInputData(sendData).build()
        observerWorkRequest(oneTimeRequest, 1)
        return oneTimeRequest
    }

    private val request: WorkRequest =
        PeriodicWorkRequest.Builder(MyWork::class.java, 15, TimeUnit.MINUTES)
            .setBackoffCriteria(BackoffPolicy.LINEAR, 20, TimeUnit.MINUTES)
            .build()

    private fun observerWorkRequest(request: WorkRequest, type: Int) {
        WorkManager.getInstance(mContext).getWorkInfoByIdLiveData(request.id)
            .observe(this, Observer<WorkInfo> { t ->
                val status = t?.state
                Log.e("TEST", "work status ->> $status  $type ")
                status?.isFinished?.apply {
                    if (this) {
                        val key = t!!.outputData.getString("dataKey")
                        if (key != null) {
                            Log.e("TEST", "执行完成 $key")
                            var user = User()
                            user.userName = "执行完成  $key"
                            viewModel.user.value = user
                            dataBinding.viewModel = viewModel
                        }
                    }
                }
            })
    }


}