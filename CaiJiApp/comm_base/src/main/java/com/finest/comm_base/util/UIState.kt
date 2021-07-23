package com.finest.comm_base.util

/**
 * Created by liangjiangze on 2021/1/12.
 */
sealed class UIState<out T> {

    class Loading<T> : UIState<T>()
    class DismissLoading<T> : UIState<T>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error<T>(val msg: String) : UIState<T>()

    companion object {

        /**
         * 加载中。
         */
        fun <T> loading() = Loading<T>()

        /**
         * 取消加载。
         */
        fun <T> dismissLoading() = DismissLoading<T>()

        /**
         *
         * @param data 返回数据
         */
        fun <T> success(data: T) = Success(data)

        /**
         *
         * @param msg  错误信息
         */
        fun <T> error(msg: String) = Error<T>(msg)


    }



}