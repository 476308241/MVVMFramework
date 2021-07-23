package com.finest.chatlibrary.entity;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.finest.chatlibrary.util.TimeTransformation;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
/**
 * name:DispatchMsgSheet
 * func:警情调度消息会话数据库
 * author:
 * date:2016/11/24 16:54
 * copyright:jy
 */
@Entity(tableName = "DispatcherInfo")
public class DispatcherInfo implements Serializable {

    @PrimaryKey
    private Long id;
    private String sid;
    /**
     * 预案编号
     */
    private String caseNo;
    /**
     * 行动编号
     */
    private String xdbh;
    /**
     * 行动类型, 1:逮捕嫌疑人  2：现场支援  3：地址搜查  4：银行搜查
     */
    private String xdlx;
    /**
     * 调度组SID
     */
    private String groupSid;
    /**
     * 调度组名称
     */
    private String groupName;
    /**
     * 信息类型，0：文本信息，1：图片，2：语音，3：视频
     */
    private String infoType;
    /**
     * 信息流向，0：110指挥中心到警员，1：警员到110指挥中心
     */
    private String infoFlow;
    /**
     * 文件下载ID
     */
    private Long fileDownloadId;
    /**
     * 信息内容，当信息类型为“0”时，存储文本信息，其他类型存储文件路径
     */
    private String content;
    /**
     * 本地消息文件目录
     */
    private String filePath;
    /**
     * 警员编号，信息发起人编号
     */
    private String senderId;
    /**
     * 发送者类型 1:对调度组信息，2：对警员个人信息交流。
     */
    private String senderType;
    /**
     * 警员名称，信息发起人名称
     */
    private String senderName;
    /**
     * 消息状态
     */
    private int status;
    /**
     * 本地发送时间
     */
    @TypeConverters({TimeTransformation.class})
    private Date sendTime;
    /**
     * 服务器保存时间
     */
    @TypeConverters({TimeTransformation.class})
    private Date createTime;
    /**
     * 保存额外信息，语音时长，视频缩略图
     */
    private String extra;
    /**
     * 标记删除
     */
    private Boolean delete;
    /**
     * 警员编号
     */
    private String policeNo;
    /**
     * 组织机构代码
     */
    private String orgCode;
    /**
     * 消息id
     */
    private String msgId;
    /**
     * 同步时间
     */
    @TypeConverters({TimeTransformation.class})
    private Date synTime;
    /**
     * 警情未读信息数量
     */
    private String count;

    /**
     * 情报是否已收藏
     */
    private String isCollect;
    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 消息描述
     */
    private String infoDesc;

    /**
     * 源文件名称
     */
    private String infoName;

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getSenderType() {
        return senderType;
    }

