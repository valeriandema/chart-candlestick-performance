package chart.candlestick.performance.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import chart.candlestick.performance.R
import chart.candlestick.performance.domain.entities.Content
import chart.candlestick.performance.utils.Data
import chart.candlestick.performance.utils.Status
import chart.candlestick.performance.view_model.CandlesticksViewModel
import com.google.android.material.button.MaterialButton
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<CandlesticksViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.mainState.observe(::getLifecycle, ::updateUI)
        viewModel.performanceState.observe(::getLifecycle, ::showPerformance)

        findViewById<MaterialButton>(R.id.btnWeeklyData).setOnClickListener {
            viewModel.getWeeklyResponse(true)
        }
        findViewById<MaterialButton>(R.id.btnWeeklyDataLocal).setOnClickListener {
            viewModel.getWeeklyResponse(false)
        }

        findViewById<MaterialButton>(R.id.btnMonthlyData).setOnClickListener {
            viewModel.getMonthlyResponse(true)
        }
        findViewById<MaterialButton>(R.id.btnMonthlyDataLocal).setOnClickListener {
            viewModel.getMonthlyResponse(false)
        }

    }

    private fun updateUI(result: Data<Content?>) {

        when (result.responseType) {
            Status.ERROR -> {
                //Show error message
                Log.e("ERROR", "${result.error?.localizedMessage}")
            }
            Status.LOADING -> {
                //Update loader's state
            }
            Status.SUCCESSFUL -> {
                result.data?.let {
                    with(it as Content) {
                        viewModel.getCalculatePerformance(this)
                    }
                }

            }
        }
    }

    private fun showPerformance(performance: SortedMap<Long, MutableList<Double>>) {
        Log.e("performance", "$performance")
    }
}