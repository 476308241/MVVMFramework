package com.finest.chatlibrary.commons.model;

import android.widget.ImageView;


public interface ImageLoader<MESSAGE extends IMessage> {
    /**
     * Load image into avatar's ImageView.
     *
     * @param avatarImageView Avatar's ImageView.
     * @param string          A file path, or a uri or url.
     */
    void loadAvatarImage(ImageView avatarImageView, String string);

    /**
     * Load image into image message's ImageView.
     *
     * @param imageView Image message's ImageView.
     * @param message   A Message.
     */
    void loadImage(ImageView imageView, MESSAGE message);

    /**
     * Load video to video message's image cover.
     *
     * @param imageCover Video message's image cover
     * @param message    A Message.
     */
    void loadVideo(ImageView imageCover, MESSAGE message);
}
