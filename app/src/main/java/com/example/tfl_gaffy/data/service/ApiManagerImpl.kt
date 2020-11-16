package com.example.tfl_gaffy.data.service

import com.example.tfl_gaffy.common.Constants
import com.example.tfl_gaffy.data.model.RoadModel
import io.reactivex.Single
import javax.inject.Inject

class ApiManagerImpl @Inject constructor(
    private val serviceApi: ServiceApi
) : ApiManager {
    override fun getRoadStatus(roadName: String): Single<List<RoadModel>> {
        return serviceApi.getRoadStatus(roadName, Constants.APP_KEY)
    }
}
