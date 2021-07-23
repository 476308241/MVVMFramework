package com.finest.chatlibrary.commons.model;


/**
 * @author cjp
 * @time 2018/9/30
 */

public interface FileLoader<MESSAGE extends IMessage> {
    void loadFile(MESSAGE message);
}
