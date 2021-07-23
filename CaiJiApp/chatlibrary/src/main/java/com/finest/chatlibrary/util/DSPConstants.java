package com.finest.chatlibrary.util;

import android.os.Environment;

/**
 * @author Administrator
 * @date 2020/1/10
 */
public class DSPConstants {
    public static final String FROM_WHICH = "which";
    public static final String DISPATCHER_TASK_ID = "dispatch_task_id";
    public static final String DISPATCHER_YA_ID = "dispatch_ya_id";
    public static final String DISPATCHER_GROUP_ID = "dispatcher_group_id";
    public static final String DISPATCHER_SHEET = "dispatcher_sheet";
    public static final String USER_INFO = "user_info";
    public static final String MSG_CONTENT = "msg_content";
    public static final String IS_EDIT = "isEdit";

    public static final String AUDIO_FILE_PATH = Environment.getExternalStorageDirectory() + "/HGFinest/Cache/files/Records/";
    public static final String PICTURE_FILE_PATH = Environment.getExternalStorageDirectory() + "/HGFinest/Cache/files/Pictures/";
    public static final String VIDEO_FILE_PATH = Environment.getExternalStorageDirectory() + "/HGFinest/Cache/files/Videos/";
    public static final String FILE_PATH = Environment.getExternalStorageDirectory() + "/HGFinest/Cache/files/Files/";

    public final static String CHAT_SEND_HEAD_PORTRAIT = "R.mipmap.dispatch_chat_right_icon";
    public final static String CHAT_RECEIVE_HEAD_PORTRAIT = "R.mipmap.dispatch_chat_left_icon";
    public final static String CHAT_CENTER_HEAD_PORTRAIT = "R.mipmap.dispatch_chat_center_icon";

    public static final String CHAT_SEND_MESSAGE = "1002";
    public static final String CHAT_RESEND_MESSAGE = "1007";
    public static final String CHAT_MOVE_MESSAGE = "1009";
    public static final String HAVE_NEW_DISPATCHER_MESSAGE = "664";
    public static final String CHAT_SEND_NOTIFICATION = "1008";

    public static final String NEW_TASK_MSG = "你有新的警情调度，请注意查收！";
    public static final String NEW_CHAT_MSG = "你有新的警情会话，请注意查收！";

    public static final String MAIN_FRAGMENT_NAME = "DispatchTaskMainFragment";
    public static final String MSG_CENTRE_FRAGMENT_NAME = "DispatchMsgListFragment";
    public static final String CHAT_FRAGMENT_NAME = "DispatchChatFragment";
    public static final String CHAT_MENU_FRAGMENT_NAME = "MenuFragment";
    public static final String MAIN_ACTIVITY_NAME = "DispatchTaskMainActivity";
    public static final String DETAIL_FRAGMENT_NAME = "DispatchDetailFragment";

    /**
     * 1：图片，2：音频，3：视频
     */
    public static final Character picFileType = '1';
    public static final Character audioFileType = '2';
    public static final Character videoFileType = '3';
}
