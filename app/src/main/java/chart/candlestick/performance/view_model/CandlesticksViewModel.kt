package chart.candlestick.performance.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chart.candlestick.performance.domain.entities.Content
import chart.candlestick.performance.domain.entities.MonthlyResponse
import chart.candlestick.performance.domain.entities.WeeklyResponse
import chart.candlestick.performance.domain.usecases.GetMonthlyResponseUseCase
import chart.candlestick.performance.domain.usecases.GetWeeklyResponseUseCase
import chart.candlestick.performance.domain.utils.Result
import chart.candlestick.performance.utils.Data
import chart.candlestick.performance.utils.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CandlesticksViewModel(private val getWeeklyResponseUseCase: GetWeeklyResponseUseCase,
                            private val getMonthlyResponseUseCase: GetMonthlyResponseUseCase
) : ViewModel() {

    private var mutableMainState: MutableLiveData<Data<Content?>> = MutableLiveData()
    val mainState: LiveData<Data<Content?>> = mutableMainState

    fun getWeeklyResponse(isRemote: Boolean) = viewModelScope.launch {
        when (val result = withContext(Dispatchers.IO) { getWeeklyResponseUseCase(isRemote) }) {
            is Result.Failure -> {
                mutableMainState.value = Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success<WeeklyResponse> -> {
                mutableMainState.value = Data(responseType = Status.SUCCESSFUL, data = result.data.content)
            }
        }
    }

    fun getMonthlyResponse(isRemote: Boolean) = viewModelScope.launch {
        when (val result = withContext(Dispatchers.IO) { getMonthlyResponseUseCase(isRemote) }) {
            is Result.Failure -> {
                mutableMainState.value = Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success<MonthlyResponse> -> {
                mutableMainState.value = Data(responseType = Status.SUCCESSFUL, data = result.data.content)
            }
        }
    }
}


