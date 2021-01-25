package chart.candlestick.performance.domain.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object DoubleConverters {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): ArrayList<Double> {
        val listType: Type = object : TypeToken<ArrayList<Double>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayList(list: ArrayList<Double>): String {
        return Gson().toJson(list)
    }
}