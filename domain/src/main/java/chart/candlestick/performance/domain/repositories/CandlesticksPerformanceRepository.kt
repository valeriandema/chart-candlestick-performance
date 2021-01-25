package chart.candlestick.performance.domain.repositories

import chart.candlestick.performance.domain.entities.MonthlyResponse
import chart.candlestick.performance.domain.entities.WeeklyResponse
import chart.candlestick.performance.domain.utils.Result


interface CandlesticksPerformanceRepository {
    fun getWeeklyResponse(isRemote: Boolean): Result<WeeklyResponse>
    fun getMonthlyResponse(isRemote: Boolean): Result<MonthlyResponse>
}
