package com.finest.chatlibrary.commons.model;


/**
 * @author cjp
 * @time 2018/9/30
 */

public interface VoiceLoader<MESSAGE extends IMessage> {
    void loadVoice(MESSAGE message);
}
