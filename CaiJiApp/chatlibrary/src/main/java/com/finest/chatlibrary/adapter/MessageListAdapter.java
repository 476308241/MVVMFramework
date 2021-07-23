package com.finest.chatlibrary.adapter;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.finest.chatlibrary.R;
import com.finest.chatlibrary.commons.model.FileLoader;
import com.finest.chatlibrary.commons.model.IMessage;
import com.finest.chatlibrary.commons.model.ImageLoader;
import com.finest.chatlibrary.commons.model.VoiceLoader;
import com.finest.chatlibrary.messages.BaseMessageViewHolder;
import com.finest.chatlibrary.messages.EventViewHolder;
import com.finest.chatlibrary.messages.FileViewHolder;
import com.finest.chatlibrary.messages.MessageListStyle;
import com.finest.chatlibrary.messages.PhotoViewHolder;
import com.finest.chatlibrary.messages.TxtViewHolder;
import com.finest.chatlibrary.messages.VideoViewHolder;
import com.finest.chatlibrary.messages.ViewHolderController;
import com.finest.chatlibrary.messages.VoiceViewHolder;

import java.util.ArrayList;
import java.util.List;

public class MessageListAdapter<MESSAGE extends IMessage> extends BaseMultiItemQuickAdapter<MESSAGE, BaseViewHolder> {
    private Context mContext;
    private HoldersConfig mHolders;
    private ImageLoader mImageLoader;
    private VoiceLoader mVoiceLoader;
    private FileLoader mFileLoader;
    private boolean mIsSelectedMode;
    private OnMsgClickListener<MESSAGE> mMsgClickListener;
    private OnMsgLongClickListener<MESSAGE> mMsgLongClickListener;
    private OnAvatarClickListener<MESSAGE> mAvatarClickListener;
    private OnMsgStatusViewClickListener<MESSAGE> mMsgStatusViewClickListener;
    private SelectionListener mSelectionListener;
    private int mSelectedItemCount;
    private LinearLayoutManager mLayoutManager;
    private MessageListStyle mStyle;
    private MediaPlayer mMediaPlayer;
    private AudioManager audioManager = null;
    private int peerSize = 10;
    private boolean hasMoreData;
    private boolean mScroll;

    public MessageListAdapter(@Nullable List<MESSAGE> data, ImageLoader imageLoader) {
        this(data, new HoldersConfig(), imageLoader);
        addItemType();
    }

    public void setPeerSize(int peerSize) {
        this.peerSize = peerSize;
    }

    public int getPeerSize() {
        return peerSize;
    }

    public boolean hasMoreData() {
        return hasMoreData;
    }

    public void setHasMoreData(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
    }

    /**
     * 设置布局类型
     */
    private void addItemType() {
        addItemType(IMessage.MessageType.SEND_TEXT.ordinal(), mHolders.mSendTxtLayout);
        addItemType(IMessage.MessageType.RECEIVE_TEXT.ordinal(), mHolders.mReceiveTxtLayout);

        addItemType(IMessage.MessageType.SEND_IMAGE.ordinal(), mHolders.mSendPhotoLayout);
        addItemType(IMessage.MessageType.RECEIVE_IMAGE.ordinal(), mHolders.mReceivePhotoLayout);

        addItemType(IMessage.MessageType.SEND_VOICE.ordinal(), mHolders.mSendVoiceLayout);
        addItemType(IMessage.MessageType.RECEIVE_VOICE.ordinal(), mHolders.mReceiveVoiceLayout);

        addItemType(IMessage.MessageType.SEND_VIDEO.ordinal(), mHolders.mSendVideoLayout);
        addItemType(IMessage.MessageType.RECEIVE_VIDEO.ordinal(), mHolders.mReceiveVideoLayout);

        addItemType(IMessage.MessageType.SEND_FILE.ordinal(), mHolders.mSendFileLayout);
        addItemType(IMessage.MessageType.RECEIVE_FILE.ordinal(), mHolders.mReceiveFileLayout);

        addItemType(IMessage.MessageType.SEND_LOCATION.ordinal(), mHolders.mSendLocationLayout);
        addItemType(IMessage.MessageType.RECEIVE_LOCATION.ordinal(), mHolders.mReceiveLocationLayout);

        addItemType(IMessage.MessageType.EVENT.ordinal(), mHolders.mEventLayout);
    }

