package com.finest.chatlibrary.util;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefChat implements ICache {

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    static final String SP_NAME = "config";

    private static SharedPrefChat instance;

    private SharedPrefChat(Context context) {
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPrefChat getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedPrefChat.class) {
                if (instance == null) {
                    instance = new SharedPrefChat(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    @Override
    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }

    @Override
    public boolean contains(String key) {
        return sharedPreferences.contains(key);
    }

    @Override
    public void clear() {
        editor.clear().apply();
    }

    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }

    public void putLong(String key, Long value) {
        editor.putLong(key, value);
        editor.apply();
    }

    public long getLong(String key, long defValue) {
        return sharedPreferences.getLong(key, defValue);
    }

    public void putBoolean(String key, Boolean value) {
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key, boolean defValue) {
        return sharedPreferences.getBoolean(key, defValue);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    @Override
    public Object get(String key, Class classs) {
//        String str = sharedPreferences.getString(key, "");
//        return GsonJsonUtil.fromJson(str, classs);
        return null;
    }

    @Override
    public void put(String key, Object value) {
//        editor.putString(key, GsonJsonUtil.toJson(value));
//        editor.apply();
    }

    public boolean isfirst() {
        boolean isFirstRun = getBoolean("isFirstRun", false);

        if (isFirstRun) {
            return false;
        } else {
            putBoolean("isFirstRun", true);
            return true;
        }
    }

}
