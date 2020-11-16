package com.example.tfl_gaffy

import com.example.tfl_gaffy.data.model.RoadModel
import com.example.tfl_gaffy.domain.RoadStatusRepo
import com.example.tfl_gaffy.presentation.roadstatus.RoadStatusPresenter
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class RoadStatusPresenterTest {

    @RelaxedMockK
    lateinit var roadStatusRepo: RoadStatusRepo

    @RelaxedMockK
    lateinit var view: RoadStatusPresenter.RoadView

    private val sut by lazy { RoadStatusPresenter(roadStatusRepo) }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        sut.onViewAttached(view)
    }

    @Test
    fun `check presenter is being attached`() {
        assert(sut.view != null)
    }


    @Test
    fun `get road status success`() {
        val roadModelList = listOf(
            RoadModel(
                "Tfl.Api.Presentation.Entities.RoadCorridor, Tfl.Api.Presentation.Entities",
                "a1",
                "A1",
                "Good",
                "No Exceptional Delays",
                "[[-0.25616,51.5319],[-0.10234,51.6562]]",
                "[[-0.25616,51.5319],[-0.25616,51.6562],[-0.10234,51.6562],[-0.10234,51.5319],[-0.25616,51.5319]]",
                "/Road/a1"
            )
        )
        every { roadStatusRepo.execute("a1") } returns Single.just(roadModelList)
        sut.getRoadStatus("a1")

        verify { view.showLoading() }
        verify { view.showRoadStatus(roadModelList) }
        verify(exactly = 0) { view.showAuthenticationError() }
        verify(exactly = 0) { view.showIdNotRecognizedError("a") }
        verify(exactly = 0) { view.showError(any()) }
    }

    @Test
    fun `get road status error 404`() {
        val response =
            Response.error<String>(404, "dummy".toResponseBody("application/json".toMediaType()))
        every { roadStatusRepo.execute("a1") } returns Single.error(HttpException(response))
        sut.getRoadStatus("a1")

        verify { view.showLoading() }
        verify { view.showIdNotRecognizedError("a1") }
        verify(exactly = 0) { view.showAuthenticationError() }
        verify(exactly = 0) { view.showError(any()) }
        verify(exactly = 0) { view.showRoadStatus(any()) }
    }


    @Test
    fun `get road status error 429`() {
        val response =
            Response.error<String>(429, "dummy".toResponseBody("application/json".toMediaType()))
        every { roadStatusRepo.execute("a1") } returns Single.error(HttpException(response))
        sut.getRoadStatus("a1")

        verify { view.showLoading() }
        verify { view.showAuthenticationError() }
        verify(exactly = 0) { view.showIdNotRecognizedError("a1") }
        verify(exactly = 0) { view.showError(any()) }
        verify(exactly = 0) { view.showRoadStatus(any()) }
    }


    @Test
    fun `get road status error any other`() {
        val response =
            Response.error<String>(500, "dummy".toResponseBody("application/json".toMediaType()))
        every { roadStatusRepo.execute("a") } returns Single.error(HttpException(response))
        sut.getRoadStatus("a")

        verify { view.showLoading() }
        verify(exactly = 0) { view.showAuthenticationError() }
        verify(exactly = 0) { view.showIdNotRecognizedError("a") }
        verify { view.showError("HTTP EXCEPTION 500") }
        verify(exactly = 0) { view.showRoadStatus(any()) }
    }


    @Test
    fun `get road status error io exception`() {

        every { roadStatusRepo.execute("a") } returns Single.error(IOException("error_message"))
        sut.getRoadStatus("a")

        verify { view.showLoading() }
        verify(exactly = 0) { view.showAuthenticationError() }
        verify(exactly = 0) { view.showIdNotRecognizedError("a") }
        verify { view.showError("error_message") }
        verify(exactly = 0) { view.showRoadStatus(any()) }
    }

    @Test
    fun `check presenter is being detached`() {
        sut.onViewDetached()
        assert(sut.view == null)
    }
}