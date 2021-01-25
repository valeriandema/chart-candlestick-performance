package chart.candlestick.performance.service

import android.content.Context
import chart.candlestick.performance.utils.getJsonFromAssets
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class MockClient(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val path = when {
            chain.request().url.pathSegments.contains("weeklyResponse") -> {
                "week.json"
            }
            chain.request().url.pathSegments.contains("monthlyResponse") -> {
                "month.json"
            }
            else -> {
                "week.json"
            }
        }
        val response = readJsonFileFromAssets(path)
        return Response.Builder()
            .code(200)
            .message(response)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(response.toResponseBody("application/json".toMediaTypeOrNull()))
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun readJsonFileFromAssets(path: String): String {
        return context.getJsonFromAssets(path)
    }
}