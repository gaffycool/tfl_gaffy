package com.example.tfl_gaffy.presentation.roadstatus

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.tfl_gaffy.R
import com.example.tfl_gaffy.common.App
import com.example.tfl_gaffy.common.dagger.activity.ActivityComponent
import com.example.tfl_gaffy.common.dagger.activity.ActivityComponentHolder
import com.example.tfl_gaffy.common.dagger.activity.DaggerActivityComponent
import com.example.tfl_gaffy.data.model.RoadModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class RoadStatusActivity : AppCompatActivity(), RoadStatusPresenter.RoadView,

    ActivityComponentHolder {
    override fun showIdNotRecognizedError(roadName: String) {
        showError(getString(R.string.api_exception) + " " + roadName)
    }

    override fun showAuthenticationError() {
        showError(getString(R.string.key_error))
    }

    override val component: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
            .sessionComponent((applicationContext as App).currentSession())
            .build()
    }

    @Inject
    lateinit var roadStatusPresenter: RoadStatusPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)
        roadStatusPresenter.onViewAttached(this)
        btnCheckStatus.setOnClickListener {
            roadStatusPresenter.getRoadStatus(etRoadName.text.toString())
        }
    }

    override fun showLoading() {
        prgTFL.visibility = View.VISIBLE
        etRoadName.visibility = View.GONE
        tvDisplayName.visibility = View.GONE
        tvDisplaySeverity.visibility = View.GONE
        tvSeverityDescr.visibility = View.GONE
        btnCheckStatus.visibility = View.GONE

    }

    override fun showRoadStatus(roadModel: List<RoadModel>) {
        prgTFL.visibility = View.GONE
        etRoadName.visibility = View.VISIBLE
        tvDisplayName.visibility = View.VISIBLE
        tvDisplaySeverity.visibility = View.VISIBLE
        tvSeverityDescr.visibility = View.VISIBLE
        btnCheckStatus.visibility = View.VISIBLE
        tvDisplayName.text =
            String.format(getString(R.string.road) + " ${roadModel[0].displayName}")
        tvDisplaySeverity.text =
            String.format(getString(R.string.severity) + " ${roadModel[0].statusSeverity}")
        tvSeverityDescr.text =
            String.format(getString(R.string.severity_description) +
                    " ${roadModel[0].statusSeverityDescription}")
    }

    override fun showError(message: String) {
        prgTFL.visibility = View.GONE
        etRoadName.visibility = View.VISIBLE
        tvDisplayName.visibility = View.VISIBLE
        tvDisplayName.text = message
        tvSeverityDescr.visibility = View.GONE
        tvDisplaySeverity.visibility = View.GONE
    }
}
