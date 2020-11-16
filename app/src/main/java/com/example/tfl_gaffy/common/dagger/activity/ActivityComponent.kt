package com.example.tfl_gaffy.common.dagger.activity

import com.example.tfl_gaffy.common.dagger.session.SessionComponent
import com.example.tfl_gaffy.presentation.roadstatus.RoadStatusActivity
import dagger.Component

@Component(dependencies = [SessionComponent::class])

@ActivityScope
interface ActivityComponent : SessionComponent {
    fun inject(target: RoadStatusActivity)
}