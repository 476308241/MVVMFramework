package com.finest.caijiapp.ui

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.finest.caijiapp.R
import com.finest.comm_base.widget.dialog.PermissionDialog
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.hjq.permissions.OnPermission
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions

class WellingFragment : BaseMVVMFragment(){

    companion object {
        private const val SHOW_TIME_MIN = 1000// 最小显示时间
    }

    override fun getLayoutResId(): Int {
        return R.layout.fragment_weling
    }

    override fun getActivityTitle(): String {
        return ""
    }


    val handler = @SuppressLint("HandlerLeak")
    object : Handler(Looper.getMainLooper()) {
        @SuppressLint("HandlerLeak")
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            navigateTo(R.id.LoginFragment)
        }

    }
    override fun initView(){
        XXPermissions.with(mContext)
            //.constantRequest() //可设置被拒绝后继续申请，直到用户授权或者永久拒绝
            //.permission(Permission.SYSTEM_ALERT_WINDOW, Permission.REQUEST_INSTALL_PACKAGES) //支持请求6.0悬浮窗权限8.0请求安装权限
            .permission(Permission.Group.STORAGE, Permission.Group.LOCATION) //不指定权限则自动获取清单中的危险权限
            .permission(Permission.CAMERA, Permission.READ_PHONE_STATE)
            .request(object : OnPermission {

                override fun hasPermission(granted: List<String>, isAll: Boolean) {
                    if (isAll) {
                        object : Thread() {
                            override fun run() {
                                try {
                                    sleep(SHOW_TIME_MIN.toLong())// 1秒后跳转
                                    handler.sendEmptyMessage(100)
                                } catch (e: InterruptedException) {
                                    e.printStackTrace()
                                }

                            }
                        }.start()
                    }

                }

                override fun noPermission(denied: List<String>, quick: Boolean) {
                    val permissionDialog = PermissionDialog(mContext)
                    permissionDialog.displayPermissionDialog(object : PermissionDialog.OnClickView {
                        override fun onClickGet() {
                            initView()
                        }

                        override fun onClickCancel() {
                            activity!!.finish()
                        }
                    })
                }
            })
    }

}