package com.example.medicalstore_admin.Network.Repository

import com.example.medicalstore_admin.Network.ApiProvider
import com.example.medicalstore_admin.Network.Response.AddProductResponse
import com.example.medicalstore_admin.Network.Response.GetAllUserResponse
import com.example.medicalstore_admin.Network.Response.UpdateUserInfoResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import com.example.medicalstore_admin.State
import java.sql.Date


class Repository {

       suspend fun getAllUserRepo(): Flow<State<Response<GetAllUserResponse>>> = flow {

           emit(State.Loading)
           try {
               val response = ApiProvider.providerApi().getAllUsers()
               emit(State.Success(response))
           }
           catch (e: Exception){
               emit(State.Error(e.message.toString()))

           }
       }

        suspend fun approveUserRepo( user_id: String, isApproved:Int): Flow<State<Response<UpdateUserInfoResponse>>> = flow {
            emit(State.Loading)

            try {
                val response = ApiProvider.providerApi().updateUserInfo(user_id = user_id,isApproved = isApproved)
                 emit(State.Success(response))
            }catch (e: Exception){
                emit(State.Error(e.message.toString()))
            }
        }

       suspend fun addProductRepo(
                   product_name : String,
                   product_price : Float,
                   product_stock : Int,
                   product_category :String,
                   product_expiry_date : String
       ): Flow<State<Response<AddProductResponse>>> = flow {
           emit(State.Loading)
           try {
               val response = ApiProvider.providerApi().addProduct(
                   product_name = product_name,
                   product_price = product_price,
                   product_stock = product_stock,
                   product_category = product_category,
                   product_expiry_date = product_expiry_date
               )
               emit(State.Success(response))
           } catch (e: Exception) {
           emit(State.Error(e.message.toString()))
           }
       }
}