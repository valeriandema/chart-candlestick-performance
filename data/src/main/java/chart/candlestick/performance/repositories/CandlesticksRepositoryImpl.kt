package chart.candlestick.performance.repositories

import chart.candlestick.performance.database.CandlesticksDatabase
import chart.candlestick.performance.domain.entities.*
import chart.candlestick.performance.domain.utils.Result
import chart.candlestick.performance.domain.repositories.CandlesticksPerformanceRepository
import chart.candlestick.performance.service.CandlesticksRemoteService
import java.lang.Exception

class CandlesticksRepositoryImpl(
    private val candlesticksRemoteService: CandlesticksRemoteService,
    private val candlesticksDatabase: CandlesticksDatabase
) : CandlesticksPerformanceRepository {
    override fun getWeeklyResponse(isRemote: Boolean): Result<WeeklyResponse> = if (isRemote) {
        val response = candlesticksRemoteService.getWeeklyResponse()
        if (response is Result.Success) {
            insertWeeklyResponse(response.data)
        }
        response
    } else {
        try {
            Result.Success(candlesticksDatabase.candlesticksDao.findAllCandlesticks())
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    override fun getMonthlyResponse(isRemote: Boolean): Result<MonthlyResponse> = if (isRemote) {
        val response = candlesticksRemoteService.getMonthlyResponse()
        if (response is Result.Success) {
            insertMonthlyResponse(response.data)
        }
        response
    } else {
        try {
            Result.Success(candlesticksDatabase.candlesticksDao.findAllCandlesticksMonthly())
        } catch (e: Exception) {
            Result.Failure(e)
        }
    }

    private fun insertWeeklyResponse(weekly: WeeklyResponse) {
        candlesticksDatabase.candlesticksDao.addAll(weekly)
    }

    private fun insertMonthlyResponse(monthly: MonthlyResponse) {
        candlesticksDatabase.candlesticksDao.addAll(monthly)
    }
}
