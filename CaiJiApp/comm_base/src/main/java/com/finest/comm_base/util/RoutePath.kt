package com.finest.comm_base.util

/**
 * Created by liangjiangze on 2021/7/20.
 * 不同模块跳转需要配置Arouter，相同模块跳转直接使用Navigation
 */
object RoutePath {

    //跳转fragment的目标
    const val Route_Destination = "route_destination"
    //跳转传参
    const val Route_Bundle = "route_bundle"
    /**
     * jetpack模块
     */
    const val JETPACK = "/jetpack"
    const val JETPACK_LAUNCH_ACTIVITY = "$JETPACK/LaunchJetpackModuleActivity"
    const val JETPACK_JETPACK = "$JETPACK/JetpackFragment"
    const val JETPACK_DATABINDING =  "$JETPACK/DataBindingTestFragment"
    const val JETPACK_VIEWMODEL =  "$JETPACK/ViewModelFragment"

    /**
     * chat模块
     */
    const val CHAT = "/chat"
    const val CHAT_LAUNCH_ACTIVITY = "$CHAT/LaunchChatModuleActivity"
    const val CHAT_CHAT = "$CHAT/ChatFragment"

    /**
     * search模块
     */
    const val SEARCH = "/search"
    const val SEARCH_LAUNCH_ACTIVITY = "$SEARCH/LaunchSearchModuleActivity"
    const val SEARCH_SEARCH = "$SEARCH/SearchFragment"

    /**
     * search模块
     */
    const val TASK = "/task"
    const val TASK_LAUNCH_ACTIVITY = "$TASK/LaunchTASKModuleActivity"
    const val TASK_TASK = "$TASK/TaskFragment"

}