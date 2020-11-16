package com.example.tfl_gaffy.data.service

import com.example.tfl_gaffy.data.model.RoadModel
import io.reactivex.Single

interface ApiManager {

    fun getRoadStatus(roadName: String): Single<List<RoadModel>>
}