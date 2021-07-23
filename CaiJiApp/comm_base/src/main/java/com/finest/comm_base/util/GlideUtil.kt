package com.finest.comm_base.util

import android.content.Context
import android.util.Base64
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.finest.comm_base.config.ConstantConfig
import com.finest.comm_net.okhttp.NetWorkCommonParams
import com.finest.comm_net.request.URLManager


/**
 * Created by liangjiangze on 2019/8/22.
 */
object GlideUtil {

    /**
     * 通过路径访问服务器图片，注意是路径，全路径Url访问请使用 displayFullUrlImage方法
     */
    fun displayImage(context: Context, path: String, view: ImageView) {
        if (NetWorkCommonParams.instance.commonParams.isEmpty()) {
            throw RuntimeException("没有登录")
        }
        Glide.with(context)
            .load("${URLManager.BASE_FILE_URL}?imgUri=${path}&finest-token=${NetWorkCommonParams.instance.commonParams[ConstantConfig.FINEST_TOKEN]}")
            .into(view)

    }


    /**
     * 通过路径访问服务器图片，注意是路径，全路径Url访问请使用 displayFullUrlImage方法
     */
    fun displayCircleImage(context: Context, path: String, view: ImageView) {
        if (NetWorkCommonParams.instance.commonParams.isEmpty()) {
            throw RuntimeException("没有登录")
        }
        Glide.with(context)
            .load("${URLManager.BASE_FILE_URL}${path}?finest-token=${NetWorkCommonParams.instance.commonParams[ConstantConfig.FINEST_TOKEN]}")
            .apply(RequestOptions.bitmapTransform(CircleCrop())).into(view)
    }

    /**
     * 通过路径访问服务器图片，注意是路径，全路径Url访问请使用 displayFullUrlImage方法
     */
    fun displayImageWithoutToken(context: Context, path: String, view: ImageView) {
        Glide.with(context).load(URLManager.BASE_FILE_URL + path).into(view)
    }


    /**
     * 通过路径访问服务器图片，注意是路径，全路径Url访问请使用 displayFullUrlImage方法
     */
    fun displayCircleImageWithoutToken(context: Context, path: String, view: ImageView) {

        Glide.with(context).load(URLManager.BASE_FILE_URL + path)
            .apply(RequestOptions.bitmapTransform(CircleCrop())).into(view)
    }


    fun displayFullUrlImage(context: Context, url: String, view: ImageView) {
        Glide.with(context).load(url).into(view)
    }

    //圆形图片
    fun displayFullUrlCircleImage(context: Context, url: String, view: ImageView) {
        Glide.with(context).load(url)
            .apply(RequestOptions.bitmapTransform(CircleCrop())).into(view)
    }



}
