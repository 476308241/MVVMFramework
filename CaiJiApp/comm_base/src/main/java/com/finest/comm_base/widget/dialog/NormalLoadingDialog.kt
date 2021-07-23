package com.finest.comm_base.widget.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Handler
import android.os.Message
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import com.finest.comm_base.R


/**
 * @author ：程序员小冰
 * @新浪微博 ：http://weibo.com/mcxiaobing
 * @GitHub: https://github.com/QQ986945193
 * @CSDN博客: http://blog.csdn.net/qq_21376985
 */

@SuppressLint("StaticFieldLeak")
object NormalLoadingDialog {

    private var mNormalLoadingDialog: Dialog? = null
    private var tipTextView: TextView? = null
    private val mHandler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                1 -> if (null != mNormalLoadingDialog) {
                    closeDialog(mNormalLoadingDialog)
                }
            }
        }
    }

    /**
     * 延迟消息等待窗口
     * @param delay  延迟时间，毫秒
     */
    fun closeDialogDelay(delay: Int) {
        mHandler.sendEmptyMessageDelayed(1, delay.toLong())
    }

    fun setTipText(text: String) {
        if (mNormalLoadingDialog != null && mNormalLoadingDialog!!.isShowing) {
            tipTextView!!.text = text
        }
    }

    fun createLoadingDialog(context: Context, msg: String): Dialog {
        val inflater = LayoutInflater.from(context)
        val v = inflater.inflate(R.layout.dialog_loading, null)// 得到加载view
        val layout = v
            .findViewById<View>(R.id.dialog_loading_view) as LinearLayout// 加载布局
        tipTextView = v.findViewById<View>(R.id.tipTextView) as TextView// 提示文字
        tipTextView!!.text = msg// 设置加载信息

        val loadingDialog = Dialog(context, R.style.MyDialogStyle)// 创建自定义样式dialog
        loadingDialog.setCancelable(false) // 是否可以按“返回键”消失
        loadingDialog.setCanceledOnTouchOutside(false) // 点击加载框以外的区域
        loadingDialog.setContentView(
            layout, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
            )
        )// 设置布局
        /**
         * 将显示Dialog的方法封装在这里面
         */
        val window = loadingDialog.window
        val lp = window!!.attributes
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.setGravity(Gravity.CENTER)
        window.attributes = lp
        window.setWindowAnimations(R.style.PopWindowAnimStyle)
        loadingDialog.show()
        mNormalLoadingDialog = loadingDialog

        return loadingDialog
    }

    /**
     * 关闭dialog
     *
     * http://blog.csdn.net/qq_21376985
     *
     * @param mDialogUtils
     */
    internal fun closeDialog(mDialogUtils: Dialog?) {
        if (mDialogUtils != null && mDialogUtils.isShowing) {
            mDialogUtils.dismiss()

            mNormalLoadingDialog = null
        }
    }

}