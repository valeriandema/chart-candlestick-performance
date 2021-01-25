package chart.candlestick.performance.domain.utils

import androidx.room.TypeConverter
import chart.candlestick.performance.domain.entities.QuoteSymbol
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object CandlestickConverters {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): ArrayList<QuoteSymbol> {
        val listType: Type = object : TypeToken<ArrayList<QuoteSymbol>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayList(list: ArrayList<QuoteSymbol>): String {
        return Gson().toJson(list)
    }
}