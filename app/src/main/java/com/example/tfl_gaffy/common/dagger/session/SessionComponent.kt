package com.example.tfl_gaffy.common.dagger.session

import com.example.tfl_gaffy.common.dagger.AppComponent
import com.example.tfl_gaffy.common.dagger.CommonOkHttpClientBuilderModule
import com.example.tfl_gaffy.common.dagger.roadstatus.RoadStatusModule
import com.example.tfl_gaffy.common.dagger.roadstatus.SessionScope
import com.example.tfl_gaffy.data.service.ApiManager
import com.example.tfl_gaffy.data.service.ServiceApi
import dagger.Component

@Component(
    dependencies = [AppComponent::class],
    modules = [RoadStatusModule::class, CommonOkHttpClientBuilderModule::class]
)

@SessionScope
interface SessionComponent : AppComponent {
    fun apiManager(): ApiManager
    fun iService(): ServiceApi
}
