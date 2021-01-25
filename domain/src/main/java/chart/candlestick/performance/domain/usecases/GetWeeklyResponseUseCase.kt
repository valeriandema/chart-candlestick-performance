package chart.candlestick.performance.domain.usecases

import chart.candlestick.performance.domain.repositories.CandlesticksPerformanceRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class GetWeeklyResponseUseCase: KoinComponent {
    private val candlesticksRepository: CandlesticksPerformanceRepository by inject()
    operator fun invoke(isRemote: Boolean) = candlesticksRepository.getWeeklyResponse(isRemote)
}
