package com.example.tfl_gaffy.common.dagger

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Module
class CommonOkHttpClientBuilderModule {

    @Provides
    fun providesCommonOkHttpClientBuilder(
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
    }
}
