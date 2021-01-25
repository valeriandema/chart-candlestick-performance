package chart.candlestick.performance.domain.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object LongConverters {

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): ArrayList<Long> {
        val listType: Type = object : TypeToken<ArrayList<Long>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromArrayList(list: ArrayList<Long>): String {
        return Gson().toJson(list)
    }
}