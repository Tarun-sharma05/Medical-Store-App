package com.example.medicalstore_admin.Ui_layer.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalstore_admin.Network.Repository.Repository
import com.example.medicalstore_admin.Network.Response.AddProductResponse
import com.example.medicalstore_admin.Network.Response.GetAllUserResponse
import com.example.medicalstore_admin.Network.Response.UpdateUserInfoResponse
import com.example.medicalstore_admin.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.descriptors.PrimitiveKind
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(private val Repository: Repository): ViewModel() {
    private val _getAllUserState = MutableStateFlow(GetAllUserState())
    val getAllUserState = _getAllUserState.asStateFlow()

    private val _updateUserState = MutableStateFlow(UpdateUserState())
    val updateUserState = _updateUserState.asStateFlow()

    private val _addProductsState = MutableStateFlow(AddProductsState())
    val addProductsState = _addProductsState.asStateFlow()

    fun addProduct(
              product_name: String,
              product_price: Float,
              product_stock: Int,
              product_category: String,
              product_expiry_date: String,

    ){
        viewModelScope.launch (Dispatchers.IO){
             Repository.addProductRepo(
                 product_name = product_name,
                 product_price = product_price,
                 product_stock = product_stock,
                 product_category = product_category,
                 product_expiry_date = product_expiry_date,
             ).collect{
                 when(it){
                     is State.Loading -> {
                         _addProductsState.value = AddProductsState(Loading = true)
                     }
                     is State.Success ->{
                         _addProductsState.value = AddProductsState(Data = it.data, Loading = false)
                 }
                     is State.Error ->{
                         _addProductsState.value = AddProductsState(Error = it.message, Loading = false)
                     }
                 }
             }
        }
    }

    fun  approveUser(user_id: String, isApproved: Int){
        viewModelScope.launch {
            Repository.approveUserRepo(
                user_id = user_id,
                isApproved = isApproved
            ).collect{
                when(it){
                    is State.Loading -> {
                        _updateUserState.value = UpdateUserState(Loading = true)
                    }
                    is State.Success ->{
                        _updateUserState.value = UpdateUserState(Data = it.data, Loading = false)
                    }

                    is State.Error ->{
                        _updateUserState.value = UpdateUserState(Error = it.message, Loading = false)
                    }
                }


            }
        }
    }

    init {
        getAllUser()
    }

    fun getAllUser() {
        viewModelScope.launch {
            Repository.getAllUserRepo().collect { state ->
                when (state){
                    State.Loading -> {
                        _getAllUserState.value = GetAllUserState(Loading = true)
                    }

                    is State.Success -> {
                        _getAllUserState.value = GetAllUserState(Data = state.data, Loading = false)
                    }

                    is State.Error -> {
                        _getAllUserState.value =
                            GetAllUserState(Error = state.message, Loading = false)
                    }
                }
            }
        }
    }
}

data class GetAllUserState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<GetAllUserResponse>? = null,
)

data class UpdateUserState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<UpdateUserInfoResponse>? = null,

)

data class AddProductsState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<AddProductResponse>? = null,

    )