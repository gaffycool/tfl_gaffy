package com.example.tfl_gaffy.domain

import com.example.tfl_gaffy.data.model.RoadModel
import com.example.tfl_gaffy.data.service.ApiManagerImpl
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RoadStatusRepo @Inject constructor(private val apiManager: ApiManagerImpl) {

    fun execute(roadName: String): Single<List<RoadModel>> {
        return apiManager
            .getRoadStatus(roadName)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}
