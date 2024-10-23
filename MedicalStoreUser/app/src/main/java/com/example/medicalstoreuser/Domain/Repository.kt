package com.example.medicalstoreuser.Domain

import com.example.medicalstoreuser.Data.Response.LoginUserResponse
import com.example.medicalstoreuser.Data.Response.createUserResponse
import com.example.medicalstoreuser.Data.Response.getAllUserResponce
import com.example.medicalstoreuser.Data.apiProvider
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
    ):  Flow<State<Response<createUserResponse>>> = flow {
        emit(State.Loading)
        try {
             val api = apiProvider.providerApi().signUpUser(
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

    suspend fun LoginUser(
        email: String,
        password: String
    ): Flow<State<Response<LoginUserResponse>>> = flow{
        emit(State.Loading)
       try {
           val api = apiProvider.providerApi().loginUser(
               email = email,
               password = password
           )
           emit(State.Success(api))
       }catch (e:Exception){
           emit(State.Error(e.message.toString()))
       }

    }






}