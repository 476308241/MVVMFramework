package com.finest.comm_base.widget.dialog

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import com.finest.comm_base.R


class PermissionDialog(private val context: Context) {
    interface OnClickView {
        fun onClickGet()
        fun onClickCancel()
    }

    fun displayPermissionDialog(mCallBack: OnClickView) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_permission, null)
        val dialog = Dialog(context, R.style.NormalDialog)
        dialog.setContentView(view)
        //设置点击屏幕不消失
        dialog.setCanceledOnTouchOutside(false)
        //设置点击返回键不消失
        dialog.setCancelable(false)
        //让对话框居于屏幕底部,占满屏
        val window = dialog.window
        val wm = context
            .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val outMetrics = DisplayMetrics()
        wm.defaultDisplay.getMetrics(outMetrics)
        val width = outMetrics.widthPixels * 4 / 5
        if (window != null) {
            window.decorView.setPadding(0, 0, 0, 0)
            val attr = window.attributes
            if (attr != null) {
                attr.height = ViewGroup.LayoutParams.WRAP_CONTENT
                attr.width = width
                //设置dialog 在布局中的位置
                attr.gravity = Gravity.CENTER
                window.attributes = attr
            }
        }
        dialog.show()
        val tvYes = view.findViewById<TextView>(R.id.tv_get)
        val tvNo = view.findViewById<TextView>(R.id.tv_cancle)
        tvYes.setOnClickListener {
            mCallBack.onClickGet()
            dialog.dismiss()
        }
        tvNo.setOnClickListener {
            mCallBack.onClickCancel()
            dialog.dismiss()
        }

    }
}

