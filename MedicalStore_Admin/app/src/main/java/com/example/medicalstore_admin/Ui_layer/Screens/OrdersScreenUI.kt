package com.example.medicalstore_admin.Ui_layer.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstore_admin.Ui_layer.ShimmerEffect
import com.example.medicalstore_admin.Ui_layer.ViewModel.AppViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreenUI(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {
       val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
        val getAllOrdersState = viewModel.getAllOrdersState.collectAsState()
        val updateOrderState = viewModel.updateOrderState.collectAsState()
    val productState = viewModel.getSpecificProductState.collectAsState()
    val productData = productState.value.Data?.body()

    var currentStock = rememberSaveable{mutableStateOf(0) }
          val orderData = getAllOrdersState.value.Data?.body()?: emptyList()



      val context = LocalContext.current


    when{
        updateOrderState.value.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        updateOrderState.value.Error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = updateOrderState.value.Error.toString())
            }
        }
        updateOrderState.value.Data != null -> {
            Toast.makeText(context, "${updateOrderState.value.Data?.body()?.message}", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "OrderScreenUI: ${updateOrderState.value.Data?.body()?.message}")
        }
    }

    Scaffold (
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row (verticalAlignment = Alignment.CenterVertically){
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Orders",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                },

                scrollBehavior = scrollBehaviour
            )
        }
    ){ innerPadding ->
        when{
            getAllOrdersState.value.Loading -> {
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
//                    CircularProgressIndicator()
//                }
                Column(modifier = Modifier.fillMaxSize().fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(70.dp))
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(8.dp)
                            .background(Color.LightGray, RoundedCornerShape(10.dp))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .padding(8.dp)
                            .background(Color.LightGray, RoundedCornerShape(10.dp))
                    )
//                    Spacer(modifier = Modifier.height(8.dp))
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(8.dp)
                            .background(Color.LightGray, RoundedCornerShape(10.dp))
                    )

                }
            }
            getAllOrdersState.value.Error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                    Text(text = getAllOrdersState.value.Error.toString())
                }
            }
            getAllOrdersState.value.Data != null -> {

                    LazyColumn (modifier = Modifier.fillMaxSize().padding(innerPadding),
                        contentPadding = PaddingValues(bottom = innerPadding.calculateBottomPadding() + 30.dp)
                        ){
                        items(orderData){ orderDta ->
                            ordersEachCard(
                                productName =  orderDta.product_name,
                                orderID = orderDta.order_id,
                                productId = orderDta.product_id,
                                productCategory = orderDta.product_category,
                                productPrice = orderDta.product_price,
                                productQuantity = orderDta.quantity,
                                totalPrice = orderDta.total_price,
                                userName = orderDta.name,
                                userId = orderDta.user_id,
                                navController = navController,
                                isApproved = orderDta.isApproved,
                                onClickApprove = {
                                    viewModel.approveOrder(
                                        orderID = orderDta.order_id,
                                        isApproved = 1
                                    )
                                    viewModel.getSpecificProduct(productID = orderDta.product_id)

                                    when{
                                        productState.value.Error != null -> {
                                            Toast.makeText(context, "error ${productState.value.Error.toString()}!", Toast.LENGTH_SHORT).show()
                                        }

                                        productState.value.Data != null -> {
                                            if(productData != null) {
                                                val firstProduct = productData
                                                val stock = firstProduct.product_stock


                                                viewModel.updateProductStock(
                                                    productID = orderDta.product_id,
                                                    product_stock = stock - orderDta.quantity
                                                )
                                            }
                                            else {
                                                // Handle the case when the product data list is empty or null
                                                Toast.makeText(context, "Product not found!", Toast.LENGTH_SHORT).show()
                                            }

//                                            viewModel.

                                        }
                                    }
                                },
                                orderDate = orderDta.date_of_order_creation
                            )
                        }
                    }
                }
            }
        }
    }






@Composable
fun ordersEachCard(
    productName: String,
    orderID: String,
    productId: String,
    productCategory: String,
    productPrice: Double,
    productQuantity: Int,
    totalPrice: Double,
    userName: String,
    userId: String,
    navController: NavController,
    isApproved: Int,
    onClickApprove: () -> Unit,
    orderDate: String


){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){

//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth()
//        ) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Text(text = "User Name: $userName")
                Text(text = "Name: $productName")
                Text(text = "Category: $productCategory")
                Text(text = "Quantity: $productQuantity")
                Text(text = "Product Price: $productPrice")
                Text(text = "Amount: $totalPrice")
                Text(text = "Order Date: $orderDate")
            }

            if (isApproved == 0) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        ),
                        onClick = {
                            onClickApprove()
                        },

                    ) {
                        Text(text = "Approve",)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Gray
                        ),
                        onClick = { /*TODO*/ }
                    ) {
                        Text(text = "Reject")
                    }
                }
            }
//        }



    }
}