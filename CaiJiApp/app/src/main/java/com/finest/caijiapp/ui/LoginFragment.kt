package com.finest.caijiapp.ui
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.launcher.ARouter
import com.finest.caijiapp.R
import com.finest.chat.config.LiveEventKey
import com.finest.comm_base.bean.CrashMsgBean
import com.finest.comm_base.config.SPConfig
import com.finest.caijiapp.databinding.FragmentLoginBinding
import com.finest.comm_base.mvvm.BaseMVVMFragment
import com.finest.caijiapp.viewmodel.LoginViewModel
import com.finest.comm_base.util.SharedPref
import com.finest.comm_base.util.UIState
import com.jeremyliao.liveeventbus.LiveEventBus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BaseMVVMFragment() {
    private val viewModel: LoginViewModel by viewModels()
    private val dataBinding:FragmentLoginBinding
        get() = mViewDataBinding as FragmentLoginBinding
    @Inject
    lateinit var sharedPref: SharedPref
    override fun getLayoutResId(): Int {
        return R.layout.fragment_login
    }
    override fun getActivityTitle(): String {
        return "登录"
    }

    override fun initView() {
        dataBinding.viewModel = viewModel
        val isRemember =sharedPref.getBoolean(SPConfig.IS_REMEMBER_PSW, false)
        if (isRemember) {
            et_userName.setText(
                sharedPref.getString(
                    SPConfig.USER_NAME,
                    ""
                )
            )
            cb_select.isChecked = true
        } else {
            cb_select.isChecked = false
        }
        et_password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                login()
                true
            } else {
                false
            }
        }
        iv_set.setOnLongClickListener{
            var crashList = sharedPref.getDataList(
                SPConfig.CRASH_MSG_LIST,
                CrashMsgBean::class.java) as ArrayList
            if (crashList.size==0){
                showToast("没有崩溃信息")
            }else{
                navigateTo(R.id.CrashListFragment)
            }
            true
        }
    }

    override fun initViewObservable() {
        //        viewModel.isSuccess.observe(this, Observer {
//            if (viewModel.isSuccess.value == true ){
//                navigateTo(R.id.MainTabFragment)
//            }
//            hideLoadingDialog(20)
//        })
        viewModel.loginData.observe(this, Observer {
            when(it){
                is UIState.Loading<*> -> {
                    displayLoadingDialog("登录中...")
                }
                is UIState.Success<*> ->{
                    LiveEventBus.get(LiveEventKey.login_success).post("")
                    try {
                        navigateTo(R.id.MainTabFragment)

                    }catch (e:Exception){
                        Log.e("TEST",e.message.toString())
                    }
//                    ARouter.getInstance().build("/search/LaunchSearchActivity").navigation()

                    hideLoadingDialog(100)
                }
                is UIState.Error<*> ->{
                    showToast(it.msg)
                    navigateTo(R.id.MainTabFragment)
//                    ARouter.getInstance().build("/search/LaunchSearchActivity").navigation()
//                    val jetpackFragment = ARouter.getInstance().build("/task/TaskFragment").navigation() as Fragment
//                     if (jetpackFragment==null){
//                         showToast("null")
//                     }else{
//                         showToast("notnult")
//                     }
                    hideLoadingDialog(100)
                }
                else ->{

                }
            }
        })
    }

    override fun initOnClick() {
        btn_login.setOnClickListener { login() }
        rl_remember.setOnClickListener {if (cb_select.isChecked) {
            cb_select.isChecked = false
            sharedPref
                .setBoolean(SPConfig.IS_REMEMBER_PSW, false)
        } else {
            cb_select.isChecked = true
            sharedPref
                .setBoolean(SPConfig.IS_REMEMBER_PSW, true)
        }  }
        iv_clear_username.setOnClickListener {
            et_userName.text.clear()
        }
        iv_clear_password.setOnClickListener {
            et_password.text.clear()
        }
    }


    private fun login(){
//        if (TextUtils.isEmpty(viewModel.userName.get())) {
//            ToastUtils.showShort("请输入账号！")
//            return
//        } else if (TextUtils.isEmpty(viewModel.password.get())) {
//            ToastUtils.showShort("请输入密码！")
//            return
//        }
//        displayLoadingDialog("登录中...")
        viewModel.login()
    }

}