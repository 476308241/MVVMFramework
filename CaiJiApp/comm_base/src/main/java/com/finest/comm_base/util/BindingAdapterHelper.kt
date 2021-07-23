package com.finest.comm_base.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter

/**
 * Created by liangjiangze on 2021/1/5.
 */

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {
        GlideUtil.displayFullUrlImage(view.context,imageUrl,view)
    }
}

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}