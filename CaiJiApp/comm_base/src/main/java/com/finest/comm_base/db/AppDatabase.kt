package com.finest.comm_base.db

import android.content.Context
import android.os.Environment
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.blankj.utilcode.util.FileUtils
import com.finest.comm_base.db.dao.DispatcherInfoDao
import com.finest.comm_base.db.dao.UserDao
import com.finest.comm_base.db.entity.DispatcherInfo
import com.finest.comm_base.db.entity.User

import java.io.File

/**
 * Created by liangjiangze on 2020/5/9.
 * entities配置时注意，不要把User::class配成UserDao::class
 */
@Database(entities = [User::class, DispatcherInfo::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun dispatcherInfoDao(): DispatcherInfoDao
    companion object {
        var dbName = "mxapp.db"
        private val DB_PATH = Environment.getRootDirectory().toString()+"/CAIJIFinest/db"
        private var dbFilePathAndName = File(DB_PATH, dbName).absolutePath
        private var instance: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                Log.e("TEST1111","创建数据库时"+ DB_PATH)
                FileUtils.createOrExistsDir(DB_PATH)
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    dbFilePathAndName
                ).addCallback(object :RoomDatabase.Callback(){
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        Log.e("TEST1111","e.message//创建数据库时"+ dbFilePathAndName)
                        //创建数据库时
                    }
                }).addMigrations(object :Migration(1,2){
                    override fun migrate(database: SupportSQLiteDatabase) {
                        //从版本1升级到版本2
                    }
                }).allowMainThreadQueries().build()
            }
            return instance as AppDatabase
        }
    }

}