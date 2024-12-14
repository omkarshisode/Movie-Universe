package com.example.movieuniverse.network

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.SocketTimeoutException

object RetrofitService {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_VALUE = "154ad8f9017ced85e1b45f006f50d4a0"
    private const val API_KEY = "api_key"
    private const val AUTHORIZATION_KEY = "Authorization"
    private const val ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxNTRhZDhmOTAxN2NlZDg1ZTFiNDVmMDA2ZjUwZDRhMCIsIm5iZiI6MTczMjI3OTA4Ni4zOTY0MDgzLCJzdWIiOiI2NzQwNWJjODAxNWQyZTBmNzAyZGZlYjgiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.yR0i-ZOZlJ_wUQN6HUSwVYj4phQGL9ZnXqDl3Up6Q8w"

    private var retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private var httpClient = OkHttpClient.Builder()
    private var loggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY).setLevel(HttpLoggingInterceptor.Level.HEADERS)

    // Generic method to create a retrofit service
    fun <T> createService(serviceClass: Class<T>): T {
        // Clear previous interceptors and add new ones
        httpClient.interceptors().clear()

        // Adding logging interceptor
        if (!httpClient.interceptors().contains(loggingInterceptor)) {
            httpClient.addInterceptor(loggingInterceptor)
        }
        // Adding authorization headers
        httpClient.addInterceptor { chain ->
            val requestBuilder = chain.request()
                .newBuilder()
                .addHeader(API_KEY, API_VALUE)
                .addHeader(AUTHORIZATION_KEY, "Bearer $ACCESS_TOKEN")
                .build()
            return@addInterceptor chain.proceed(requestBuilder)
        }

        // Set the modified client to retrofit instance
        retrofit.client(httpClient.build())

        // Handle exceptions during retrofit request
        return try {
            // Try to create the service
            retrofit.build().create(serviceClass)
        } catch (e: SocketTimeoutException) {
            // Handle socket timeout exception (e.g., request took too long)
            Log.e("RetrofitService", "Request Timeout: ${e.message}")
            // Optionally, show a message to the user
            throw Exception("Request Timeout, please check your internet connection.")  // Throw a custom exception
        } catch (e: IOException) {
            // Handle network IO exception (e.g., no connection or server not reachable)
            Log.e("RetrofitService", "Network Error: ${e.message}")
            // Optionally, show a message to the user
            throw Exception("Network Error, please check your internet connection.")  // Throw a custom exception
        } catch (e: Exception) {
            // Handle general errors, e.g., unexpected issues
            Log.e("RetrofitService", "Unexpected Error: ${e.message}")
            throw Exception("An unexpected error occurred.")  // Throw a custom exception
        }
    }

}