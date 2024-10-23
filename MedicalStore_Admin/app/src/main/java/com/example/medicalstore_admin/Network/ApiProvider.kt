package com.example.medicalstore_admin.Network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiProvider {

      fun providerApi() = Retrofit.Builder().baseUrl(BASE_URL).client(OkHttpClient.Builder().build())
          .addConverterFactory(
              GsonConverterFactory.create()
          ).build().create(ApiService::class.java)
}