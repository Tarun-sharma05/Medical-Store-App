package com.example.medicalstore_admin.Network

import com.example.medicalstore_admin.Network.Response.AddProductResponse
import com.example.medicalstore_admin.Network.Response.GetAllUserResponse
import com.example.medicalstore_admin.Network.Response.UpdateUserInfoResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import java.sql.Date

interface ApiService {

    @GET("getAllUsers")
    suspend fun getAllUsers(): Response<GetAllUserResponse>

    @FormUrlEncoded
    @PATCH("updateUserInfo")
    suspend fun updateUserInfo(
        @Field("user_id") user_id: String,
        @Field("isApproved") isApproved: Int,
    ): Response<UpdateUserInfoResponse>

    @FormUrlEncoded
    @POST("addProduct")
    suspend fun addProduct(
        @Field("product_name") product_name: String,
        @Field("product_price") product_price: Float,
        @Field("product_stock") product_stock: Int,
        @Field("product_category") product_category: String,
        @Field("product_expiry_date")product_expiry_date: String
    ): Response<AddProductResponse>


}