    private MessageListAdapter(@Nullable List<MESSAGE> data, HoldersConfig holders, ImageLoader imageLoader) {
        super(data);
        mMediaPlayer = new MediaPlayer();
        mHolders = holders;
        mImageLoader = imageLoader;
    }

    @Override
    protected void convert(BaseViewHolder holder, MESSAGE item) {
        BaseMessageViewHolder baseHolder = null;
        if (item != null) {
            if (item.getItemType() == IMessage.MessageType.SEND_TEXT.ordinal()) {
                baseHolder = new DefaultTxtViewHolder(holder.itemView, true);
            } else if (item.getItemType() == IMessage.MessageType.RECEIVE_TEXT.ordinal()) {
                baseHolder = new DefaultTxtViewHolder(holder.itemView, false);
            } else if (item.getItemType() == IMessage.MessageType.SEND_IMAGE.ordinal()) {
                baseHolder = new DefaultPhotoViewHolder(holder.itemView, true);
            } else if (item.getItemType() == IMessage.MessageType.RECEIVE_IMAGE.ordinal()) {
                baseHolder = new DefaultPhotoViewHolder(holder.itemView, false);
            } else if (item.getItemType() == IMessage.MessageType.SEND_VOICE.ordinal()) {
                baseHolder = new DefaultVoiceViewHolder(holder.itemView, true);
            } else if (item.getItemType() == IMessage.MessageType.RECEIVE_VOICE.ordinal()) {
                baseHolder = new DefaultVoiceViewHolder(holder.itemView, false);
            } else if (item.getItemType() == IMessage.MessageType.SEND_VIDEO.ordinal()) {
                baseHolder = new DefaultVideoViewHolder(holder.itemView, true);
            } else if (item.getItemType() == IMessage.MessageType.RECEIVE_VIDEO.ordinal()) {
                baseHolder = new DefaultVideoViewHolder(holder.itemView, false);
            } else if (item.getItemType() == IMessage.MessageType.SEND_FILE.ordinal()) {
                baseHolder = new DefaultFileViewHolder(holder.itemView, true);
            } else if (item.getItemType() == IMessage.MessageType.RECEIVE_FILE.ordinal()) {
                baseHolder = new DefaultFileViewHolder(holder.itemView, false);
            } else if (item.getItemType() == IMessage.MessageType.SEND_LOCATION.ordinal()) {
//                baseHolder = new DefaultMessageViewHolder(holder.itemView, true);
            } else if (item.getItemType() == IMessage.MessageType.RECEIVE_LOCATION.ordinal()) {
//                baseHolder = new DefaultMessageViewHolder(holder.itemView, false);
            } else if (item.getItemType() == IMessage.MessageType.EVENT.ordinal()) {
                baseHolder = new DefaultEventMsgViewHolder(holder.itemView, true);
            }

            if (baseHolder != null) {
                if (baseHolder instanceof DefaultMessageViewHolder) {
                    ((DefaultMessageViewHolder) baseHolder).applyStyle(mStyle);
                }
                baseHolder.mPosition = holder.getAdapterPosition();
                baseHolder.mContext = this.mContext;
                DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
                baseHolder.mDensity = dm.density;
                baseHolder.mIsSelected = item.isSelected();
                baseHolder.mImageLoader = this.mImageLoader;
                baseHolder.mMsgLongClickListener = this.mMsgLongClickListener;
                baseHolder.mMsgClickListener = this.mMsgClickListener;
                baseHolder.mAvatarClickListener = this.mAvatarClickListener;
                baseHolder.mMsgStatusViewClickListener = this.mMsgStatusViewClickListener;
                baseHolder.mMediaPlayer = this.mMediaPlayer;
                baseHolder.mScroll = this.mScroll;
                baseHolder.mVoiceLoader = this.mVoiceLoader;
                baseHolder.mFileLoader = this.mFileLoader;

                baseHolder.onBind(item);
            }
        }
    }

    public void setScrolling(boolean scroll) {
        this.mScroll = scroll;
    }

    public boolean getScrolling() {
        return this.mScroll;
    }

