package com.finest.comm_base.di

import com.finest.comm_net.NetworkManager
import com.finest.comm_net.request.HttpRequest
import com.finest.comm_net.request.URLManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * Created by liangjiangze on 2021/1/11.
 */
@InstallIn(ApplicationComponent::class)
@Module
class NetworkModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrlRequest

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class WarnUrlRequest

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class BaseUrlRequestLong

   @BaseUrlRequest
   @Singleton
   @Provides
   fun provideBaseUrlRequest(): HttpRequest {
       return NetworkManager.getRequest()
   }

    @WarnUrlRequest
    @Singleton
    @Provides
    fun provideWarnUrlRequest():HttpRequest{
        return NetworkManager.getRequest(
            "https://api.apiopen.top/")
    }

    @BaseUrlRequestLong
    @Singleton
    @Provides
    fun provideBaseUrlRequestLong():HttpRequest{
        return NetworkManager.getRequestLong()
    }
}