package com.finest.chatlibrary.commons.model;


public interface IUser {

    /**
     * User id.
     * @return user id, unique
     */
    String getId();

    /**
     * Display name of user
     * @return display name
     */
    String getDisplayName();

    /**
     * Get user avatar file path.
     * @return avatar file path
     */
    String getAvatarFilePath();

}
