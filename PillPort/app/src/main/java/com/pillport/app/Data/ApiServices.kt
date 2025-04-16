package com.pillport.app.Data

import com.pillport.app.Data.Response.CreateUserResponse
import com.pillport.app.Data.Response.GetAllProductsResponse
import com.pillport.app.Data.Response.GetAllUserResponse
import com.pillport.app.Data.Response.UpdateUserResponse
import com.pillport.app.Data.Response.AddOrderResponse
import com.pillport.app.Data.Response.GetSpecificProductResponse
import com.pillport.app.Data.Response.GetSpecificUserResponse
import com.pillport.app.Data.Response.LoginUserResponse
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

    @FormUrlEncoded
    @POST("addOrder")
    suspend fun addOrder(
        @Field("user_id") user_id: String,
        @Field("name") name: String,
        @Field("product_name") product_name: String,
        @Field("quantity") quantity: Int,
        @Field("product_id") product_id: String
    ): Response<AddOrderResponse>



}