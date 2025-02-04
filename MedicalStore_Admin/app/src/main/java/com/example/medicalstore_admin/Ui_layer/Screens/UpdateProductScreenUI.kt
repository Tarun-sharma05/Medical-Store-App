package com.example.medicalstore_admin.Ui_layer.Screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstore_admin.Network.Response.GetSpecificProductResponse
import com.example.medicalstore_admin.Ui_layer.ShimmerEffect
import com.example.medicalstore_admin.Ui_layer.ViewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateProductScreenUI(productID: String, viewModel: AppViewModel = hiltViewModel(), navController: NavController) {

    val productState by viewModel.getSpecificProductState.collectAsState()
    val productData = productState.Data?.body()

    var updateProductState = viewModel.updateProductState.collectAsState()
    var updateProductData = updateProductState.value.Data?.body()

    val context = LocalContext.current

    var productName by remember { mutableStateOf("") }
    var productPrice by remember { mutableStateOf("") }
    var productStock by remember { mutableStateOf("") }
    var productCategory by remember { mutableStateOf("") }
    var productExpiryDate by remember { mutableStateOf("") }
    var productCompany by remember { mutableStateOf("") }
    var productDescription by remember { mutableStateOf("") }
    var productImage by remember { mutableStateOf("") }


    var productPriceText by remember { mutableStateOf(productPrice.toString()) }




    LaunchedEffect(productID) {
        // Calling the ViewModel method to fetch the product based on productID
        viewModel.getSpecificProduct(productID)
    }

    LaunchedEffect(productData){
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

    when{
        updateProductState.value.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        updateProductState.value.Error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = updateProductState.value.Error.toString())
            }
        }
        updateProductState.value.Data != null -> {
            Toast.makeText(context, "${updateProductState.value.Data?.body()?.message}", Toast.LENGTH_SHORT).show()
//            Log.d("TAG", "OrderScreenUI: ${updateProductState.value.Data?.body()?.message}")
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



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(16.dp),

                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Box(modifier = Modifier.padding(bottom = 30.dp)){
                    Text(text = "Update Product", fontSize = 24.sp, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(value = productName, onValueChange = { productName = it  }, label = { Text(text = "Product Name") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = productCategory, onValueChange = { productCategory = it }, label = { Text(text = "Product Category") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = productPrice, onValueChange = { productPrice = it }, label = { Text(text = "Product Price") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = productStock, onValueChange = { productStock = it }, label = { Text(text = "Product Stock") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = productExpiryDate, onValueChange = { productExpiryDate = it }, label = { Text(text = "Exp Date") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = productCompany, onValueChange = { productCompany = it }, label = { Text(text = "Product Company") }, modifier = Modifier.fillMaxWidth())
                OutlinedTextField(value = productDescription,
                    onValueChange = { productDescription = it},
                    label = { Text(text = "Product Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2, maxLines = 2)
                OutlinedTextField(value = productImage,
                    onValueChange = { productImage = it},
                    label = { Text(text = "Product Image") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 2, maxLines = 2)

                Spacer(modifier = Modifier.height(12.dp))

                Button(onClick = {
                    viewModel.updateProduct(
                        productID, productName, productCategory, productPrice,productStock.toIntOrNull()?: 0, productExpiryDate, productCompany, productDescription, productImage
                    )
                }, modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
                ) { Text(text = "Update Product") }
            }


            
           
        }
    }



    }



