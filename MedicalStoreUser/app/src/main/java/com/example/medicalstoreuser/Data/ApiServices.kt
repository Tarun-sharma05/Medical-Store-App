package com.example.medicalstoreuser.Data

import com.example.medicalstoreuser.Data.Response.LoginUserResponse
import com.example.medicalstoreuser.Data.Response.createUserResponse
import com.example.medicalstoreuser.Data.Response.getAllProductResponce
import com.example.medicalstoreuser.Data.Response.getAllUserResponce
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface apiServices {
    @FormUrlEncoded
    @POST("/signUp")
    suspend fun signUpUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phoneNumber") phoneNumber: String,
        @Field("address") address: String,
        @Field("pinCode") pinCode: String,
    ): Response<createUserResponse>

    @FormUrlEncoded
    @POST("/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginUserResponse>


    @GET("/getAllUsers")
    suspend fun getAllUsers(): Response<getAllUserResponce>

    @GET("/getAllProducts")
    suspend fun getAllProducts(): Response<getAllProductResponce>
}