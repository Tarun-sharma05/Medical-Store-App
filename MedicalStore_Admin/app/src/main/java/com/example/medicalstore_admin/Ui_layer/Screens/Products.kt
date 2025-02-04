package com.example.medicalstore_admin.Ui_layer.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.SubcomposeAsyncImage
import com.example.medicalstore_admin.Ui_layer.Navigation.AddProductScreen
import com.example.medicalstore_admin.Ui_layer.Navigation.Screen
import com.example.medicalstore_admin.Ui_layer.ShimmerEffect
import com.example.medicalstore_admin.Ui_layer.ViewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Products(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val getAllProductsState = viewModel.getAllProductsState.collectAsState()
    val productState = viewModel.getSpecificProductState.collectAsState()
    val productData = getAllProductsState.value.Data?.body()?: emptyList()
    val state = viewModel.updateProductState.collectAsState()


    val context = LocalContext.current

    when{
        state.value.Loading -> {
//            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
//                CircularProgressIndicator()
//            }
            ShimmerEffect(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(8.dp)
                    .background(Color.LightGray, RoundedCornerShape(10.dp))
            )
        }
        state.value.Error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
               Text(text = getAllProductsState.value.Error.toString())
            }
        }
        state.value.Data != null -> {
            Toast.makeText(context, "${state.value.Data?.body()?.message}", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "Products: ${state.value.Data?.body()?.message}")
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize()
            .nestedScroll(scrollBehaviour.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Products",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                    }
                },

                actions = {
                    IconButton(onClick = {navController.navigate(AddProductScreen)}) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
                    }

                },


                scrollBehavior = scrollBehaviour
            )

        }
    ) { innerPadding ->

        when {
            getAllProductsState.value.Loading -> {
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

            getAllProductsState.value.Error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.value.Error.toString())
                }
            }

            getAllProductsState.value.Data != null -> {



//                AllCardLazyColumn(viewModel = viewModel, navHostController = navHostController)


                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentPadding = PaddingValues(bottom = innerPadding.calculateBottomPadding() + 30.dp)
                ) {

                   items(productData) { productDta ->
                       productsEachCard(
                           productId = productDta.product_id,
                           productName = productDta.product_name,
                           productCategory = productDta.product_category,
                           productPrice = productDta.product_price,
                           Stock = productDta.product_stock,
                           productExpiryDate = productDta.product_expiry_date,
                           productCompany = productDta.product_company,
                           productDescription = productDta.product_description,
                           productImage = productDta.product_image,
                           navController = navController
                       )
                   }

                }

            }
        }




    }



}


@Composable
fun productsEachCard(
    productId: String,
    productName: String,
    productCategory: String,
    productPrice: Double,
    Stock: Int,
    productExpiryDate: String,
    productCompany: String,
    productDescription: String,
    productImage: String,
    navController: NavController,


    ){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                navController.navigate(Screen.UpdateProduct.createRoute(productID = productId))
            },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ){



            Column() {
                Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp, top = 16.dp, bottom = 8.dp)) {
                    Text(text = "$productName", style = MaterialTheme.typography.titleLarge)
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Fill full width of the parent
                        .height(220.dp) // Fixed height for the image
                ){
                    SubcomposeAsyncImage(
                        model = productImage,
                        loading = {
                            CircularProgressIndicator()
                        },
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp)) {
                    Text(text = "product ID: $productId", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Price: $productPrice", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Stock: $Stock", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Category: $productCategory", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Exp Date: $productExpiryDate", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Company: $productCompany", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Description: $productDescription", style = MaterialTheme.typography.bodyMedium)
                }

            }


    }
}