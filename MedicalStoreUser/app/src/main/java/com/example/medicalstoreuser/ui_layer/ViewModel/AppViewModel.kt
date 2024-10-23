package com.example.medicalstoreuser.ui_layer.ViewModel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalstoreuser.Data.Response.LoginUserResponse
import com.example.medicalstoreuser.Data.Response.createUserResponse
import com.example.medicalstoreuser.Data.User_Pref.UserPreferenceManager
import com.example.medicalstoreuser.Domain.Repository
import com.example.medicalstoreuser.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val repository: Repository, private val userPreferenceManager: UserPreferenceManager) : ViewModel() {

   private val _signUpUserState = MutableStateFlow(SignUpState())
    val signUpUserState = _signUpUserState.asStateFlow()
   private val _loginUserState = MutableStateFlow(LoginState())
    val loginUserState = _loginUserState.asStateFlow()

    fun SignUp(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        address: String,
        pinCode: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
             repository.SignUpUser(
                name = name,
                email = email,
                password = password,
                phoneNumber = phoneNumber,
                address = address,
                pinCode = pinCode,
                ).collect{
                  when(it){
                      is State.Loading -> {
                          _signUpUserState.value = SignUpState(Loading = true)
                      }
                      is State.Success ->{
                          _signUpUserState.value = SignUpState(Data = it.data, Loading = false)
                      }
                      is State.Error ->{
                          _signUpUserState.value = SignUpState(Error = it.message, Loading = false)
                      }
                  }
             }

        }

    }

    fun Login(
        email: String,
        password: String,

        ) {
        viewModelScope.launch(Dispatchers.IO) {
             repository.LoginUser(
                email=email,
                password = password
            ).collect{ state ->
                when(state){
                    is State.Loading ->{
                        _loginUserState.value = LoginState(Loading = true)
                    }
                    is State.Success ->{
                        val userid = state.data.message()
                        userPreferenceManager.saveUserId(userid)
                        _loginUserState.value = LoginState(Data = state.data, Loading = false)
                    }
                    is State.Error ->{
                        _loginUserState.value = LoginState(Error = state.message, Loading = false)
                    }
                }
             }

        }

    }

}


data class SignUpState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<createUserResponse>? = null
)


data class LoginState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<LoginUserResponse>? = null
)