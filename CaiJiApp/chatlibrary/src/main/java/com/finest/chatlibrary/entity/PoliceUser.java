package com.finest.chatlibrary.entity;


import com.finest.chatlibrary.commons.model.IUser;

/**
 * 警员用户实体
 *
 * @author jinG
 * @date 2020/1/10
 */
public class PoliceUser implements IUser {
    /**
     * 民警编号
     */
    private String mjbh;
    /**
     * 民警姓名
     */
    private String mjxm;
    /**
     * 所属组织机构代码
     */
    private String sszzjgdm;
    /**
     * 头像
     */
    private String avatar;

    public PoliceUser(String id, String displayName, String orgCode, String avatar) {
        this.mjbh = id;
        this.mjxm = displayName;
        this.sszzjgdm = orgCode;
        this.avatar = avatar;
    }

    @Override
    public String getId() {
        return mjbh;
    }

    @Override
    public String getDisplayName() {
        return mjxm;
    }

    @Override
    public String getAvatarFilePath() {
        return avatar;
    }

    public String getSszzjgdm() {
        return sszzjgdm;
    }

    public void setSszzjgdm(String sszzjgdm) {
        this.sszzjgdm = sszzjgdm;
    }
}
