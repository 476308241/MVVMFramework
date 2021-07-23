package com.finest.comm_base.di

import android.content.Context

import com.finest.comm_base.db.AppDatabase
import com.finest.comm_base.db.dao.DispatcherInfoDao
import com.finest.comm_base.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

/**
 * Created by liangjiangze on 2021/1/7.
 */
@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabaseDaggerHilt(@ApplicationContext appContext: Context): AppDatabase {
        return AppDatabase.getInstance(appContext)
    }

    @Provides
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    fun provideDispatcherInfoDao(database: AppDatabase): DispatcherInfoDao {
        return database.dispatcherInfoDao()
    }
}