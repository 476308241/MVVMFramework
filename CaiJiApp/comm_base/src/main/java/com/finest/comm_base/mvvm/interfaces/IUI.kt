package com.finest.comm_base.mvvm.interfaces

/**
 * Created by liangjiangze on 2021/7/8.
 */
interface IUI {
    fun initView()
    fun getLayoutResId():Int
    fun getActivityTitle(): String
}