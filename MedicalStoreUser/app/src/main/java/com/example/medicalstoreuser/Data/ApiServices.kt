package com.example.medicalstoreuser.Data

import com.example.medicalstoreuser.Data.Response.GetSpecificProductResponse
import com.example.medicalstoreuser.Data.Response.GetSpecificUserResponse
import com.example.medicalstoreuser.Data.Response.CreateUserResponse
import com.example.medicalstoreuser.Data.Response.GetAllProductsResponse
import com.example.medicalstoreuser.Data.Response.GetAllUserResponse
import com.example.medicalstoreuser.Data.Response.LoginUserResponse
import com.example.medicalstoreuser.Data.Response.UpdateUserResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
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
    ): Response<CreateUserResponse>

    @FormUrlEncoded
    @POST("/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Response<LoginUserResponse>


    @GET("/getAllUsers")
    suspend fun getAllUsers(): Response<GetAllUserResponse>

    @GET("/getAllProducts")
    suspend fun getAllProducts(): Response<GetAllProductsResponse>

    @FormUrlEncoded
    @POST("/getSpecificProduct")
    suspend fun getSpecificProduct(
        @Field("productID") product_id: String
    ): Response<GetSpecificProductResponse>

    @FormUrlEncoded
    @POST("/getSpecificUser")
    suspend fun getSpecificUser(
        @Field("userID") user_id: String
    ): Response<GetSpecificUserResponse>


    @FormUrlEncoded
    @PATCH("/updateUserInfo")
    suspend fun updateUserInfo(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("phoneNumber") phone_number: String,
        @Field("password") password: String,
        @Field("address") address: String,
        @Field("pinCode") pinCode: String,

    ): Response<UpdateUserResponse>



}