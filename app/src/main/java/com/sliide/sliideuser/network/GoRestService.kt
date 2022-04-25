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
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
interface GoRestService {
    companion object {
        const val RESPONSE_CODE_OK_NO_CONTENT = 204
    }

    @GET("users")
    fun getUsersAsync(): Deferred<List<NetworkUser>>

    @DELETE("users/{userId}")
    fun deleteUserAsync(@Path("userId") userId: Long): Deferred<Response<ResponseBody>>

    @POST("users")
    fun addUserAsync(@Body networkUser: NetworkUser): Deferred<Response<ResponseBody>>
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
 * The token itself is held in the gradle.properties (Global Properties) file. Please create a new variable there with
 * your own token (in the form of token="b74c25...") in order to properly use this application
 */
object Network {
    // Build a client that adds the necessary token for all the calls
    private val tokenClient = OkHttpClient.Builder()
        .addInterceptor(Interceptor { chain ->
            val requestBuilder = chain.request().newBuilder()
            requestBuilder.header("Authorization", "Bearer ${BuildConfig.token}") //use your own token as per this method's documentation
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