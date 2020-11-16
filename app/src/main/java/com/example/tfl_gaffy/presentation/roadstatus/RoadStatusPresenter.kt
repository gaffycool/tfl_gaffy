package com.example.tfl_gaffy.presentation.roadstatus

import com.example.tfl_gaffy.data.model.RoadModel
import com.example.tfl_gaffy.domain.RoadStatusRepo
import com.example.tfl_gaffy.presentation.base.BasePresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RoadStatusPresenter @Inject constructor(
    private val roadStatusRepo: RoadStatusRepo
) :
    BasePresenter<RoadStatusPresenter.RoadView>() {

    private val compositeDisposable = CompositeDisposable()

    fun getRoadStatus(roadName: String) {
        view?.showLoading()
        compositeDisposable.add(
            roadStatusRepo.execute(roadName)
                .subscribe({
                    view?.showRoadStatus(it)
                },
                    {
                        if (it is HttpException) {
                            when (val responseCode = it.code()) {
                                404 -> view?.showIdNotRecognizedError(roadName)
                                429 -> view?.showAuthenticationError()
                                else -> view?.showError("HTTP EXCEPTION $responseCode")
                            }
                        } else {
                            view?.showError(it.message!!)
                        }
                    }
                ))

    }

    interface RoadView : View {
        fun showLoading()
        fun showRoadStatus(roadModel: List<RoadModel>)
        fun showError(message: String)
        fun showIdNotRecognizedError(roadName: String)
        fun showAuthenticationError()
    }
}
