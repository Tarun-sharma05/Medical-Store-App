package com.example.medicalstoreuser.ui_layer.ViewModel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalstoreuser.Data.Response.CreateUserResponse
import com.example.medicalstoreuser.Data.Response.GetAllProductsResponse
import com.example.medicalstoreuser.Data.Response.GetSpecificProductResponse
import com.example.medicalstoreuser.Data.Response.GetSpecificUserResponse
import com.example.medicalstoreuser.Data.Response.LoginUserResponse
import com.example.medicalstoreuser.Data.User_Pref.UserPreferenceManager
import com.example.medicalstoreuser.Data.Domain.Repository
import com.example.medicalstoreuser.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val Repository: Repository, private val userPreferenceManager: UserPreferenceManager) : ViewModel() {
    val userId: Flow<String?> = userPreferenceManager.userId
//    val userId: StateFlow<String?> = userPreferenceManager.userId
//    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)


   private val splashShowFlow = MutableStateFlow(false)
    val isSplashShow = splashShowFlow.asStateFlow()

   private val _signUpUserState = MutableStateFlow(SignUpState())
    val signUpUserState = _signUpUserState.asStateFlow()

   private val _loginUserState = MutableStateFlow(LoginState())
    val loginUserState = _loginUserState.asStateFlow()

    private val  _getSpecificUserState = MutableStateFlow(GetSpecificUserState())
    val getSpecificUserState = _getSpecificUserState.asStateFlow()

    private val  _getSpecificProductState = MutableStateFlow(GetSpecificProductState())
    val getSpecificProductState = _getSpecificProductState.asStateFlow()

    private val _getAllProductsState = MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductsState.asStateFlow()


    // StateFlow for search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()


    // StateFlow for search results
    private val _searchResults = MutableStateFlow<GetAllProductsResponse>(GetAllProductsResponse())
    val searchResults = _searchResults.asStateFlow()


    init {
        splashScreen()
    }
    fun splashScreen(){
        viewModelScope.launch {
            delay(3000)
//            val userId = userPreferenceManager.userId.value ?: "Bad Gateway"
//            val userId = userPreferenceManager.userId.collectAsState(initial = null)
             val userId = userPreferenceManager.userId.firstOrNull() ?: "Bad Gateway"
            splashShowFlow.value = true // Dismiss splash after fetching userId
        }
    }

    fun SignUp(
        name: String,
        email: String,
        password: String,
        phoneNumber: String,
        address: String,
        pinCode: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
             Repository.SignUpUser(
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

//    fun Login(email: String, password: String){
//        viewModelScope.launch(Dispatchers.IO) {
//             repository.LoginUser(email=email, password = password).collect{ state ->
//                when(state){
//                    is State.Loading ->{
//                        _loginUserState.value = LoginState(Loading = true)
//                    }
//                    is State.Success ->{
//                        val userid = state.data.body()?.message?: "Unknown"
//                        userPreferenceManager.saveUserId(userid)
//                        _loginUserState.value = LoginState(Data = state.data, Loading = false)
//                    }
//                    is State.Error ->{
//                        _loginUserState.value = LoginState(Error = state.message, Loading = false)
//                    }
//                }
//             }
//        }
//    }

    ///////////////////////////////////////////////////////////////////////////
    fun Login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.LoginUser(email, password).collect { state ->
                when (state) {
                    is State.Loading -> {
                        _loginUserState.value = LoginState(Loading = true)
                    }
//                    is State.Success -> {
//                        val userId = state.data // Since repository returns a String
//                        userPreferenceManager.saveUserId(userId)
//                        _loginUserState.value = LoginState(Data = userId, Loading = false)
//                    }


//                    is State.Success -> {
//                        val response = state.data  // response is of type Response<LoginUserResponse>
//
//                        if (response.isSuccessful && response.body() != null) {
//                            val userId = response.body()?.message ?: "Unknown" // Extract userId safely
//                            userPreferenceManager.saveUserId(userId) // Store userId in preferences
//                            _loginUserState.value = LoginState(Data = response, Loading = false)
//                        } else {
//                            _loginUserState.value = LoginState(Error = "Login failed: Invalid response", Loading = false)
//                        }
//                    }


                    is State.Success -> {
                        val response = state.data  // response is of type Response<LoginUserResponse>

                        if (response.isSuccessful && response.body() != null) {
                            val userId = response.body()?.message ?: ""

                            if (userId.isNotEmpty()) {
                                userPreferenceManager.saveUserId(userId) // ✅ Save only valid userId
                                _loginUserState.value = LoginState(Data = response, Loading = false)
                            } else {
                                userPreferenceManager.saveUserId("")  // ✅ Clear userId on failure
                                _loginUserState.value = LoginState(Error = "Invalid email or password", Loading = false)
                            }
                        } else {
                            userPreferenceManager.saveUserId("")  // ✅ Clear userId on failure
                            _loginUserState.value = LoginState(Error = "Login failed: ${response.message()}", Loading = false)
                        }
                    }



                    is State.Error -> {
                        userPreferenceManager.saveUserId("")
                        _loginUserState.value = LoginState(Error = state.message, Loading = false)
                    }
                }
            }
        }
    }



    fun LogOut(){
        viewModelScope.launch(Dispatchers.IO) {
            userPreferenceManager.clearUserID()
        }
    }


    init {
        getAllProducts()
    }
    fun getAllProducts(){
        viewModelScope.launch {
            Repository.getAllProductsRepo().collect { state ->
                when(state){
                    State.Loading -> {
                        _getAllProductsState.value = GetAllProductsState(Loading = true)
                    }
                    is State.Success -> {
                        _getAllProductsState.value = GetAllProductsState(Data = state.data, Loading = false)
                    }
                    is State.Error -> {
                        _getAllProductsState.value =
                            GetAllProductsState(Error = state.message, Loading = false)
                    }
                }
            }
        }
    }



    fun getSpecificUser(userID: String){
        viewModelScope.launch {
            Repository.getSpecificUserRepo(userID = userID).collect{
                when(it){
                    State.Loading -> {
                        _getSpecificUserState.value = GetSpecificUserState(Loading = true)
                    }
                    is State.Success -> {
                        _getSpecificUserState.value = GetSpecificUserState(Data = it.data, Loading = false)
                    }
                    is State.Error -> {
                        _getSpecificUserState.value =
                            GetSpecificUserState(Error = it.message, Loading = false)
                    }

                }
            }
        }
    }


    fun getSpecificProduct(productID: String) {
        viewModelScope.launch {
            Repository.getSpecificProductRepo(productID = productID).collect {
                when (it) {
                    State.Loading -> {
                        _getSpecificProductState.value = GetSpecificProductState(Loading = true)
                    }

                    is State.Success -> {
                        _getSpecificProductState.value =
                            GetSpecificProductState(Data = it.data, Loading = false)
                    }

                    is State.Error -> {
                        _getSpecificProductState.value =
                            GetSpecificProductState(Error = it.message, Loading = false)
                    }

                }
            }

        }
    }

//
//    // Search products by name
//    fun searchProducts(query: String) {
//        val allProducts = _getAllProductsState.value.Data?.body() ?: GetAllProductsResponse()
//        if (query.isEmpty()) {
//            // If the query is empty, show all products
//            _searchResults.value = allProducts
//        } else {
//            // Filter products by name
//            val filteredProducts = allProducts.filter { product ->
//                product.product_name.contains(query, ignoreCase = true)
//            }
//            _searchResults.value = GetAllProductsResponse().apply { addAll(filteredProducts) }
//        }
//    }

    // Search products by name
    fun searchProducts(query: String) {
        _searchQuery.value = query // Update the search query
        val allProducts = _getAllProductsState.value.Data?.body() ?: GetAllProductsResponse()
        if (query.isEmpty()) {
            // If the query is empty, show all products
            _searchResults.value = allProducts
        } else {
            // Filter products by name
            val filteredProducts = allProducts.filter { product ->
                product.product_name.contains(query, ignoreCase = true)
            }
            _searchResults.value = GetAllProductsResponse().apply { addAll(filteredProducts) }
        }
    }




}


data class SignUpState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<CreateUserResponse>? = null
)


data class LoginState(
    val Loading: Boolean = false,
    val Error: String? = null,
//    val Data: String? = null
    val Data: Response<LoginUserResponse>? = null
)

data class GetSpecificProductState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<GetSpecificProductResponse>? = null,
    )

data class GetSpecificUserState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<GetSpecificUserResponse>? = null,
    )

data class GetAllProductsState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<GetAllProductsResponse>? = null,
)

