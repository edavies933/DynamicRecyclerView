package com.example.emmanueldaviest.internal.di.module

import android.app.Application
import com.emmanueldavies.flixbus.internal.Utility.INetworkManager
import com.emmanueldavies.flixbus.internal.Utility.NetWorkManagerImpl
import com.example.emmanueldaviest.data.repository.api.HeadlineApiInterface
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideHeadlineService(okHttpClient: OkHttpClient): HeadlineApiInterface {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(HeadlineApiInterface::class.java)
    }

    @Provides
    @Singleton
    fun providesINetWorkManager(): INetworkManager {
        return NetWorkManagerImpl()
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(mockInterceptor: MockInterceptor): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(mockInterceptor)
        }.build()

    }

    @Provides
    @Singleton
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideMockInterceptor(application: Application): MockInterceptor = MockInterceptor(application)


    companion object {
        private const val BASE_URL = "https://www.google.com/"
    }

}


