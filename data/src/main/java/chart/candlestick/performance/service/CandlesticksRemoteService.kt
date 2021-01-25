package chart.candlestick.performance.service

import android.content.Context
import chart.candlestick.performance.domain.entities.MonthlyResponse
import chart.candlestick.performance.domain.entities.WeeklyResponse
import chart.candlestick.performance.service.api.CandlesticksApi
import chart.candlestick.performance.domain.utils.Result

class CandlesticksRemoteService(androidContext: Context) {

    private val api = CandlesticksRequestGenerator(androidContext)
    private val candlesticksApi: CandlesticksApi
    init {
        candlesticksApi = api.createService(CandlesticksApi::class.java)
    }

    fun getWeeklyResponse(): Result<WeeklyResponse> {
        val callResponse = candlesticksApi.getWeeklyResponse()
        val response = callResponse.execute()
        if (response.isSuccessful && response.body() != null) {
            return Result.Success(response.body()!!)
        }
        return Result.Failure(Exception(response.message()))
    }

    fun getMonthlyResponse(): Result<MonthlyResponse> {
        val callResponse = candlesticksApi.getMonthlyResponse()
        val response = callResponse.execute()
        if (response.isSuccessful && response.body() != null) {
            return Result.Success(response.body()!!)
        }
        return Result.Failure(Exception(response.message()))
    }
}
