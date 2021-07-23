package com.finest.chatlibrary.util;


import androidx.room.TypeConverter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeTransformation {

    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }


    @TypeConverter
    public static Long dateToTimestamp(Date value) {
        Long date = value == null ? null : value.getTime();
        return date;
    }
}