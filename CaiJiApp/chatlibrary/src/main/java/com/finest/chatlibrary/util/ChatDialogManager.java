package com.finest.chatlibrary.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.finest.chatlibrary.R;


public class ChatDialogManager {
    /**
     * 以下为dialog的初始化控件，包括其中的布局文件
     */
    private Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLabel;

    private Context mContext;

    public ChatDialogManager(Context context) {
        mContext = context;
    }

    public void showRecordingDialog() {
        mDialog = new Dialog(mContext, R.style.Theme_audioDialog);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dispatch_dialog_manager, null);
        mDialog.setContentView(view);

        mIcon = (ImageView) mDialog.findViewById(R.id.dialog_icon);
        mVoice = (ImageView) mDialog.findViewById(R.id.dialog_voice);
        mLabel = (TextView) mDialog.findViewById(R.id.recorder_dialog_text);
        mDialog.show();
    }

    /**
     * 设置正在录音时的dialog界面
     */
    public void recording() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.mipmap.aurora_recordvoice);
            mLabel.setText(R.string.scroll_up_cancel_send);
        }
    }

    /**
     * 取消界面
     */
    public void wantToCancel() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.mipmap.aurora_recordvoice_cancle);
            mLabel.setText(R.string.unpress_cancel_send);
        }

    }

    /**
     * 时间过短
     */
    public void tooShort() {
        if (mDialog != null && mDialog.isShowing()) {
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.mipmap.aurora_voice_to_short);
            mLabel.setText(R.string.record_voice_time_short);
        }

    }

    // 隐藏dialog
    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
            mDialog = null;
        }

    }

    public void updateVoiceLevel(int level) {
        if (mDialog != null && mDialog.isShowing()) {

            //先不改变它的默认状态
//			mIcon.setVisibility(View.VISIBLE);
//			mVoice.setVisibility(View.VISIBLE);
//			mLabel.setVisibility(View.VISIBLE);
            switch (level) {
                case 1:
                    mVoice.setImageResource(R.mipmap.vol1);
                    break;
                case 2:
                    mVoice.setImageResource(R.mipmap.vol2);
                    break;
                case 3:
                    mVoice.setImageResource(R.mipmap.vol3);
                    break;
                case 4:
                    mVoice.setImageResource(R.mipmap.vol4);
                    break;
                case 5:
                    mVoice.setImageResource(R.mipmap.vol5);
                    break;
                case 6:
                    mVoice.setImageResource(R.mipmap.vol6);
                    break;
                case 7:
                    mVoice.setImageResource(R.mipmap.vol7);
                    break;
                default:
            }
        }

    }

}
