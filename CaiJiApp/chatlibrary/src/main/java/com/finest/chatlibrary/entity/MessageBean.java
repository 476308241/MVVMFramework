package com.finest.chatlibrary.entity;



import com.finest.chatlibrary.commons.model.IMessage;
import com.finest.chatlibrary.commons.model.IUser;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/**
 * 封装的通用消息实体
 *
 * @author jinG
 * @date 2020/1/10
 */
public class MessageBean implements IMessage, Serializable {
    private long id;
    private String text;
    private String timeString;
    private int type;
    private IUser user;
    private String mediaFilePath;
    private long duration;
    private String progress;
    private MessageStatus mMsgStatus = MessageStatus.CREATED;
    private DispatcherInfo dispatcherInfo;
    private boolean isLocal;
    private boolean isSelected;

    public MessageBean(String text, int type) {
        this.text = text;
        this.type = type;
        this.id = UUID.randomUUID().getLeastSignificantBits();
    }

    @Override
    public String getMsgId() {
        return String.valueOf(id);
    }

    public long getId() {
        return this.id;
    }

    @Override
    public IUser getFromUser() {
        if (user == null) {
            return new PoliceUser("test", "test", "test", null);
        }
        return user;
    }

    public void setUserInfo(IUser user) {
        this.user = user;
    }

    public void setMediaFilePath(String path) {
        this.mediaFilePath = path;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public long getDuration() {
        return duration;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    @Override
    public String getProgress() {
        return progress;
    }

    @Override
    public HashMap<String, String> getExtras() {
        return null;
    }

    public void setTimeString(String timeString) {
        this.timeString = timeString;
    }

    @Override
    public String getTimeString() {
        return timeString;
    }

    @Override
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    @Override
    public boolean isSelected() {
        return isSelected;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public int getType() {
        return type;
    }

    /**
     * Set Message status. After sending Message, change the status so that the progress bar will dismiss.
     *
     * @param messageStatus
     */
    public void setMessageStatus(MessageStatus messageStatus) {
        this.mMsgStatus = messageStatus;
    }

    @Override
    public MessageStatus getMessageStatus() {
        return this.mMsgStatus;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public String getMediaFilePath() {
        return mediaFilePath;
    }

    public DispatcherInfo getDispatcherInfo() {
        return dispatcherInfo;
    }

    public void setDispatcherInfo(DispatcherInfo dispatcherInfo) {
        this.dispatcherInfo = dispatcherInfo;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    @Override
    public int getItemType() {
        return type;
    }

}