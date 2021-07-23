package com.finest.chatlibrary.messages;


import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;

import com.finest.chatlibrary.commons.model.IMessage;

import java.lang.ref.WeakReference;
import java.util.HashMap;

public class ViewHolderController {

    private static ViewHolderController mInstance;
    private static HashMap<Integer, ImageView> mData;
    private WeakReference<Integer> mLastPlayPosition = new WeakReference<>(-1);
    private WeakReference<Boolean> mIsSender;
    private WeakReference<Integer> mSendDrawable;
    private WeakReference<Integer> mReceiveDrawable;
    private WeakReference<ReplayVoiceListener> mListener;
    private WeakReference<IMessage> mMsg;

    private ViewHolderController() {
        mData = new HashMap<>();
    }

    public static ViewHolderController getInstance() {
        if (mInstance == null) {
            mInstance = new ViewHolderController();
        }
        return mInstance;
    }

    public void addView(int position, ImageView view) {
        if (mData == null) {
            mData = new HashMap<>(16);
        }
        mData.put(position, view);
    }

    public void setLastPlayPosition(int position, boolean isSender) {
        mLastPlayPosition = new WeakReference<>(position);
        mIsSender = new WeakReference<>(isSender);
    }

    public int getLastPlayPosition() {
        return mLastPlayPosition == null ? -1 : mLastPlayPosition.get();
    }

    public void notifyAnimStop() {
        ImageView imageView = mData.get(mLastPlayPosition.get());
        try {
            if (imageView != null) {
                AnimationDrawable anim = (AnimationDrawable) imageView.getDrawable();
                anim.stop();
                if (mIsSender.get()) {
                    imageView.setImageResource(mSendDrawable.get());
                } else {
                    imageView.setImageResource(mReceiveDrawable.get());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void remove(int position) {
        if (null != mData && mData.size() > 0) {
            mData.remove(position);
        }
    }

    public void setMessage(IMessage message) {
        this.mMsg = new WeakReference<>(message);
    }

    public IMessage getMessage() {
        return mMsg == null ? null : mMsg.get();
    }

    public void setReplayVoiceListener(ReplayVoiceListener listener) {
        this.mListener = new WeakReference<>(listener);
    }

    public void replayVoice() {
        mListener.get().replayVoice();
    }

    public void release() {
        if (mData != null) {
            mData.clear();
            mData = null;
        }
    }

    public void setDrawable(int sendDrawable, int receiveDrawable) {
        mSendDrawable = new WeakReference<>(sendDrawable);
        mReceiveDrawable = new WeakReference<>(receiveDrawable);
    }

    interface ReplayVoiceListener {
        void replayVoice();
    }
}
