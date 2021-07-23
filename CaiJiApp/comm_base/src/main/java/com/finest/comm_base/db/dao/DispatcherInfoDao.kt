package com.finest.comm_base.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.finest.comm_base.db.entity.DispatcherInfo

/**
 * Created by liangjiangze on 2021/1/20.
 */
@Dao
interface DispatcherInfoDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dispatcherInfo: DispatcherInfo?)
    @Query(
        "SELECT * FROM DispatcherInfo "
    )
    fun getDispatcherInfoByGroudId(): List<DispatcherInfo?>?

}