package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.MessageServerApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object ApiServiceFactory {
    private val okHttpClient = OkHttpClient.Builder().build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://messageServer.chondosha.com")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    fun createMessageServerApi(): MessageServerApi {
        return retrofit.create(MessageServerApi::class.java)
    }
}