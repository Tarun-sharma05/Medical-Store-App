package com.example.medicalstore_admin.Ui_layer.Screens

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialogDefaults.containerColor
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
fun Sell_history(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    val getAllSellHistoryState = viewModel.getAllSellHistoryState.collectAsState()

    var currentStock = rememberSaveable { mutableStateOf(0) }
    val sellsData = getAllSellHistoryState.value.Data?.body() ?: emptyList()


    val context = LocalContext.current


    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Sells History",
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
    ) { innerPadding ->

        when  {

            getAllSellHistoryState.value.Loading -> {
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
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

            getAllSellHistoryState.value.Error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = getAllSellHistoryState.value.Error.toString())
                }
            }

            getAllSellHistoryState.value.Data != null -> {

                    LazyColumn(modifier = Modifier.fillMaxSize()
                        .padding(innerPadding),
                        contentPadding = PaddingValues(bottom = innerPadding.calculateBottomPadding() + 30.dp)
                    ) {
                        items(sellsData) { sellsData ->
                            sellsEachCard(
                                productName = sellsData.product_name,
                                orderID = sellsData.order_id,
                                productId = sellsData.product_id,
                                productCategory = sellsData.product_category,
                                productPrice = sellsData.product_price,
                                productQuantity = sellsData.quantity,
                                totalPrice = sellsData.total_price,
                                userName = sellsData.name,
                                userId = sellsData.user_id,
                                navController = navController,
                                orderDate = sellsData.date_of_order_creation,
                                sellStatus = sellsData.status,
                                sellDate = sellsData.date_of_sale


                            )
                        }

                    }


            }


        }

    }

}

@Composable
fun sellsEachCard(
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
    orderDate: String,
    sellStatus: String,
    sellDate: String

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
            Text(text = "Product Name: $productName")
            Text(text = "Category: $productCategory")
            Text(text = "Quantity: $productQuantity")
            Text(text = "Product Price: $productPrice")
            Text(text = "Total Amount: $totalPrice")
            Text(text = "Order Date: $orderDate")
            Text(text = "Sell Status: $sellStatus")
            Text(text = "Sell Date: $sellDate")
        }




    }
}
