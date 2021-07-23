package com.finest.chatlibrary.messages;

import android.view.View;

import com.finest.chatlibrary.R;
import com.finest.chatlibrary.adapter.MessageListAdapter;
import com.finest.chatlibrary.commons.model.IMessage;
import com.finest.chatlibrary.view.RoundTextView;


public class EventViewHolder<MESSAGE extends IMessage>
        extends BaseMessageViewHolder<MESSAGE>
        implements MessageListAdapter.DefaultMessageViewHolder {

    private RoundTextView mEvent;

    public EventViewHolder(View itemView, boolean isSender) {
        super(itemView);
        mEvent = (RoundTextView) itemView.findViewById(R.id.aurora_tv_msgitem_event);
    }

    @Override
    public void onBind(MESSAGE message) {
        mEvent.setText(message.getText());
    }

    @Override
    public void applyStyle(MessageListStyle style) {
        mEvent.setTextColor(style.getEventTextColor());
        mEvent.setLineSpacing(style.getEventLineSpacingExtra(), 1.0f);
        mEvent.setBgColor(style.getEventBgColor());
        mEvent.setBgCornerRadius(style.getEventBgCornerRadius());
        mEvent.setTextSize(style.getEventTextSize());
        mEvent.setPadding(style.getEventPaddingLeft(), style.getEventPaddingTop(),
                style.getEventPaddingRight(), style.getEventPaddingBottom());
    }
}
