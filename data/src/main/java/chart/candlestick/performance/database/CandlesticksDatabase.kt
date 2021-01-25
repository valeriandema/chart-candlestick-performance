package chart.candlestick.performance.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import chart.candlestick.performance.api.CandlesticksDao
import chart.candlestick.performance.domain.entities.*
import chart.candlestick.performance.domain.utils.CandlestickConverters
import chart.candlestick.performance.domain.utils.DoubleConverters
import chart.candlestick.performance.domain.utils.LongConverters

@Database(
    entities = [
        WeeklyResponse::class,
        MonthlyResponse::class,
        QuoteSymbol::class],
    version = 1, exportSchema = false
)
@TypeConverters(CandlestickConverters::class, LongConverters::class, DoubleConverters::class)
abstract class CandlesticksDatabase : RoomDatabase() {
    abstract val candlesticksDao: CandlesticksDao
}
