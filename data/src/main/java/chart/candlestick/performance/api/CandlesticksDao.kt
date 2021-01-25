package chart.candlestick.performance.api

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import chart.candlestick.performance.domain.entities.MonthlyResponse
import chart.candlestick.performance.domain.entities.WeeklyResponse

@Dao
interface CandlesticksDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(weekly: WeeklyResponse): Long

    @Query("SELECT * FROM WeeklyResponse")
    fun findAllCandlesticks(): WeeklyResponse

    @Query("SELECT * FROM MonthlyResponse")
    fun findAllCandlesticksMonthly(): MonthlyResponse

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(monthly: MonthlyResponse): Long
}