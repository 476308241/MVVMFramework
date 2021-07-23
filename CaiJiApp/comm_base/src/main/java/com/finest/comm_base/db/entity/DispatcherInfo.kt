package com.finest.comm_base.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by liangjiangze on 2021/1/20.
 */
@Entity(tableName = "dispatcherInfo")
class DispatcherInfo {
    @PrimaryKey(autoGenerate = true)
    var id: Long?=null
    var sid: String? = null

    /**
     * 预案编号
     */
    var caseNo: String? = null

    /**
     * 行动编号
     */
    var xdbh: String? = null

    /**
     * 行动类型, 1:逮捕嫌疑人  2：现场支援  3：地址搜查  4：银行搜查
     */
    var xdlx: String? = null

    /**
     * 调度组SID
     */
    var groupSid: String? = null

    /**
     * 调度组名称
     */
    var groupName: String? = null

    /**
     * 信息类型，0：文本信息，1：图片，2：语音，3：视频
     */
    var infoType: String? = null

    /**
     * 信息流向，0：110指挥中心到警员，1：警员到110指挥中心
     */
    var infoFlow: String? = null

    /**
     * 文件下载ID
     */
    var fileDownloadId: Long? = null

    /**
     * 信息内容，当信息类型为“0”时，存储文本信息，其他类型存储文件路径
     */
    var content: String? = null

    /**
     * 本地消息文件目录
     */
    var filePath: String? = null

    /**
     * 警员编号，信息发起人编号
     */
    var senderId: String? = null

    /**
     * 发送者类型 1:对调度组信息，2：对警员个人信息交流。
     */
    var senderType: String? = null

    /**
     * 警员名称，信息发起人名称
     */
    var senderName: String? = null

    /**
     * 消息状态
     */
    var status = 0

//    /**
//     * 本地发送时间
//     */
//    @TypeConverters(TimeTransformation::class)
//    val sendTime: Date? = null

    /**
     * 保存额外信息，语音时长，视频缩略图
     */
    var extra: String? = null

    /**
     * 标记删除
     */
    var delete: Boolean? = null

    /**
     * 警员编号
     */
    var policeNo: String? = null

    /**
     * 组织机构代码
     */
    var orgCode: String? = null

    /**
     * 消息id
     */
    var msgId: String? = null


    /**
     * 警情未读信息数量
     */
    var count: String? = null

//    /**
//     * 情报是否已收藏
//     */
//    var isCollect: String? = null

    /**
     * 经度
     */
    var longitude: Double? = null

    /**
     * 纬度
     */
    var latitude: Double? = null

    /**
     * 消息描述
     */
    var infoDesc: String? = null

    /**
     * 源文件名称
     */
    var infoName: String? = null
}