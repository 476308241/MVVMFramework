package com.finest.comm_base.mvvm


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.blankj.utilcode.util.ToastUtils
import com.finest.comm_base.R
import com.finest.comm_base.app.MyObserver
import com.finest.comm_base.mvvm.interfaces.IUI
import com.finest.comm_base.util.RoutePath
import com.finest.comm_base.widget.dialog.NormalLoadingDialog
import timber.log.Timber

/**
 * Created by liangjiangze on 2020/12/24.
 */
abstract class BaseMVVMFragment: Fragment(), IUI {
    var inflaterView: View? = null
    val TAG = javaClass.simpleName
    lateinit var mViewDataBinding:ViewDataBinding
    var isFirstCreate = true
    val mContext: AppCompatActivity
        get() = activity as AppCompatActivity
    val routeBundle:Bundle?
        get() =  requireActivity().intent.getBundleExtra(RoutePath.Route_Bundle)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isFirstCreate){
            isFirstCreate = false
            initView()
            initViewObservable()
            initOnClick()
        }
        setHasOptionsMenu(true)
        var myObserver = MyObserver()
        myObserver.tag = TAG
        lifecycle.addObserver(myObserver)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (isFirstCreate){
            try {
                mViewDataBinding =  DataBindingUtil.inflate(inflater, getLayoutResId(),container,false)
                inflaterView = mViewDataBinding.root
            }catch (e:Exception){
                Timber.d(TAG,"页面的xml需要以layout为根布局,否则绑定出现异常,不属于MVVM模式")
                inflaterView = inflater.inflate(
                    getLayoutResId(), container,
                    false
                )
            }
        }
        return inflaterView

    }

    override fun onResume() {
        super.onResume()
        refreshTitle(getActivityTitle())
    }

    private fun refreshTitle(title:String){
        val requireActivity = requireActivity()
        if (requireActivity is BaseMVVMActivity) {
            if (title.isNullOrEmpty()){
                requireActivity.mToolbar?.visibility = View.GONE
            }else{
                requireActivity.mToolbar?.visibility = View.VISIBLE
                requireActivity.mToolbar?.title = title
            }
        }
    }

    private fun displayLoadingProgressBarBase() {
        val loadingLayout = inflaterView!!.findViewById<LinearLayout>(R.id.layout_loading)
        loadingLayout.visibility = View.VISIBLE
        val mProgressBar = inflaterView!!.findViewById<ProgressBar>(R.id.progressBar)
        val refreshText = inflaterView!!.findViewById<TextView>(R.id.tv_refresh)
        val ivNetError = inflaterView!!.findViewById<ImageView>(R.id.iv_net_error)
        mProgressBar.visibility = View.VISIBLE
        refreshText.visibility = View.GONE
        ivNetError.visibility = View.GONE
        //加载中的效果时，点击无效，执行onRefreshView();
        loadingLayout.setOnClickListener { }
    }

    private fun hideLoadingProgressBarBase() {
        val loadingLayout = inflaterView!!.findViewById<LinearLayout>(R.id.layout_loading)
        loadingLayout?.visibility = View.GONE
    }

    private fun showRefreshTextBase(errorMsg: String) {
        val loadingLayout = inflaterView!!.findViewById<LinearLayout>(R.id.layout_loading)
        loadingLayout.visibility = View.VISIBLE
        val mProgressBar = inflaterView!!.findViewById<ProgressBar>(R.id.progressBar)
        val refreshText = inflaterView!!.findViewById<TextView>(R.id.tv_refresh)
        val ivNetError = inflaterView!!.findViewById<ImageView>(R.id.iv_net_error)
        refreshText.text = errorMsg
        mProgressBar.visibility = View.GONE
        refreshText.visibility = View.VISIBLE
        ivNetError.visibility = View.VISIBLE
        loadingLayout.setOnClickListener {
            displayLoadingProgressBar()
            onRefreshView()
        }
    }

    fun showToast(str: String) {
        ToastUtils.showShort(str)
    }

    fun showRefreshText() {
        showRefreshTextBase("网络连接失败，点击空白刷新")
    }
    fun showRefreshText(errorMsg: String) {
        showRefreshTextBase(errorMsg)
    }

    fun displayLoadingProgressBar() {
        displayLoadingProgressBarBase()
    }

    fun hideLoadingProgressBar() {
        hideLoadingProgressBarBase()
    }

    fun displayLoadingDialog(txt: String) {
        NormalLoadingDialog.createLoadingDialog(mContext, txt)
    }

    fun hideLoadingDialog(delay: Int) {
        NormalLoadingDialog.closeDialogDelay(delay)
    }


    fun navigateTo(id:Int){
        findNavController().navigate(id)
    }
    fun navigateTo(id:Int,bundle: Bundle){
        findNavController().navigate(id,bundle)
    }

    /**
     * 刷新界面
     */
    open fun onRefreshView() {

    }

    /**
     * 初始化监听
     */
    open fun initViewObservable() {

    }

    /**
     * 初始化点击事件
     */
    open fun initOnClick() {

    }




}