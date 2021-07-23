package com.finest.chatlibrary.messages;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.finest.chatlibrary.R;
import com.finest.chatlibrary.adapter.MessageListAdapter;
import com.finest.chatlibrary.commons.model.IMessage;
import com.finest.chatlibrary.entity.MessageBean;
import com.finest.chatlibrary.view.RoundImageView;
import com.finest.chatlibrary.view.RoundTextView;


public class FileViewHolder<Message extends IMessage> extends BaseMessageViewHolder<Message>
        implements MessageListAdapter.DefaultMessageViewHolder {

    private boolean mIsSender;
    private RoundTextView mDateTv;
    private TextView mDisplayNameTv;
    private TextView mFileTv;
    private RoundImageView mAvatarIv;
    private ProgressBar mSendingPb;
    private ImageButton mResendIb;


    public FileViewHolder(View itemView, boolean isSender) {
        super(itemView);
        this.mIsSender = isSender;
        mDateTv = (RoundTextView) itemView.findViewById(R.id.aurora_tv_msgitem_date);
        mFileTv = (TextView) itemView.findViewById(R.id.aurora_iv_msgitem_file);
        mAvatarIv = (RoundImageView) itemView.findViewById(R.id.aurora_iv_msgitem_avatar);
        mSendingPb = (ProgressBar) itemView.findViewById(R.id.aurora_pb_msgitem_sending);
        if (mIsSender) {
            mResendIb = (ImageButton) itemView.findViewById(R.id.aurora_ib_msgitem_resend);
            mDisplayNameTv = (TextView) itemView.findViewById(R.id.aurora_tv_msgitem_sender_display_name);
        } else {
            mDisplayNameTv = (TextView) itemView.findViewById(R.id.aurora_tv_msgitem_receiver_display_name);
        }
    }

    @Override
    public void applyStyle(MessageListStyle style) {
        mDateTv.setTextSize(style.getDateTextSize());
        mDateTv.setTextColor(style.getDateTextColor());
        mDateTv.setPadding(style.getDatePaddingLeft(), style.getDatePaddingTop(),
                style.getDatePaddingRight(), style.getDatePaddingBottom());
        mDateTv.setBgCornerRadius(style.getDateBgCornerRadius());
        mDateTv.setBgColor(style.getDateBgColor());
        if (style.getSendingIndeterminateDrawable() != null) {
            mSendingPb.setIndeterminateDrawable(style.getSendingIndeterminateDrawable());
        }
        if (mIsSender) {
            if (style.getSendingProgressDrawable() != null) {
                mSendingPb.setProgressDrawable(style.getSendingProgressDrawable());
            }
            if (style.getShowSenderDisplayName()) {
                mDisplayNameTv.setVisibility(View.VISIBLE);
            } else {
                mDisplayNameTv.setVisibility(View.GONE);
            }
        } else {
            if (style.getShowReceiverDisplayName()) {
                mDisplayNameTv.setVisibility(View.VISIBLE);
            } else {
                mDisplayNameTv.setVisibility(View.GONE);
            }
        }
        mDisplayNameTv.setTextSize(style.getDisplayNameTextSize());
        mDisplayNameTv.setTextColor(style.getDisplayNameTextColor());
        mDisplayNameTv.setPadding(style.getDisplayNamePaddingLeft(), style.getDisplayNamePaddingTop(),
                style.getDisplayNamePaddingRight(), style.getDisplayNamePaddingBottom());
        android.view.ViewGroup.LayoutParams layoutParams = mAvatarIv.getLayoutParams();
        layoutParams.width = style.getAvatarWidth();
        layoutParams.height = style.getAvatarHeight();
        mAvatarIv.setLayoutParams(layoutParams);
        mAvatarIv.setBorderRadius(style.getAvatarRadius());
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBind(Message message) {
        String timeString = message.getTimeString();
        mDateTv.setVisibility(View.VISIBLE);
        if (timeString != null && !TextUtils.isEmpty(timeString)) {
            mDateTv.setText(timeString);
        } else {
            mDateTv.setVisibility(View.GONE);
        }
        boolean isAvatarExists = message.getFromUser().getAvatarFilePath() != null
                && !message.getFromUser().getAvatarFilePath().isEmpty();
        if (isAvatarExists && mImageLoader != null) {
            mImageLoader.loadAvatarImage(mAvatarIv, message.getFromUser().getAvatarFilePath());
        }

        mFileTv.setText(message.getText());
        Drawable fileTypeImage = getFileTypeImage(message.getText());
        fileTypeImage.setBounds(0, 0, fileTypeImage.getMinimumWidth(), fileTypeImage.getMinimumHeight());

        if (message.getType() == MessageBean.MessageType.RECEIVE_FILE.ordinal()) {
            mFileTv.setCompoundDrawables(null, null, fileTypeImage, null);
        } else {
            mFileTv.setCompoundDrawables(fileTypeImage, null, null, null);
        }

        mAvatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAvatarClickListener != null) {
                    mAvatarClickListener.onAvatarClick(message);
                }
            }
        });

        mFileTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mMsgClickListener != null) {
                    mMsgClickListener.onMessageClick(message);
                }
            }
        });

        mFileTv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mMsgLongClickListener != null) {
                    mMsgLongClickListener.onMessageLongClick(view, message);
                }
                return true;
            }
        });
        if (mDisplayNameTv.getVisibility() == View.VISIBLE) {
            mDisplayNameTv.setText(message.getFromUser().getDisplayName());
        }
        if (mIsSender) {
            switch (message.getMessageStatus()) {
                case SEND_GOING:
                    Log.i("FileViewHolder", "sending file, progress: " + message.getProgress());
                    mSendingPb.setVisibility(View.VISIBLE);
                    mResendIb.setVisibility(View.GONE);
                    if (mFileLoader != null) {
                        mFileLoader.loadFile(message);
                    }
                    break;
                case SEND_FAILED:
                    mResendIb.setVisibility(View.VISIBLE);
                    mSendingPb.setVisibility(View.GONE);
                    mResendIb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mMsgStatusViewClickListener != null) {
                                mMsgStatusViewClickListener.onStatusViewClick(message);
                            }
                        }
                    });
                    Log.i("FileViewHolder", "send file failed");
                    break;
                case SEND_SUCCEED:
                    mSendingPb.setVisibility(View.GONE);
                    mResendIb.setVisibility(View.GONE);
                    Log.i("FileViewHolder", "send file succeed");
                    break;
                default:
            }
        } else {
            switch (message.getMessageStatus()) {
                case RECEIVE_GOING:
                    mSendingPb.setVisibility(View.VISIBLE);
                    if (mFileLoader != null) {
                        mFileLoader.loadFile(message);
                    }
                    break;
                case RECEIVE_SUCCEED:
                    mSendingPb.setVisibility(View.GONE);
                    break;
                default:
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private Drawable getFileTypeImage(String fileName) {
        int resId = R.drawable.ic_file;
        if (!TextUtils.isEmpty(fileName)) {
            int i = fileName.lastIndexOf(".");
            if (i > 0) {
                String fileExtra = fileName.substring(i + 1);
                switch (fileExtra) {
                    case "txt":
                    case "TXT":
                        resId = R.drawable.ic_txt;
                        break;
                    case "ppt":
                    case "PPT":
                    case "pptx":
                    case "PPTX":
                        resId = R.drawable.ic_ppt;
                        break;
                    case "doc":
                    case "docx":
                    case "DOCX":
                    case "DOC":
                        resId = R.drawable.ic_word;
                        break;
                    case "xls":
                    case "xlsx":
                    case "XLS":
                    case "XLSX":
                        resId = R.drawable.ic_excel;
                        break;
                    case "pdf":
                    case "PDF":
                        resId = R.drawable.ic_pdf;
                        break;
                    default:
                        break;
                }
            }
        }

        return mContext.getDrawable(resId);
    }
}