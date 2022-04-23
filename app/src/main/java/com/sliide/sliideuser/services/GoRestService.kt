package com.sliide.sliideuser.services

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by Robert Ruxandrescu on 4/23/22.
 */
interface GoRestService {
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
    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://gorest.co.in/public/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    val goRestService = retrofit.create(GoRestService::class.java)
}