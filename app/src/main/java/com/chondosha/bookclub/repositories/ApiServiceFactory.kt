package com.chondosha.bookclub.repositories

import com.chondosha.bookclub.api.MessageServerApi
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object ApiServiceFactory {
    private val okHttpClient = OkHttpClient.Builder().build()
    
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .add(UUIDAdapter())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://messageServer.chondosha.com")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(okHttpClient)
        .build()

    fun createMessageServerApi(): MessageServerApi {
        return retrofit.create(MessageServerApi::class.java)
    }
}

class UUIDAdapter {
    @ToJson
    fun toJson(uuid: UUID): String {
        return uuid.toString()
    }

    @FromJson
    fun fromJson(json: String): UUID {
        return UUID.fromString(json)
    }
}