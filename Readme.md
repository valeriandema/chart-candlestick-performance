# Introduction
This is a sample app that computes candlesticks performance based on mocked values 

# MVVM, Clean Architecture, Coroutines, Room, Retrofit, Koin, Kotlin

The project consists of 3 main modules

* __Presentation__: Layer with the Android Framework, the MVVM pattern and the DI module. Depends on domain to access the use cases and on di, to inject dependencies.
* __Domain__: Layer with the business logic. Contains the use cases, in charge of calling the correct repository or data member.
* __Data__: Layer with the responsibility of selecting the proper data source for the domain layer. It contains the implementations of  the repositories declared in the domain layer.

![Screenshot](clean_architecture.png)

# How it works

The project simulate a network query by adding interceptor into **OkHttpClient** which returns a parced local json file from **assets** folder

```
override fun intercept(chain: Interceptor.Chain): Response {
        val path = when {
            chain.request().url().pathSegments().contains("weeklyResponse") -> {
                "week.json"
            }
            chain.request().url().pathSegments().contains("monthlyResponse") -> {
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
            .body(ResponseBody.create(MediaType.parse("application/json"), response))
            .addHeader("content-type", "application/json")
            .build()
} 
```


Right afer the network query finishes succesfully it's response is saved into local database by **Room**

```
@Insert(onConflict = OnConflictStrategy.REPLACE)
fun addAll(weekly: WeeklyResponse): Long
 ```

Remote and local repositories are managed by **Koin** which are used in **ViewModel**

```
val repositoriesModule = module {
    single { CandlesticksRemoteService(androidContext()) }
    single { Room.databaseBuilder(androidContext(), CandlesticksDatabase::class.java, "candlesticks").build() }
    single<CandlesticksPerformanceRepository> { CandlesticksRepositoryImpl(get(), get()) }
}
```

And **ViewModel** with injected repositories is added into **MainActivity**, where observes the states from **data** module  

```
class MainActivity : AppCompatActivity() {
    private val viewModel by viewModel<CandlesticksViewModel>()
```

The work with repositories is carried out inside **viewModelScope** 

Any coroutine launched in this scope is automatically canceled if the **ViewModel is cleared**
For example, if you are computing some data for a layout, you should scope the work to the ViewModel so that if the ViewModel is cleared, the work is canceled automatically to avoid consuming resources. In current case it is network/db request the IO dispatcher is used for this purpose

```
fun getWeeklyResponse(isRemote: Boolean) = viewModelScope.launch {
        when (val result = withContext(Dispatchers.IO) { getWeeklyResponseUseCase(isRemote) }) {
            is Result.Failure -> {
                mutableMainState.value = Data(responseType = Status.ERROR, error = result.exception)
            }
            is Result.Success<WeeklyResponse> -> {
                mutableMainState.value = Data(responseType = Status.SUCCESSFUL, data = result.data.content)
           }
        }
    }
```

Business logic for calculating candlestick's performance. The complexity of an algorithm **log(𝑛)2**
```
fun getCalculatePerformance(content: Content) = viewModelScope.launch {
        when (val result = withContext(Dispatchers.Default) {
            val hashMap: SortedMap<Long, MutableList<Double>> = sortedMapOf()
            content.quoteSymbols.forEach { quoteSymbol ->
                quoteSymbol.lows.forEachIndexed { index, d ->
                    if (hashMap.containsKey(quoteSymbol.timestamps[index])) {
                        hashMap[quoteSymbol.timestamps[index]]?.add(d)
                    } else {
                        hashMap[quoteSymbol.timestamps[index]] = mutableListOf(d)
                    }
                }
            }
            hashMap.forEach {
                val firstVal = it.value.first()
                it.value.forEachIndexed { index, d ->
                    it.value[index] = d - firstVal
                }
            }
            hashMap
        }) { result ->
            mutablePerformanceState.value = result
        }
    }
    ```
