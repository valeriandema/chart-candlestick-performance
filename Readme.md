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

Remote and local repositories are managed by **Koin** which are used in ViewModel

```
val repositoriesModule = module {
    single { CandlesticksRemoteService(androidContext()) }
    single { Room.databaseBuilder(androidContext(), CandlesticksDatabase::class.java, "candlesticks").build() }
    single<CandlesticksPerformanceRepository> { CandlesticksRepositoryImpl(get(), get()) }
}
```
