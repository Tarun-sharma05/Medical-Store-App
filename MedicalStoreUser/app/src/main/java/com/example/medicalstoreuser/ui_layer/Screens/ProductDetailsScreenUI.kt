package com.example.medicalstoreuser.ui_layer.Screens

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.medicalstoreuser.ui_layer.ViewModel.AppViewModel


@Composable
fun ProductDetailsScreenUI(productID: String, viewModel: AppViewModel = hiltViewModel(), navController: NavController) {
    val productState by viewModel.getSpecificProductState.collectAsState()
    val productData = productState.Data?.body()


    val context = LocalContext.current

    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productStock by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("") }
    var productExpiryDate by remember { mutableStateOf("") }
    var productCompany by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productImage by remember { mutableStateOf("") }

    LaunchedEffect(productID) {
        // Calling the ViewModel method to fetch the product based on productID
        viewModel.getSpecificProduct(productID)

    }

    LaunchedEffect(productData) {
        // Calling the ViewModel method to fetch the product based on productID
        productData?.let {
            productName = it.product_name
            productPrice = it.product_price.toString()
            productStock = it.product_stock.toString()
            productCategory = it.product_category
            productExpiryDate = it.product_expiry_date
            productCompany = it.product_company
            productDescription = it.product_description
            productImage = it.product_image
        }
    }


    when {
        productState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }


        }

        productState.Error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${productState.Error}", color = Color.Red)
            }
        }

        productData != null -> {

            Log.d("ProductDetailsScreenUI", "Product Data: $productData")


//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .fillMaxWidth()
//                    .padding(16.dp),
//
//                verticalArrangement = Arrangement.Center,
//                horizontalAlignment = Alignment.Start
//            ) {
//                Text(text = "Product Name: $productName")
//                Text(text = "Product Price: $productPrice")
//                Text(text = "Product Stock: $productStock")
//                Text(text = "Product Category: $productCategory")
//                Text(text = "Product Expiry Date: $productExpiryDate")
//                Text(text = "Product Company: $productCompany")
//                Text(text = "Product Description: $productDescription")
//                Text(text = "Product Image: $productImage")
//            }





            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Product Image
                AsyncImage(
                    model = productData.product_image,
                    contentDescription = "Product Image",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Product Details Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(4.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        ProductDetailItem("Name", productData.product_name)
                        ProductDetailItem("Price", "₹${productData.product_price}")
                        ProductDetailItem("Stock", productData.product_stock.toString())
                        ProductDetailItem("Category", productData.product_category)
                        ProductDetailItem("Expiry Date", productData.product_expiry_date)
                        ProductDetailItem("Company", productData.product_company)
                        ProductDetailItem("Description", productData.product_description)
                    }
                }
            }







        }


    }
}



@Composable
fun ProductDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

