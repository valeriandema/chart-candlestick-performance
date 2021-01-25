package chart.candlestick.performance

import android.app.Application
import chart.candlestick.performance.di.useCasesModule
import chart.candlestick.performance.di.viewModelsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CandlestickPerformanceApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@CandlestickPerformanceApp)
            modules(listOf(repositoriesModule, viewModelsModule, useCasesModule))
        }
    }
}