    public void setAudioPlayByEarPhone(int state) {
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        // 外放模式
        if (state == 0) {
            changeToSpeaker();
            // 耳机模式
        } else if (state == 1) {
            changeToHeadset();
            // 听筒模式
        } else {
            changeToReceiver();
            int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, currVolume,
                    AudioManager.STREAM_VOICE_CALL);
        }
    }

    /**
     * 切换到外放
     */
    private void changeToSpeaker() {
        audioManager.setMode(AudioManager.MODE_NORMAL);
        audioManager.setSpeakerphoneOn(true);
    }

    /**
     * 切换到耳机模式
     */
    private void changeToHeadset() {
        audioManager.setSpeakerphoneOn(false);
    }

    /**
     * 切换到听筒
     */
    private void changeToReceiver() {
        audioManager.setSpeakerphoneOn(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
        } else {
            audioManager.setMode(AudioManager.MODE_IN_CALL);
        }
    }

    public void pauseVoice() {
        try {
            mMediaPlayer.pause();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public void stopVoice() {
        try {
            if (mMediaPlayer != null) {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer.release();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    /**
     * Add message to bottom of list
     *
     * @param message        message to be add
     * @param scrollToBottom if true scroll list to bottom
     */
    public void addData(MESSAGE message, boolean scrollToBottom) {
        getData().add(message);
        notifyItemRangeInserted(getData().size() - 1, 1);
        if (mLayoutManager != null && scrollToBottom) {
            mLayoutManager.scrollToPosition(getData().size() - 1);
        }
    }

    public void loadMore(List<MESSAGE> messages) {
        addToStartChronologically(messages);
    }

    /**
     * If messages in list is sorted chronologically, for example, messages[0].timeString < messages[1].timeString.
     * To load last page of messages from history, use this method.
     *
     * @param messages Last page of messages.
     */
    private void addToStartChronologically(List<MESSAGE> messages) {
        getData().addAll(0, messages);
        notifyItemRangeInserted(0, messages.size());
        mLayoutManager.scrollToPosition(messages.size() - 1);
    }

    private int getMessagePositionById(String id) {
        for (int i = 0; i < getData().size(); i++) {
            if (getData().get(i) != null) {
                if (getData().get(i).getMsgId().contentEquals(id)) {
                    return i;
                }
            }
        }
        return -1;
    }

    public MESSAGE getMessageById(String id) {
        for (int i = 0; i < getData().size(); i++) {
            MESSAGE message = getData().get(i);
            if (message != null) {
                if (message.getMsgId().contentEquals(id)) {
                    return message;
                }
            }
        }
        return null;
    }

    /**
     * Update message by its id.
     *
     * @param message message to be updated.
     */
    public void updateMessage(MESSAGE message) {
        updateMessage(message.getMsgId(), message);
    }

    /**
     * Updates message by old identifier.
     *
     * @param oldId      message id to be updated
     * @param newMessage message to be updated
     */
    public void updateMessage(String oldId, MESSAGE newMessage) {
        int position = getMessagePositionById(oldId);
        if (position >= 0) {
            getData().set(position, newMessage);
            notifyItemChanged(position);
        }
    }

    /**
     * Updates message by old identifier.
     *
     * @param message message
     */
    public void moveMessageToStart(MESSAGE message) {
        int position = getMessagePositionById(message.getMsgId());
        if (position != getData().size() - 1) {
            getData().remove(position);
            getData().add(message);
            notifyItemMoved(position, getData().size() - 1);
            if (mLayoutManager != null) {
                mLayoutManager.scrollToPosition(getData().size() - 1);
            }
        } else {
            updateMessage(message);
        }
    }

    /**
     * Delete message.
     *
     * @param message message to be deleted.
     */
    public void delete(MESSAGE message) {
        deleteById(message.getMsgId());
    }

    /**
     * Delete message by identifier.
     *
     * @param id identifier of message.
     */
    public void deleteById(String id) {
        int index = getMessagePositionById(id);
        if (index >= 0) {
            getData().remove(index);
            notifyItemRemoved(index);
        }
    }

    /**
     * Delete messages.
     *
     * @param messages messages list to be deleted.
     */
    public void delete(List<MESSAGE> messages) {
        for (MESSAGE message : messages) {
            int index = getMessagePositionById(message.getMsgId());
            if (index >= 0) {
                getData().remove(index);
                notifyItemRemoved(index);
            }
        }
    }

    /**
     * Delete messages by identifiers.
     *
     * @param ids ids array of identifiers of messages to be deleted.
     */
    public void deleteByIds(String[] ids) {
        for (String id : ids) {
            int index = getMessagePositionById(id);
            if (index >= 0) {
                getData().remove(index);
                notifyItemRemoved(index);
            }
        }
    }

    /**
     * Clear messages list.
     */
    public void clear() {
        getData().clear();
        notifyDataSetChanged();
    }

    /**
     * Enable selection mode.
     *
     * @param listener SelectionListener. To get selected messages use {@link #getSelectedMessages()}.
     */
    public void enableSelectionMode(SelectionListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("SelectionListener must not be null.");
        } else {
            mSelectionListener = listener;
        }
    }

    /**
     * Disable selection mode, and deselect all items.
     */
    public void disableSelectionMode() {
        mSelectionListener = null;
        deselectAllItems();
    }

    /**
     * Get selected messages.
     *
     * @return ArrayList with selected messages.
     */
    @SuppressWarnings("unchecked")
    public ArrayList<MESSAGE> getSelectedMessages() {
        ArrayList<MESSAGE> list = new ArrayList<>();
        for (MESSAGE message : getData()) {
            if (message != null && message.isSelected()) {
                list.add(message);
            }
        }
        return list;
    }

    /**
     * Delete all selected messages
     */
    public void deleteSelectedMessages() {
        List<MESSAGE> selectedMessages = getSelectedMessages();
        delete(selectedMessages);
        deselectAllItems();
    }

    /**
     * Deselect all items.
     */
    public void deselectAllItems() {
        for (int i = 0; i < getData().size(); i++) {
            MESSAGE message = getData().get(i);
            if (message.isSelected()) {
                message.setSelected(false);
                notifyItemChanged(i);
            }
        }
        mIsSelectedMode = false;
        mSelectedItemCount = 0;
        notifySelectionChanged();
    }

    private void notifySelectionChanged() {
        if (mSelectionListener != null) {
            mSelectionListener.onSelectionChanged(mSelectedItemCount);
        }
    }

    /**
     * Set onMsgClickListener, fires onClick event only if list is not in selection mode.
     *
     * @param listener OnMsgClickListener
     */
    public void setOnMsgClickListener(OnMsgClickListener<MESSAGE> listener) {
        mMsgClickListener = listener;
    }

    private View.OnClickListener getMsgClickListener(final MESSAGE message) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelectionListener != null && mIsSelectedMode) {
                    message.setSelected(!message.isSelected());
                    if (message.isSelected()) {
                        incrementSelectedItemsCount();
                    } else {
                        decrementSelectedItemsCount();
                    }

                    notifyItemChanged(getMessagePositionById(message.getMsgId()));
                } else {
                    notifyMessageClicked(message);
                }
            }
        };
    }

    private void incrementSelectedItemsCount() {
        mSelectedItemCount++;
        notifySelectionChanged();
    }

    private void decrementSelectedItemsCount() {
        mSelectedItemCount--;
        mIsSelectedMode = mSelectedItemCount > 0;
        notifySelectionChanged();
    }

    private void notifyMessageClicked(MESSAGE message) {
        if (mMsgClickListener != null) {
            mMsgClickListener.onMessageClick(message);
        }
    }

    /**
     * Set long click listener for dispatch_item_sug_search, fires only if selection mode is disabled.
     *
     * @param listener onMsgLongClickListener
     */
    public void setMsgLongClickListener(OnMsgLongClickListener<MESSAGE> listener) {
        mMsgLongClickListener = listener;
    }

    private View.OnLongClickListener getMessageLongClickListener(final MESSAGE message) {
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mSelectionListener == null) {
                    notifyMessageLongClicked(view, message);
                    return true;
                } else {
                    mIsSelectedMode = true;
                    view.callOnClick();
                    return true;
                }
            }
        };
    }

    private void notifyMessageLongClicked(View view, MESSAGE message) {
        if (mMsgLongClickListener != null) {
            mMsgLongClickListener.onMessageLongClick(view, message);
        }
    }

    public void setOnAvatarClickListener(OnAvatarClickListener<MESSAGE> listener) {
        mAvatarClickListener = listener;
    }

    public void setLayoutManager(LinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    public void setStyle(Context context, MessageListStyle style) {
        mContext = context;
        mStyle = style;
    }

    public interface DefaultMessageViewHolder {
        void applyStyle(MessageListStyle style);
    }

    public interface SelectionListener {
        void onSelectionChanged(int count);
    }

    /**
     * Callback will invoked when message dispatch_item_sug_search is clicked
     *
     * @param <MESSAGE>
     */
    public interface OnMsgClickListener<MESSAGE extends IMessage> {
        void onMessageClick(MESSAGE message);
    }

    /**
     * Callback will invoked when message dispatch_item_sug_search is long clicked
     *
     * @param <MESSAGE>
     */
    public interface OnMsgLongClickListener<MESSAGE extends IMessage> {
        void onMessageLongClick(View view, MESSAGE message);
    }

    public interface OnAvatarClickListener<MESSAGE extends IMessage> {
        void onAvatarClick(MESSAGE message);
    }

    public void setMsgStatusViewClickListener(OnMsgStatusViewClickListener<MESSAGE> listener) {
        this.mMsgStatusViewClickListener = listener;
    }

    public interface OnMsgStatusViewClickListener<MESSAGE extends IMessage> {
        void onStatusViewClick(MESSAGE message);
    }

    public void setVoiceLoader(VoiceLoader voiceLoader) {
        this.mVoiceLoader = voiceLoader;
    }

    public void setFileLoader(FileLoader fileLoader) {
        this.mFileLoader = fileLoader;
    }

    /**
     * Holders Config
     * Config your custom layouts and view holders into adapter.
     * You need instantiate HoldersConfig, otherwise will use default MessageListStyle.
     */
    public static class HoldersConfig {
        private int mSendTxtLayout;
        private int mReceiveTxtLayout;

        private int mSendLocationLayout;
        private int mReceiveLocationLayout;

        private int mSendVoiceLayout;
        private int mReceiveVoiceLayout;

        private int mSendPhotoLayout;
        private int mReceivePhotoLayout;

        private int mSendVideoLayout;
        private int mReceiveVideoLayout;

        private int mSendFileLayout;
        private int mReceiveFileLayout;

        private int mEventLayout;

        public HoldersConfig() {
            mSendTxtLayout = R.layout.dispatch_item_aurora_send_txt;
            mReceiveTxtLayout = R.layout.dispatch_item_aurora_receive_txt;

            mSendVoiceLayout = R.layout.dispatch_item_aurora_send_voice;
            mReceiveVoiceLayout = R.layout.dispatch_item_aurora_receive_voice;

            mSendPhotoLayout = R.layout.dispatch_item_aurora_send_photo;
            mReceivePhotoLayout = R.layout.dispatch_item_aurora_receive_photo;

            mSendVideoLayout = R.layout.dispatch_item_aurora_send_video;
            mReceiveVideoLayout = R.layout.dispatch_item_aurora_receive_video;

            mSendFileLayout = R.layout.dispatch_item_aurora_send_file;
            mReceiveFileLayout = R.layout.dispatch_item_aurora_receive_file;

            mEventLayout = R.layout.dispatch_item_event_message;
        }
    }

    private static class DefaultTxtViewHolder extends TxtViewHolder<IMessage> {

        public DefaultTxtViewHolder(View itemView, boolean isSender) {
            super(itemView, isSender);
        }
    }

    private static class DefaultVoiceViewHolder extends VoiceViewHolder<IMessage> {

        public DefaultVoiceViewHolder(View itemView, boolean isSender) {
            super(itemView, isSender);
        }
    }

    private static class DefaultPhotoViewHolder extends PhotoViewHolder<IMessage> {

        public DefaultPhotoViewHolder(View itemView, boolean isSender) {
            super(itemView, isSender);
        }
    }

    private static class DefaultVideoViewHolder extends VideoViewHolder<IMessage> {

        public DefaultVideoViewHolder(View itemView, boolean isSender) {
            super(itemView, isSender);
        }
    }

    private static class DefaultFileViewHolder extends FileViewHolder<IMessage> {

        public DefaultFileViewHolder(View itemView, boolean isSender) {
            super(itemView, isSender);
        }
    }

    private static class DefaultEventMsgViewHolder extends EventViewHolder<IMessage> {
        public DefaultEventMsgViewHolder(View itemView, boolean isSender) {
            super(itemView, isSender);
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        ViewHolderController.getInstance().release();
    }
}

