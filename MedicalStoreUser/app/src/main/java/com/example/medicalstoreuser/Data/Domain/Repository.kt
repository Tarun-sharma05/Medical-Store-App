package com.example.medicalstoreuser.Data.Domain

import com.example.medicalstoreuser.Data.Response.CreateUserResponse
import com.example.medicalstoreuser.Data.Response.GetSpecificProductResponse
import com.example.medicalstoreuser.Data.Response.GetSpecificUserResponse
import com.example.medicalstoreuser.Data.ApiProvider
import com.example.medicalstoreuser.Data.Response.GetAllProductsResponse
import com.example.medicalstoreuser.Data.Response.LoginUserResponse
import com.example.medicalstoreuser.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


import retrofit2.Response

class Repository {
    suspend fun SignUpUser(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        address: String,
        pinCode: String
    ):  Flow<State<Response<CreateUserResponse>>> = flow {
        emit(State.Loading)
        try {
             val api = ApiProvider.providerApi().signUpUser(
            name = name,
            email = email,
            password = password,
            phoneNumber = phoneNumber,
            address = address,
            pinCode = pinCode
        )
            emit(State.Success(api))
     }
        catch (e:Exception){
            emit(State.Error(e.message.toString()))

        }
    }

//    suspend fun LoginUser(email: String, password: String): Flow<State<Response<LoginUserResponse>>> = flow{
//        emit(State.Loading)
//       try {
//           val api = ApiProvider.providerApi().loginUser(
//               email = email,
//               password = password
//           )
//           emit(State.Success(api))
//       }catch (e:Exception){
//           emit(State.Error(e.message.toString()))
//       }
//    }


    suspend fun LoginUser(email: String, password: String): Flow<State<Response<LoginUserResponse>>> = flow {
        emit(State.Loading)
        try {
            val response = ApiProvider.providerApi().loginUser(email, password)

            if (response.isSuccessful && response.body() != null) {
                val userId = response.body()?.message ?: ""
                if (userId.isNotEmpty()) {
                    emit(State.Success(response))  // Only emit success if userId is valid
                } else {
                    emit(State.Error("Login failed: Invalid response"))
                }
            } else {
                emit(State.Error("Login failed: ${response.message()}")) // Handle API error messages
            }
        } catch (e: Exception) {
            emit(State.Error(e.message.toString()))
        }
    }



    suspend fun getSpecificProductRepo(productID: String): Flow<State<Response<GetSpecificProductResponse>>> = flow {
            emit(State.Loading)
            try {
                val response = ApiProvider.providerApi().getSpecificProduct(productID)
                emit(State.Success(response))
            } catch (e: Exception) {
                emit(State.Error(e.message.toString()))
            }
        }


    suspend fun getSpecificUserRepo(userID: String): Flow<State<Response<GetSpecificUserResponse>>> = flow {
            emit(State.Loading)
            try {
                val response = ApiProvider.providerApi().getSpecificUser(user_id = userID)
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