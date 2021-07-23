package com.finest.comm_base.db.dao


import androidx.room.*
import com.finest.comm_base.db.entity.User

/**
 * Created by liangjiangze on 2020/5/9.
 */
@Dao
interface UserDao{

    @Query("select * from user where userId = :id")
    fun getUserById(id: Long): User
    @Query("select * from user")
    fun getAllUsers():List<User>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addUser(user: User)
    @Delete
    fun deleteUserByUser(user: User)
    @Update
    fun updateUserByUser(user: User)

}