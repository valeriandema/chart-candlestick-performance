# MVVM, Clean Architecture, Coroutines, Room, Retrofit, Koin, Kotlin

The project consists of 3 main modules

* __Presentation__: Layer with the Android Framework, the MVVM pattern and the DI module. Depends on domain to access the use cases and on di, to inject dependencies.
* __Domain__: Layer with the business logic. Contains the use cases, in charge of calling the correct repository or data member.
* __Data__: Layer with the responsibility of selecting the proper data source for the domain layer. It contains the implementations of  the repositories declared in the domain layer.

# How it works

The project simulate a network query by adding interceptor into **OkHttpClient** which will return parced local json file from **assets** folder

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
