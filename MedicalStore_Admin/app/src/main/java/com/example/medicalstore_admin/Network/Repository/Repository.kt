package com.example.medicalstore_admin.Network.Repository

import com.example.medicalstore_admin.Network.ApiProvider
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import com.example.medicalstore_admin.State


class Repository {

    suspend fun getAllUserRepo(): Flow<State<Response<GetAllUserResponse>>> = flow {

        emit(State.Loading)
        try {
            val response = ApiProvider.providerApi().getAllUsers()
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))

        }
    }

    suspend fun approveUserRepo(
        user_id: String,
        isApproved: Int
    ): Flow<State<Response<UpdateUserInfoResponse>>> = flow {
        emit(State.Loading)

        try {
            val response =
                ApiProvider.providerApi().updateUserInfo(user_id = user_id, isApproved = isApproved)
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun addProductRepo(
        product_name: String,
        product_price: Double,
        product_stock: Int,
        product_category: String,
        product_expiry_date: String,
        product_company: String,
        product_description: String,
        product_image: String
    ): Flow<State<Response<AddProductResponse>>> = flow {
        emit(State.Loading)
        try {
            val response = ApiProvider.providerApi().addProduct(
                product_name = product_name,
                product_price = product_price,
                product_stock = product_stock,
                product_category = product_category,
                product_expiry_date = product_expiry_date,
                product_company = product_company,
                product_description = product_description,
                product_image = product_image
            )
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun getOrdersRepo(): Flow<State<Response<GetAllOrdersResponse>>> = flow {
        emit(State.Loading)
        try {
            val response = ApiProvider.providerApi().getAllOrders()
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun getSellHistoryRepo(): Flow<State<Response<GetAllSellsHistoryResponse>>> = flow {
        emit(State.Loading)
        try {
            val response = ApiProvider.providerApi().getAllSellHistory()
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }

    suspend fun approveOrderDetailsRepo(
        orderID: String,
        isApproved: Int
    ): Flow<State<Response<UpdateOrderDetailsResponse>>> = flow {
        emit(State.Loading)

        try {
            val response = ApiProvider.providerApi()
                .updateOrderDetails(orderID = orderID, isApproved = isApproved)
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }

    }

    suspend fun getSpecificProductRepo(productID: String): Flow<State<Response<GetSpecificProductResponse>>> =
        flow {
            emit(State.Loading)
            try {
                val response = ApiProvider.providerApi().getSpecificProduct(productID)
                emit(State.Success(response))
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }


    suspend fun getSpecificUserRepo(userID: String): Flow<State<Response<GetSpecificUserResponse>>> =
        flow {
            emit(State.Loading)
            try {
                val response = ApiProvider.providerApi().getSpecificUser(user_id = userID)
                emit(State.Success(response))
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }

    suspend fun updateProductRepo(
        productID: String,
        product_name: String,
        product_category: String,
        product_price: String,
        product_stock: Int,
        product_expiry_date: String,
        product_company: String,
        product_description: String,
        product_image: String
    ): Flow<State<Response<UpdateProductResponse>>> = flow {
        emit(State.Loading)

        try {
            val response = ApiProvider.providerApi()
                .updateProductDetails(productID = productID, product_name = product_name, product_price = product_price, product_stock = product_stock, product_category = product_category, product_expiry_date = product_expiry_date, product_company = product_company, product_description = product_description, product_image = product_image)
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }

    }


    suspend fun updateProductStockRepo(
        productID: String,
        product_stock: Int,
    ): Flow<State<Response<UpdateProductResponse>>> = flow {
        emit(State.Loading)

        try {
            val response = ApiProvider.providerApi()
                .updateProductStockDetails(productID = productID, product_stock = product_stock)
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }

    }

    suspend fun getAllProductsRepo(): Flow<State<Response<GetAllProductsResponse>>> = flow {
        emit(State.Loading)
        try {
            val response = ApiProvider.providerApi().getAllProducts()
            emit(State.Success(response))
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }


}