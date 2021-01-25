package chart.candlestick.performance.service.api

import chart.candlestick.performance.domain.entities.MonthlyResponse
import chart.candlestick.performance.domain.entities.WeeklyResponse
import retrofit2.Call
import retrofit2.http.GET

interface CandlesticksApi {
    @GET("/weeklyResponse/")
    fun getWeeklyResponse(): Call<WeeklyResponse>

    @GET("/monthlyResponse/")
    fun getMonthlyResponse(): Call<MonthlyResponse>
}
