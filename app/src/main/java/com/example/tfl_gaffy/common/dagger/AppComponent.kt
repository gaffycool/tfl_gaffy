package com.example.tfl_gaffy.common.dagger

import android.content.Context
import com.google.gson.Gson
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        AppModule::class
    ]
)
@Singleton
interface AppComponent {

    fun context(): Context
    fun gson(): Gson
}