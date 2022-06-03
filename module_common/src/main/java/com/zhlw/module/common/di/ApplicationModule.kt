package com.zhlw.module.common.di

import android.content.Context
import com.tencent.mmkv.MMKV
import com.zhlw.module.common.constant.MMKV_CRYPT_KEY
import com.zhlw.module.common.constant.MMKV_ID
import com.zhlw.module.common.network.ClientManager
import com.zhlw.module.common.network.HostManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class ApplicationModule {

    @Provides
    fun provideMMKV(@ApplicationContext context: Context) : MMKV =
        MMKV.defaultMMKV(
            MMKV.SINGLE_PROCESS_MODE,
            MMKV_CRYPT_KEY
        )


    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient =
        ClientManager.getOkHttpClient(context)

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        ClientManager.getRetrofitClient(HostManager.getCurrentHost(), client)

}