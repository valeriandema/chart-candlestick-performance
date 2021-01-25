package chart.candlestick.performance.domain.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import chart.candlestick.performance.domain.utils.CandlestickConverters
import chart.candlestick.performance.domain.utils.DoubleConverters
import chart.candlestick.performance.domain.utils.LongConverters
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "WeeklyResponse")
@Parcelize
data class WeeklyResponse (
    @PrimaryKey(autoGenerate = true) var id: Long,
    @Embedded
    val content: Content,
    val status: String
): Parcelable

@Entity(tableName = "MonthlyResponse")
@Parcelize
data class MonthlyResponse (
    @PrimaryKey(autoGenerate = true) var id: Long,
    @Embedded
    var content: Content?,
    val status: String
): Parcelable

@Parcelize
data class Content (
    @TypeConverters(CandlestickConverters::class)
    var quoteSymbols: ArrayList<QuoteSymbol>
): Parcelable

@Entity(tableName = "QuoteSymbol")
@Parcelize
data class QuoteSymbol (
    @PrimaryKey(autoGenerate = true) var id: Long,
    var symbol: String,
    @TypeConverters(LongConverters::class)
    var timestamps: ArrayList<Long>,
    @TypeConverters(DoubleConverters::class)
    var opens: ArrayList<Double>,
    @TypeConverters(DoubleConverters::class)
    var closures: ArrayList<Double>,
    @TypeConverters(DoubleConverters::class)
    var highs: ArrayList<Double>,
    @TypeConverters(DoubleConverters::class)
    var lows: ArrayList<Double>,
    @TypeConverters(LongConverters::class)
    var volumes: ArrayList<Long>
): Parcelable