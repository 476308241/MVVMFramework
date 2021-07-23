package com.finest.comm_base.widget.view

import android.content.Context
import android.graphics.Color
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import com.finest.comm_base.R

/**标题居中的toolbar
 *@Created by H.W.J 2020/6/29
 */
class CommonToolbar : Toolbar {

    private var mTitleTextView: TextView? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setBackgroundColor(resources.getColor(R.color.colorPrimary))
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun setTitle(title: CharSequence?) {
        if (mTitleTextView == null) {
            mTitleTextView = AppCompatTextView(context).apply {
                isSingleLine = true
                ellipsize = TextUtils.TruncateAt.END
                setTextColor(Color.WHITE)
                textSize = 16f
                gravity = Gravity.CENTER
                val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
                lp.gravity = Gravity.CENTER
                layoutParams = lp
            }
            mTitleTextView?.let {

                this@CommonToolbar.addView(it)
            }
        }
        mTitleTextView?.text = title
    }
}