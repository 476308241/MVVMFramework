package com.finest.chatlibrary.messages;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.View;

import com.finest.chatlibrary.adapter.MessageListAdapter;
import com.finest.chatlibrary.commons.model.FileLoader;
import com.finest.chatlibrary.commons.model.IMessage;
import com.finest.chatlibrary.commons.model.ImageLoader;
import com.finest.chatlibrary.commons.model.ViewHolder;
import com.finest.chatlibrary.commons.model.VoiceLoader;


public abstract class BaseMessageViewHolder<MESSAGE extends IMessage>
        extends ViewHolder<MESSAGE> {

    public Context mContext;

    public float mDensity;
    public int mPosition;
    public boolean mIsSelected;
    public ImageLoader<MESSAGE> mImageLoader;
    public VoiceLoader<MESSAGE> mVoiceLoader;
    public FileLoader<MESSAGE> mFileLoader;

    public MessageListAdapter.OnMsgLongClickListener<MESSAGE> mMsgLongClickListener;
    public MessageListAdapter.OnMsgClickListener<MESSAGE> mMsgClickListener;
    public MessageListAdapter.OnAvatarClickListener<MESSAGE> mAvatarClickListener;
    public MessageListAdapter.OnMsgStatusViewClickListener<MESSAGE> mMsgStatusViewClickListener;
    public MediaPlayer mMediaPlayer;
    public boolean mScroll;

    public BaseMessageViewHolder(View itemView) {
        super(itemView);
    }
}