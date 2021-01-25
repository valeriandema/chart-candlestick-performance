package chart.candlestick.performance

import android.content.Context
import java.io.IOException

fun Context.getJsonFromAssets(fileName: String): String {
    val jsonString: String
    try {
        jsonString = this.assets.open(fileName).bufferedReader().use { it.readText() }
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        return ioException.localizedMessage ?: "Error"
    }
    return jsonString
}