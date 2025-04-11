package com.example.medicalstore_admin.Ui_layer.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalstore_admin.Network.Repository.Repository
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
import com.example.medicalstore_admin.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    private val _getAllOrdersState = MutableStateFlow(GetAllOrdersState())
    val getAllOrdersState = _getAllOrdersState.asStateFlow()

    private val _updateOrderState = MutableStateFlow(UpdateOrderState())
    val updateOrderState = _updateOrderState.asStateFlow()

    private val  _getSpecificProductState = MutableStateFlow(GetSpecificProductState())
    val getSpecificProductState = _getSpecificProductState.asStateFlow()

    private val  _getSpecificUserState = MutableStateFlow(GetSpecificUserState())
    val getSpecificUserState = _getSpecificUserState.asStateFlow()

    private val  _updateProductState = MutableStateFlow(UpdateProductState())
    val updateProductState = _updateProductState.asStateFlow()

    private val  _updateProductStockState = MutableStateFlow(UpdateProductStockState())
    val updateProductStockState = _updateProductStockState.asStateFlow()

    private val _getAllSellHistoryState = MutableStateFlow(GetAllSellHistoryState())
    val getAllSellHistoryState = _getAllSellHistoryState.asStateFlow()

    private val _getAllProductsState = MutableStateFlow(GetAllProductsState())
    val getAllProductsState = _getAllProductsState.asStateFlow()

    private val splashShowFlow = MutableStateFlow(false)
    val isSplashShow = splashShowFlow.asStateFlow()


    init {
        splashScreen()
    }
    fun splashScreen(){
        viewModelScope.launch {
            delay(3000)
            splashShowFlow.value = true
        }
    }

    fun addProduct(
        product_name: String,
        product_category: String,
        product_price: Double,
        product_stock: Int,
        product_expiry_date: String,
        product_company: String,
        product_description: String,
        product_image: String

    ){
        viewModelScope.launch (Dispatchers.IO){
             Repository.addProductRepo(
                 product_name = product_name,
                 product_category = product_category,
                 product_price = product_price,
                 product_stock = product_stock,
                 product_expiry_date = product_expiry_date,
                 product_company = product_company,
                 product_description = product_description,
                 product_image = product_image
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

    init {
        getAllOrders()
    }

    fun getAllOrders(){
        viewModelScope.launch {
            Repository.getOrdersRepo().collect { state ->
                when(state){
                    State.Loading -> {
                        _getAllOrdersState.value = GetAllOrdersState(Loading = true)
                    }
                    is State.Success -> {
                        _getAllOrdersState.value = GetAllOrdersState(Data = state.data, Loading = false)
                    }

                    is State.Error -> {
                        _getAllOrdersState.value =
                            GetAllOrdersState(Error = state.message, Loading = false)
                    }
                }
            }
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


    init {
        getAllSellHistory()
    }
    fun getAllSellHistory(){
        viewModelScope.launch {
            Repository.getSellHistoryRepo().collect{ state ->
                when(state){
                     State.Loading -> {
                         _getAllSellHistoryState.value = GetAllSellHistoryState(Loading = true)
                     }
                     is State.Success -> {
                         _getAllSellHistoryState.value = GetAllSellHistoryState(Data = state.data, Loading = false)
                     }
                    is State.Error ->{
                         _getAllSellHistoryState.value =
                             GetAllSellHistoryState(Error = state.message, Loading = false)
                    }
                }
            }
        }
    }

    fun  approveOrder(orderID: String, isApproved: Int){
        viewModelScope.launch {
            Repository.approveOrderDetailsRepo(
                orderID = orderID,
                isApproved = isApproved
            ).collect{
                when(it){
                    is State.Loading -> {
                        _updateOrderState.value = UpdateOrderState(Loading = true)
                    }
                    is State.Success ->{
                        _updateOrderState.value = UpdateOrderState(Data = it.data, Loading = false)
                    }

                    is State.Error ->{
                        _updateOrderState.value = UpdateOrderState(Error = it.message, Loading = false)
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


    fun  updateProduct(productID: String, product_name: String, product_category: String, product_price: String, product_stock: Int, product_expiry_date: String, product_company: String, product_description: String, product_image: String){
        viewModelScope.launch {
            Repository.updateProductRepo(
                productID = productID,
                product_name = product_name,
                product_category = product_category,
                product_price = product_price,
                product_stock = product_stock,
                product_expiry_date = product_expiry_date,
                product_company = product_company,
                product_description = product_description,
                product_image = product_image
            ).collect{
                when(it){
                    is State.Loading -> {
                        _updateProductState.value = UpdateProductState(Loading = true)
                    }
                    is State.Success ->{
                        _updateProductState.value = UpdateProductState(Data = it.data, Loading = false)
                    }
                    is State.Error ->{
                        _updateProductState.value = UpdateProductState(Error = it.message, Loading = false)
                    }
                }


            }
        }
    }

    fun  updateProductStock(productID: String, product_stock: Int){
        viewModelScope.launch {
            Repository.updateProductStockRepo(
                productID = productID,
                product_stock = product_stock,

            ).collect{
                when(it){
                    is State.Loading -> {
                        _updateProductStockState.value = UpdateProductStockState(Loading = true)
                    }
                    is State.Success ->{
                        _updateProductStockState.value = UpdateProductStockState(Data = it.data, Loading = false)
                    }
                    is State.Error ->{
                        _updateProductStockState.value = UpdateProductStockState(Error = it.message, Loading = false)
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


data class GetAllOrdersState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<GetAllOrdersResponse>? = null,
    )

data class UpdateOrderState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<UpdateOrderDetailsResponse>? = null,
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

data class UpdateProductState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<UpdateProductResponse>? = null,
)

data class UpdateProductStockState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<UpdateProductResponse>? = null,
)

data class GetAllSellHistoryState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<GetAllSellsHistoryResponse>? = null,
)

data class GetAllProductsState(
    val Loading: Boolean = false,
    val Error: String? = null,
    val Data: Response<GetAllProductsResponse>? = null,
)