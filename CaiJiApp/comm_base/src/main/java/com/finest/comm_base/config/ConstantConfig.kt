package com.finest.comm_base.config

object ConstantConfig {
    const val PAGE_SIZE = 10
    const val NETWORK_FAIL_ALERT = "网络请求失败，请稍后再试！"
    const val Exception_FLAT_MAP = "Exception_Flat_Map"
    const val LOADING = "加载中..."
    const val FILE_SELECTOR_CODE = 1000
    const val REQUEST_CODE_TYPE_VIDEO = 1001
    const val SELECT_CASE = 1002
    const val SELECT_DEAL_MAN = 1003
    const val RECORD_AUDIO_PATH = 1004
    const val FEEDBACK_RECORD_AUDIO_PATH = 1005
    const val FEEDBACK_REQUEST_CAMERA = 1006
    const val REQUEST_CAMERA = 1007
    const val RESULT_REQUEST_CAMERA = 1008
    const val SELECT_CHECK_TASK_RECEIVER = 1009
    const val ADD_PIC = "addPic"
    const val btn_yes = -100  //同意
    const val btn_return_pre = -101  //返回上一级
    const val btn_return_first = -102 // 返回原点
    const val FINEST_TOKEN = "finest-token"
    const val REQUEST_ADD_STAKEHOLDERS = 10086 //添加干系人
    const val EDIT_MODE="edit_mode" //干系人编辑模式
    const val ADD_MODE="add_mode" //干系人添加模式
    var faceImageListStr = "" //人脸搜索结果字符
    var faceImageDetailStr = ""//某个人脸详情
    var base64ImageStr = "" //base64图片字符
    var lat:Double?=null //纬度
    var lon:Double?=null //经度
}
