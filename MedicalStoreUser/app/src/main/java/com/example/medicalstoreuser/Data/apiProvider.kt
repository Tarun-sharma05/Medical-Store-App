package com.example.medicalstoreuser.Data

import com.google.gson.GsonBuilder
import com.google.gson.stream.JsonReader
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object apiProvider {

    fun providerApi() = Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpClient.Builder().build())
        .addConverterFactory(
        GsonConverterFactory.create()
            ).build().create(apiServices::class.java)

}