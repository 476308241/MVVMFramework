package com.finest.comm_base.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

/**
 * Created by liangjiangze on 2020/5/9.
 */

@Entity(tableName = "user")
 class User{
    @PrimaryKey(autoGenerate = true)
    var userId: Long?=null

    @ColumnInfo(name = "user_name")
    var userName: String?=null

    @ColumnInfo(defaultValue = "china")
    var address: String?=null
    @Ignore var sex: Boolean?=null
}


