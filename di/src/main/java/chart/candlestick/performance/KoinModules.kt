package chart.candlestick.performance

import androidx.room.Room
import chart.candlestick.performance.database.CandlesticksDatabase
import chart.candlestick.performance.domain.repositories.CandlesticksPerformanceRepository
import chart.candlestick.performance.repositories.CandlesticksRepositoryImpl
import chart.candlestick.performance.service.CandlesticksRemoteService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoriesModule = module {
    single { CandlesticksRemoteService(androidContext()) }
    single { Room.databaseBuilder(androidContext(), CandlesticksDatabase::class.java, "candlesticks").build() }
    single<CandlesticksPerformanceRepository> { CandlesticksRepositoryImpl(get(), get()) }
}
