package com.finest.comm_net.request

/**
 * 是否是生产环境
 */
const val PRODUCT_VERSION = true

/**
 * 服务地址管理
 */
const val PRODUCT_URL = "http://192.168.20.90:8888/res19123102/services/"
const val COMPANY_URL = "http://192.168.30.174:8084/services/"   //备用地址

object URLManager {


    /**
     * 布控接口
     */
    val BASE_URL_BK: String
    /**
     * 搜索
     */
    val BASE_URL_SEARCH: String
    /**
     * 搜索详单
     */
    val BASE_URL_SEARCH_2: String
    /**
     * 预警
     */
    val BASE_URL_WARNING: String

    val BASE_URL_WEB: String

    val BASE_URL: String

    val BASE_FILE_URL: String
    /**
     * 文件上传
     */
    val UPLOAD_FILE: String
    /**
     * 人脸识别文件上传
     */
    val UPLOAD_FILE_FACE_SEARCH: String
    /**
     * 预警附件上传
     */
    val UPLOAD_FILE_WARN_FJ: String

    init {
        if (PRODUCT_VERSION) {//生产环境
            BASE_URL_BK = PRODUCT_URL
            BASE_URL_SEARCH = PRODUCT_URL
            BASE_URL_SEARCH_2 =  PRODUCT_URL
            BASE_URL_WARNING = PRODUCT_URL
            BASE_URL_WEB = PRODUCT_URL
            BASE_URL = PRODUCT_URL
            /**
             * 文件服务器地址需要单独写出来，因为Retrofit的要求，所有接口的baseUrl都必须以路径分隔符结尾，所以普通接口前缀都以/结尾。
             * 而后台返回的文件路径是以/开头，所以文件访问前缀不能以/结尾，单独写一个.
             */
            BASE_FILE_URL = "http://192.168.20.90:8888/res19123102/services/synframe/portal/synframe/fileService/downLoadImgFile"
//            UPLOAD_FILE = "http://192.168.20.90:8888/res19123102/services/flow-review/upfiles"
            UPLOAD_FILE = "http://192.168.20.90:8888/res19123102/services/synframe/portal/synframe/fileService/uploadFile"
            //注意门户接口地址多一个synframe
            UPLOAD_FILE_FACE_SEARCH = "http://192.168.20.90:8888/res19123102/services/synframe/portal/synframe/fileService/uploadFile"
            UPLOAD_FILE_WARN_FJ = "http://192.168.20.90:8888/res19123102/services/synframe/portal/synframe/fileService/uploadFile"
        } else {//测试环境

            /**
             * 现场一致的地址
             */
            BASE_URL_BK = "http://192.168.30.174:8099/"
            BASE_URL_SEARCH = "http://192.168.30.174:8081/"
//            BASE_URL_SEARCH_2 = "http://192.168.31.64:8080/"
            BASE_URL_SEARCH_2 = "http://192.169.30.174:8080/zhqz/"
            BASE_URL_WARNING = "http://192.168.30.174:8088/"
            BASE_URL_WEB = "http://192.168.30.174:8000/"
            BASE_URL = "http://192.168.30.174:16666/"
//            BASE_URL =  "http://192.168.31.56:16666/"
//            BASE_FILE_URL = "http://192.168.30.174:16666"
            BASE_FILE_URL = "http://192.168.30.174:8000/portal/synframe/fileService/downLoadImgFile"
//            UPLOAD_FILE = "http://192.168.30.174:16666/flow-review/upfiles"
            UPLOAD_FILE = "http://192.168.30.174:8000/portal/synframe/fileService/uploadFile"
            UPLOAD_FILE_FACE_SEARCH = "http://192.168.30.174:8000/portal/synframe/fileService/uploadFile"
            UPLOAD_FILE_WARN_FJ = "http://192.168.30.174:8000/portal/synframe/fileService/uploadFile"
            /**
             * 备用地址
//             */
//            BASE_URL_BK = COMPANY_URL
//            BASE_URL_SEARCH = COMPANY_URL
//            BASE_URL_WARNING = COMPANY_URL
//            BASE_URL_WEB = COMPANY_URL
//            BASE_URL = COMPANY_URL
//            BASE_FILE_URL = "http://192.168.30.174:8084/services"
//            UPLOAD_FILE = "http://192.168.30.174:8084/services/flow-review/upfiles"
        }
    }

}
