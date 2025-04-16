package com.example.medicalstore_admin.Network

import com.example.medicalstore_admin.Network.Response.AddOrderResponse
import com.example.medicalstore_admin.Network.Response.AddProductResponse
import com.example.medicalstore_admin.Network.Response.GetAllOrdersResponse
import com.example.medicalstore_admin.Network.Response.GetAllProductsResponse
import com.example.medicalstore_admin.Network.Response.GetAllSellsHistoryResponse
import com.example.medicalstore_admin.Network.Response.GetAllUserResponse
import com.example.medicalstore_admin.Network.Response.GetSpecificProductResponse
import com.example.medicalstore_admin.Network.Response.UpdateOrderDetailsResponse
import com.example.medicalstore_admin.Network.Response.UpdateProductResponse
import com.example.medicalstore_admin.Network.Response.UpdateUserInfoResponse
import com.example.medicalstore_admin.Network.Response.GetSpecificUserResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

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
        @Field("product_price") product_price: Double,
        @Field("product_stock") product_stock: Int,
        @Field("product_category") product_category: String,
        @Field("product_expiry_date") product_expiry_date: String,
        @Field("product_company") product_company: String,
        @Field("product_description") product_description: String,
        @Field("product_image") product_image: String
    ): Response<AddProductResponse>

    @GET("getAllOrders")
    suspend fun getAllOrders(): Response<GetAllOrdersResponse>

    @GET("getSellHistory")
    suspend fun getAllSellHistory(): Response<GetAllSellsHistoryResponse>

    @GET("getAllProducts")
    suspend fun getAllProducts(): Response<GetAllProductsResponse>

    @FormUrlEncoded
    @PATCH("updateOrder")
    suspend fun updateOrderDetails(
        @Field("orderID") orderID: String,
        @Field("isApproved") isApproved: Int,
    ): Response<UpdateOrderDetailsResponse>

    @FormUrlEncoded
    @POST("getSpecificProduct")
    suspend fun getSpecificProduct(
        @Field("productID") product_id: String
    ): Response<GetSpecificProductResponse>

    @FormUrlEncoded
    @POST("getSpecificUser")
    suspend fun getSpecificUser(
        @Field("userID") user_id: String
    ): Response<GetSpecificUserResponse>


    @FormUrlEncoded
    @PATCH("updateProduct")
    suspend fun updateProductDetails(
        @Field("productID") productID: String,
        @Field("product_name") product_name: String,
        @Field("product_price") product_price: String,
        @Field("product_stock") product_stock: Int,
        @Field("product_category") product_category: String,
        @Field("product_expiry_date") product_expiry_date: String,
        @Field("product_company") product_company: String,
        @Field("product_description") product_description: String,
        @Field("product_image") product_image: String

    ): Response<UpdateProductResponse>

    @FormUrlEncoded
    @PATCH("updateProduct")
    suspend fun updateProductStockDetails(
        @Field("productID") productID: String,
        @Field("product_stock") product_stock: Int,
    ): Response<UpdateProductResponse>


}