package com.finest.chatlibrary.util;

/**
 * @author hqf
 * @date 2018/9/29
 * @Dscrit 公用常量配置
 */
public final class AppConstants {
    public static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "soft_input_height";
    public static final String IS_SAVE_ACCOUNT = "is_save_account";
    public static final String LogTag = "HGFinest";
    public static final boolean isPrintException = true;

    /**
     * 设备编号
     */
    public static String IMEI = "";

    /**
     * 跳转到调度反馈界面的fragment类型,"msgList" 从消息中心进入完成反馈界面,"taskList" 从调度任务进入完成反馈界面
     */
    public static String FRAGMENT_TYPE = "";

    /**
     * 是否首次登陆缓存key
     */
    public static final String IS_FIRST_LOGIN = "is_first_login";

    public static final String APP_API_KEY = "2f9c0fa353fb416eb980b8dee3199c01";
    public static final String MMKV_NAME = "tpsCache";
    public static final String APP_MMKV_CRYPTKEY = "mmkv123";
    public static final String USERNAME_KEY = "userNameKey";
    public static final String USERPASSWORD_KEY = "userPassWordKey";
    public static final String USERCARNO_KEY = "userCarNOKey";

    public static final String DB_NAME = "hg_database.db";
    public static final String APP_DBROOM_CRYPTKEY = "TPS123456";

    public static final String KEY_DAEMON = "isDaemon";

    public static final String KEY_URL = "key_url";
    public static final String KEY_TITLE = "key_title";
    public static final String KEY_AUTHOR = "key_author";
    public static final String KEY_ID = "key_id";
    public static final String KEY_IS_COLLECT = "key_is_collect";

    public static final String KEY_REFRESH = "key_refresh";

    /**
     * 用于向心跳服务进行通知
     */
    public static final String NOTICE_HEART_BEAT_SERVICE = "notice_heart_beat_service";

    /**
     * 用于通知会话与消息中心更新数据
     */
    public static final String NOTICE_UPDATE_MSG = "notice_update_msg";

    /**
     * 用于通知调度任务更新数据
     */
    public static final String NOTICE_UPDATE_DISPATCH_TASK = "notice_update_dispatch_task";

    /**
     * 用于通知报警反馈更新数据
     */
    public static final String NOTICE_UPDATE_CALL_POLICE_FEEDBACK = "notice_update_callpolice_feedback";

    /**
     * 用于通知更新嫌疑人状态
     */
    public static final String NOTICE_UPDATE_SUSPECT_STATUS = "notice_update_suspect_status";

    /**
     * 用于通知关闭poi弹窗
     */
    public static final String NOTICE_CLOSE_POI = "notice_close_poi";

    /**
     * 显示警告弹窗
     */
    public static final int CODE_ALERT_DIALOG = 1;

    /**
     * 同步警情
     */
    public static final int SYNC_TASK = 2;

    /**
     * 同步会话
     */
    public static final int SYNC_MESSAGE = 3;

    /**
     * 同步嫌疑人状态
     */
    public static final int SYNC_SUSPECT = 4;

    public static final String CC_MODULE_APP_MAIN = "tps.AppMain";
    public static final String CC_UI_ROUTER_APP_MAIN_ACT_TEST_MODULE = "toModuleAct_TestModule";
    public static final String CC_MODULE_RF = "tps.RFModule";
    public static final String CC_UI_ROUTER_RF_ACT_SINGLE_CHOOSE = "toCommonAct_SingleChoose";
    public static final String CC_MODULE_ZH = "tps.ZHModule";
    public static final String CC_UI_ROUTER_ZH_ACT_LOCATION_PICK = "toModuleAct_LocationPick";
    public static final String CC_MODULE_AAR_YY = "tpsAAR.YYModule";
    public static final String CC_UI_ROUTER_AAR_YY_ACT_AUDIO_RECORD = "toModuleAct_AudioRecord";
    public static final String CC_MODULE_AAR_SP = "tpsAAR.SPModule";
    public static final String CC_UI_ROUTER_AAR_SP_ACT_VIDEO_RECORD = "toModuleAct_VideoRecord";
    /**
     * 登录过期
     */
    public static final String ERROR_1025 = "1025";
    /**
     * 报警提示
     */
    public static final int CALL_POLICE_TIP = 110;
    /**
     * 报警反馈
     */
    public static final int CALL_POLICE_FEED_BACK_TIP = 111;

    /**
     * 调度警情
     */
    public static final int DISPATCH_SITUATION_TIP = 112;

    /**
     * 调度会话
     */
    public static final int DISPATCH_CHAT_TIP = 113;

    /**
     * 调度会话
     */
    public static final int DISPATCH_SUSPECT_TIP = 114;

    /**
     * 行动类型
     */
    public static final String TASK_ARREST = "1";
    public static final String TASK_BANK_SURVEY = "4";
    public static final String TASK_SEARCH_ADDRESS = "3";
    public static final String TASK_SUPPORT = "2";
    /**
     * 调度任务状态 0：未签收，1：签收，2：签到，3：重定位，4：已反馈
     */
    public static final String NO_SIGN = "0";
    public static final String SIGN_FOR = "1";
    public static final String SIGN_IN = "2";
    public static final String RELOCATION = "3";
    public static final String FEEDBACKED = "4";
    /**
     * 定位模式（传统GPS坐标和百度坐标）和经纬度缓存
     */
    public static final String LOCATION_MODE = "locationMode";
    public static final String LONGITUDE_CACHE = "longitude_cache";
    public static final String LATITUDE_CACHE = "latitude_cache";
    public static final String ADDRESS_CACHE = "address_cache";
    public static final String CITY_CACHE = "city_cache";
    /**
     * 政务微信对接——开发者信息
     */
    public static final String SECRET = "NDY2ZDQwNTItZmE2YS00MGYwLTlmNWMtMmRiNzA4NDdhNmRm";
    public static final String LAUNCH_PARAMS = "launchParam";
    public static final String APP_ID = "2000507";
    public static final String SUCCESS = "SUCCESS";
    public static final String KEY_POLICENO = "警号";
    public static final String KEY_IDCARD = "身份证号";
    /**
     * 消息类型  0：指挥中心  1：调度组
     */
    public static final String COMMAND_CENTER = "0";
    public static final String DISPATCH_GROUP = "1";
    /**
     * 耳机拔出
     */
    public static final int HEADSET_DETECT_RECEIVER_FLAG = 100120;


    public static final int MESSAGE_PEER_SIZE = 10;

    /**
     * 信息流向 0：110指挥中心到警员，1：警员到110指挥中心
     */
    public static final String CENTER_TO_POLICEMEN = "0";
    public static final String POLICEMEN_TO_CENTER = "1";

    /**
     * 行动组已升级
     */
    public static final String ACTION_GROUP_UPGRADE = "行动组严重程度已升级！";
}
