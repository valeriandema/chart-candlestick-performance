package chart.candlestick.performance.utils

data class Data<T>(var responseType: Status, var data: Any? = null, var error: Exception? = null)

enum class Status { SUCCESSFUL, ERROR, LOADING }