    public void setSenderType(String senderType) {
        this.senderType = senderType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getIsCollect() {
        return isCollect;
    }

    public void setIsCollect(String isCollect) {
        this.isCollect = isCollect;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getInfoDesc() {
        return infoDesc;
    }

    public void setInfoDesc(String infoDesc) {
        this.infoDesc = infoDesc;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getGroupSid() {
        return groupSid;
    }

    public void setGroupSid(String groupSid) {
        this.groupSid = groupSid;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    public String getInfoFlow() {
        return infoFlow;
    }

    public void setInfoFlow(String infoFlow) {
        this.infoFlow = infoFlow;
    }

    public Long getFileDownloadId() {
        return fileDownloadId;
    }

    public void setFileDownloadId(Long fileDownloadId) {
        this.fileDownloadId = fileDownloadId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Boolean getDelete() {
        return delete;
    }

    public void setDelete(Boolean delete) {
        this.delete = delete;
    }

    public String getPoliceNo() {
        return policeNo;
    }

    public void setPoliceNo(String policeNo) {
        this.policeNo = policeNo;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Date getSynTime() {
        return synTime;
    }

    public void setSynTime(Date synTime) {
        this.synTime = synTime;
    }

    public String getXdbh() {
        return xdbh;
    }

    public void setXdbh(String xdbh) {
        this.xdbh = xdbh;
    }

    public String getXdlx() {
        return xdlx;
    }

    public void setXdlx(String xdlx) {
        this.xdlx = xdlx;
    }

    @Ignore
    public DispatcherInfo(@NotNull String sid, String caseNo, String xdbh, String xdlx, String groupSid, String groupName, String infoType, String infoFlow, Long fileDownloadId, String content, String filePath, String senderId, String senderType, String senderName, int status, Date sendTime, Date createTime, String extra, Boolean delete, String policeNo, String orgCode, String msgId, Date synTime, String count, String isCollect, Double longitude, Double latitude, String infoDesc, String infoName) {
        this.sid = sid;
        this.caseNo = caseNo;
        this.xdbh = xdbh;
        this.xdlx = xdlx;
        this.groupSid = groupSid;
        this.groupName = groupName;
        this.infoType = infoType;
        this.infoFlow = infoFlow;
        this.fileDownloadId = fileDownloadId;
        this.content = content;
        this.filePath = filePath;
        this.senderId = senderId;
        this.senderType = senderType;
        this.senderName = senderName;
        this.status = status;
        this.sendTime = sendTime;
        this.createTime = createTime;
        this.extra = extra;
        this.delete = delete;
        this.policeNo = policeNo;
        this.orgCode = orgCode;
        this.msgId = msgId;
        this.synTime = synTime;
        this.count = count;
        this.isCollect = isCollect;
        this.longitude = longitude;
        this.latitude = latitude;
        this.infoDesc = infoDesc;
        this.infoName = infoName;
        this.id = UUID.randomUUID().getLeastSignificantBits();
    }

    public DispatcherInfo() {
        this.id = UUID.randomUUID().getLeastSignificantBits();
    }

    /**
     * 消息类型
     */
    public final class Type {
        /**
         * 文本消息类型
         */
        public static final String TEXT = "0";
        /**
         * 图片消息类型
         */
        public static final String IMAGE = "1";
        /**
         * 音频消息类型
         */
        public static final String VOICE = "2";
        /**
         * 视频消息类型
         */
        public static final String VIDEO = "3";
        /**
         * 文件消息类型
         */
        public static final String NOTIFICATION = "4";
        /**
         * 通知消息类型
         */
        public static final String FILE = "9";

    }

    /**
     * 消息处理状态
     */
    public final class Status {
        /**
         * 处理失败
         */
        public static final int FAILED = -1;
        /**
         * 处理中
         */
        public static final int PROGRESS = 0;
        /**
         * 处理成功
         */
        public static final int SUCCESS = 1;
        /**
         * 已读
         */
        public static final int READ = 2;
        /**
         * 已删除
         */
        public static final int DELETED = 3;
    }

    @Override
    public String toString() {
        return "DispatcherInfo{" +
                ", sid='" + sid + '\'' +
                ", groupSid='" + groupSid + '\'' +
                ", groupName='" + groupName + '\'' +
                ", infoType='" + infoType + '\'' +
                ", infoFlow='" + infoFlow + '\'' +
                ", fileDownloadId=" + fileDownloadId +
                ", content='" + content + '\'' +
                ", filePath='" + filePath + '\'' +
                ", senderId='" + senderId + '\'' +
                ", senderName='" + senderName + '\'' +
                ", senderType=" + senderType + '\'' +
                ", status=" + status + '\'' +
                ", sendTime=" + sendTime + '\'' +
                ", createTime=" + createTime + '\'' +
                ", extra='" + extra + '\'' +
                ", delete=" + delete + '\'' +
                ", policeNo='" + policeNo + '\'' +
                ", orgCode='" + orgCode + '\'' +
                ", msgId='" + msgId + '\'' +
                ", synTime=" + synTime + '\'' +
                ", count=" + count + '\'' +
                ", isCollect=" + isCollect + '\'' +
                ", longitude=" + longitude + '\'' +
                ", latitude=" + latitude + '\'' +
                ", infoDesc=" + infoDesc + '\'' +
                '}';
    }
}