package chart.candlestick.performance.di

import chart.candlestick.performance.domain.usecases.GetMonthlyResponseUseCase
import chart.candlestick.performance.domain.usecases.GetWeeklyResponseUseCase
import chart.candlestick.performance.view_model.CandlesticksViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { CandlesticksViewModel(get(),get()) }
}

val useCasesModule = module {
    single { GetWeeklyResponseUseCase() }
    single { GetMonthlyResponseUseCase() }
}


