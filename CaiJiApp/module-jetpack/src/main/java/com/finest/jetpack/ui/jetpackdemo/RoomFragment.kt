package com.finest.jetpack.ui.jetpackdemo

import androidx.lifecycle.ViewModelProvider
import com.finest.comm_base.db.dao.UserDao
import com.finest.comm_base.db.entity.User
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.jetpack.R
import com.finest.jetpack.databinding.FragmentRoomBinding
import com.finest.jetpack.viewmodel.RoomViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Created by liangjiangze on 2020/12/24.
 */

@AndroidEntryPoint
class RoomFragment: BaseMVVMFragment() {
    @Inject
    lateinit var dao: UserDao
    override fun getLayoutResId(): Int {
        return R.layout.fragment_room
    }

    override fun getActivityTitle(): String {
        return ""
    }

    override fun initView() {
        var viewModel = ViewModelProvider(this)[RoomViewModel::class.java]
        var dataBinding = mViewDataBinding as FragmentRoomBinding

        var user = User()
        user.userName = "userName哈哈"
        user.address = "地址喝喝"
        // TODO: 2021/1/7 改造为dragger
//        AppDatabase.getInstance(mContext).userDao().addUser(user)
//
//        AppDatabase.getInstance(mContext).userDao().getAllUsers().forEach { it ->
//            var user = User()
//            user.userName = it.userName
//            user.address = it.address
//            viewModel.user.value = user
//        }
        dao.addUser(user)
        dao.getAllUsers().forEach { it ->
            var user = User()
            user.userName = it.userName
            user.address = it.address
            viewModel.user.value = user
        }
        dataBinding.viewModel = viewModel


    }

}