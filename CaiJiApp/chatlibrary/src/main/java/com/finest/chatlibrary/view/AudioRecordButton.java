package com.finest.chatlibrary.view;


import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.CompoundButton;

import com.finest.chatlibrary.R;
import com.finest.chatlibrary.entity.DispatcherInfo;
import com.finest.chatlibrary.util.AudioManager;
import com.finest.chatlibrary.util.ChatDialogManager;
import com.finest.chatlibrary.util.MediaCacheUtils;


public class AudioRecordButton extends CompoundButton implements AudioManager.AudioStageListener {

    private static final String TAG = AudioRecordButton.class.getSimpleName();
    private static final int STATE_NORMAL = 1;
    private static final int STATE_RECORDING = 2;

    private static final int STATE_WANT_TO_CANCEL = 3;
    /**
     * 取消发送Y距离
     */
    private static final int DISTANCE_Y_CANCEL = 50;
    /**
     * 最大录音时长：秒
     */
    private static final int MAX_RECORD_TIME = 30;
    private int mCurrentState = STATE_NORMAL;
    /**
     * 已经开始录音
     */
    private boolean isRecording = false;
    private ChatDialogManager mChatDialogManager;
    private AudioManager mAudioManager;
    private MyHandler mHandler = new MyHandler();

    private float mTime = 0;
    /**
     * 是否触发了onlongclick，准备好了
     */
    private boolean mReady;
    /**
     * 准备三个常量
     */
    private static final int MSG_AUDIO_PREPARED = 0X110;
    private static final int MSG_VOICE_CHANGE = 0X111;
    private static final int MSG_DIALOG_DIMISS = 0X112;
    /**
     * 触摸事件，用于录音时间到，自动完成
     */
    private MotionEvent mEvent;

    /**
     * 先实现两个参数的构造方法，布局会默认引用这个构造方法， 用一个 构造参数的构造方法来引用这个方法
     *
     * @param context 上下文环境
     */
    public AudioRecordButton(Context context) {
        this(context, null);
    }

    public AudioRecordButton(final Context context, AttributeSet attrs) {
        super(context, attrs);

        mChatDialogManager = new ChatDialogManager(getContext());

        // 这里没有判断储存卡是否存在
        String dir = context.getExternalFilesDir(Environment.DIRECTORY_MUSIC).toString();
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = MediaCacheUtils.getMediaCacheSavePath(DispatcherInfo.Type.VOICE);
        } else {
            Log.v(TAG, "SD卡不存在");
        }
        mAudioManager = new AudioManager(dir);
        mAudioManager.setOnAudioStageListener(this);

        setOnLongClickListener(v -> {
            mReady = true;
            mAudioManager.prepareAudio();
            return false;
        });
    }

    /**
     * 录音完成后的回调，回调给activiy，可以获得mtime和文件的路径
     *
     * @author nickming
     */
    public interface AudioFinishRecorderListener {
        void onFinished(float seconds, String filePath);
    }

    private AudioFinishRecorderListener mListener;

    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener) {
        mListener = listener;
    }

    // 获取音量大小的runnable
    private Runnable mGetVoiceLevelRunnable = new Runnable() {

        @Override
        public void run() {
            while (isRecording) {
                try {
                    Thread.sleep(100);
                    mTime += 0.1f;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    // 在这里面发送一个handler的消息
    @Override
    public void wellPrepared() {
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
    }

    /**
     * 直接复写这个监听函数
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mEvent = event;
        int action = event.getAction();
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //MpsLog.log(TAG, "action_down");
                changeState(STATE_RECORDING);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isRecording) {
                    // MpsLog.log(TAG, x + "," + y);
                    // 根据x，y来判断用户是否想要取消
                    if (wantToCancel(x, y)) {
                        changeState(STATE_WANT_TO_CANCEL);
                    } else {
                        changeState(STATE_RECORDING);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                //MpsLog.log(TAG, "Action_up");
                // 首先判断是否有触发onlongclick事件，没有的话直接返回reset
                if (!mReady) {
                    reset();
                    return super.onTouchEvent(event);
                }
                // 如果按的时间太短，还没准备好或者时间录制太短，就离开了，则显示这个dialog
                if (!isRecording || mTime < 0.6f) {
                    mChatDialogManager.tooShort();
                    mAudioManager.cancel();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DIMISS, 1300);// 持续1.3s
                } else if (mCurrentState == STATE_RECORDING) {//正常录制结束
                    mChatDialogManager.dismissDialog();
                    mAudioManager.release();// release释放一个mediarecorder
                    if (mListener != null) {// 并且callbackActivity，保存录音
                        mListener.onFinished(mTime, mAudioManager.getCurrentFilePath());
                    }
                } else if (mCurrentState == STATE_WANT_TO_CANCEL) {
                    // cancel
                    mAudioManager.cancel();
                    mChatDialogManager.dismissDialog();
                }
                reset();// 恢复标志位

                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 回复标志位以及状态
     */
    private void reset() {
        isRecording = false;
        changeState(STATE_NORMAL);
        mReady = false;
        mTime = 0;
    }

    private boolean wantToCancel(int x, int y) {
        if (x < 0 || x > getWidth()) {// 判断是否在左边，右边，上边，下边
            return true;
        }
        return y < -DISTANCE_Y_CANCEL || y > getHeight() + DISTANCE_Y_CANCEL;

    }

    private void changeState(int state) {
        if (mCurrentState != state) {
            mCurrentState = state;
            switch (mCurrentState) {
                case STATE_NORMAL:
                    setBackgroundResource(R.drawable.dispatch_btn_voice_unrecorded);
                    setText(R.string.btn_voice_unrecorded);
                    break;
                case STATE_RECORDING:
                    setBackgroundResource(R.drawable.dispatch_btn_voice_recording);
                    setText(R.string.btn_voice_recording);
                    if (isRecording) {
                        mChatDialogManager.recording();
                        // 复写dialog.recording();
                    }
                    break;
                case STATE_WANT_TO_CANCEL:
                    setBackgroundResource(R.drawable.dispatch_btn_voice_recording);
                    setText(R.string.scroll_up_cancel_send);
                    // dialog want to cancel
                    mChatDialogManager.wantToCancel();
                    break;
                default:
                    break;
            }
        }
    }

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_AUDIO_PREPARED:
                    // 显示应该是在audio end prepare之后回调
                    mChatDialogManager.showRecordingDialog();
                    isRecording = true;
                    try {
                        new Thread(mGetVoiceLevelRunnable).start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // 需要开启一个线程来变换音量
                    break;
                case MSG_VOICE_CHANGE:
                    mChatDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
                    if (mTime > MAX_RECORD_TIME) {
                        mEvent.setAction(MotionEvent.ACTION_UP);
                        onTouchEvent(mEvent);
                    }
                    break;
                case MSG_DIALOG_DIMISS:
                    mChatDialogManager.dismissDialog();
                    break;
                default:
                    break;
            }
        }
    }


}
