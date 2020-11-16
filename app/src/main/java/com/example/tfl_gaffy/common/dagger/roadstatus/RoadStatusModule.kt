package com.example.tfl_gaffy.common.dagger.roadstatus

import com.example.tfl_gaffy.common.Constants
import com.example.tfl_gaffy.data.service.ApiManager
import com.example.tfl_gaffy.data.service.ApiManagerImpl
import com.example.tfl_gaffy.data.service.ServiceApi
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RoadStatusModule {

    @Provides
    @SessionScope
    @RoadStatusQualifier
    fun okClient(okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return okHttpClientBuilder
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @SessionScope
    fun roadService(
        @RoadStatusQualifier okClient: OkHttpClient,
        gson: Gson
    ): ServiceApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(okClient)
            .build()
            .create(ServiceApi::class.java)
    }

    @Provides
    @SessionScope
    fun roadApi(
        restService: ServiceApi
    ): ApiManager = ApiManagerImpl(restService)
}
