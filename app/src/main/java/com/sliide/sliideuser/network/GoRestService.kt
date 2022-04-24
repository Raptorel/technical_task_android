package com.sliide.sliideuser.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.localebro.okhttpprofiler.OkHttpProfilerInterceptor
import com.sliide.sliideuser.BuildConfig
import com.sliide.sliideuser.domain.User
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
interface GoRestService {
    @GET("users")
    fun getUsers(): Deferred<List<NetworkUser>>
}

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * Main entry point for network access. Call like `Network.goRestService.getUsers() or simply use the repository's service`
 */
object Network {
    // Build a client that adds the necessary token for all the calls
    private val tokenClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("token", "Bearer ${BuildConfig.token}")
            chain.proceed(requestBuilder.build())
        })
        .addInterceptor(OkHttpProfilerInterceptor())
        .build()

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://gorest.co.in/public/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(tokenClient)
        .build()

    val goRestService = retrofit.create(GoRestService::class.java)
}