package com.finest.chatlibrary.util;

public interface ICache {

    /**
     * 添加缓存字段
     *
     * @param key   键
     * @param value 值
     */
    void put(String key, Object value);

    /**
     * 获取缓存字段
     *
     * @param key 键
     * @return 值
     */

    Object get(String key, Class classs);

    /**
     * 删除缓存字段
     *
     * @param key 键
     */
    void remove(String key);

    /**
     * 是否包含指定字段
     *
     * @param key 键
     * @return
     */
    boolean contains(String key);

    /**
     * 清除缓存
     */
    void clear();

}

