package com.example.tfl_gaffy.common

import android.app.Application
import com.example.tfl_gaffy.common.dagger.AppComponent
import com.example.tfl_gaffy.common.dagger.AppModule
import com.example.tfl_gaffy.common.dagger.DaggerAppComponent
import com.example.tfl_gaffy.common.dagger.session.DaggerSessionComponent
import com.example.tfl_gaffy.common.dagger.session.SessionComponent

class App : Application() {
    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    private lateinit var sessionComponent: SessionComponent

    override fun onCreate() {
        super.onCreate()
        resetSession()
        //currentSession()
    }

    fun currentSession(): SessionComponent {
        return sessionComponent
    }

    fun resetSession() {

        sessionComponent = DaggerSessionComponent.builder()
            .appComponent(appComponent)
            .build()

    }
}
