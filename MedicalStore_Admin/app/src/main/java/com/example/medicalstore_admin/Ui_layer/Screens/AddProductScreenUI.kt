package com.example.medicalstore_admin.Ui_layer.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

import com.example.medicalstore_admin.Ui_layer.ViewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreenUI( viewModel: AppViewModel = hiltViewModel(), navController: NavController){
      val state = viewModel.addProductsState.collectAsState()
       val context = LocalContext.current


    var product_name by remember { mutableStateOf("") }
    var product_price by remember { mutableStateOf("") }
    var product_stock by remember { mutableStateOf("") }
    var product_category by remember { mutableStateOf("")}
    var product_expiry_date by remember { mutableStateOf("")}
    var product_company by remember { mutableStateOf("")}
    var product_description by remember { mutableStateOf("")}
    var product_image by remember { mutableStateOf("")}



    when{
        state.value.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        state.value.Error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                Text(text = state.value.Error.toString())
            }
        }
        state.value.Data != null -> {
            Toast.makeText(context, "${state.value.Data?.body()?.message}", Toast.LENGTH_SHORT).show()

              LaunchedEffect(Unit){
                  product_name = ""
                  product_price = ""
                  product_stock = ""
                  product_category = ""
                  product_expiry_date = ""
                  product_company = ""
                  product_description = ""
                  product_image = ""

              }
        }
    }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ){
        Text(text = "Add Product", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, modifier = Modifier.padding(bottom = 16.dp))
        Spacer(modifier = Modifier.height(70.dp))



         OutlinedTextField(
             value = product_name,
             onValueChange = {product_name = it},
             label = { Text(text = "Name")},
             singleLine = true,
             modifier = Modifier.fillMaxWidth())

         OutlinedTextField( value = product_price,
             onValueChange = {product_price = it},
             label = { Text(text = "Price")},
             singleLine = true,
             modifier = Modifier.fillMaxWidth())

        OutlinedTextField( value = product_stock,
            onValueChange = {product_stock = it},
            label = { Text(text = "Stock")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth())

        OutlinedTextField( value = product_category,
            onValueChange = {product_category = it},
            label = { Text(text = "Category")},
            singleLine = true,
            modifier = Modifier.fillMaxWidth())

        OutlinedTextField(
            value = product_expiry_date,
            onValueChange = {product_expiry_date = it },
            label = { Text(text = "Expiry Date")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = product_company,
            onValueChange = {product_company = it},
            label = { Text(text = "Company")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = product_description,
            onValueChange = {product_description = it},
            label = { Text(text = "Description")},
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = product_image,
            onValueChange = {product_image = it},
            label = { Text(text = "Image")},
            modifier = Modifier.fillMaxWidth()
        )


        val responseCode by remember { mutableStateOf<Int?>(null) }

        LaunchedEffect(responseCode) {
            responseCode?.let { code ->
                val message = if( code == 200 ){
                    "Product Added Successfully"
                }else{
                    "Failed to Add Product"

                }
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

            }
        }

        Button(onClick = {
            if (product_name.isNotEmpty()
                && product_price.isNotEmpty()
                && product_stock.isNotEmpty()
                && product_category.isNotEmpty()
                && product_expiry_date.isNotEmpty()
                && product_company.isNotEmpty()
                && product_description.isNotEmpty()
                && product_image.isNotEmpty()
            ) {
                viewModel.addProduct(
                    product_name = product_name,
                    product_price = product_price.toDouble(),
                    product_stock = product_stock.toInt(),
                    product_category = product_category,
                    product_expiry_date = product_expiry_date,
                    product_company = product_company,
                    product_description = product_description,
                    product_image = product_image
                )
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        },

            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),

                    modifier = Modifier.fillMaxWidth()){
            Text(text = "Add Product")
        }


    }
}

