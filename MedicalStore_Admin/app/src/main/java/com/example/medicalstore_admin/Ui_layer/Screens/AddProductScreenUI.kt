package com.example.medicalstore_admin.Ui_layer.Screens

import android.icu.text.SimpleDateFormat
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import com.example.medicalstore_admin.Ui_layer.ViewModel.AppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreenUI(viewModel: AppViewModel = hiltViewModel(), navHostController: NavHostController){
      val state = viewModel.addProductsState.collectAsState()
       val context = LocalContext.current


    var product_name by remember { mutableStateOf("") }
    var product_price by remember { mutableStateOf("") }
    var product_stock by remember { mutableStateOf("") }
    var product_category by remember { mutableStateOf("")}
    var product_expiry_date by remember { mutableStateOf("")}



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

              }
        }
    }

    Column (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ){


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
            ) {
                viewModel.addProduct(
                    product_name = product_name,
                    product_price = product_price.toFloat(),
                    product_stock = product_stock.toInt(),
                    product_category = product_category,
                    product_expiry_date = product_expiry_date
                )
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        },
            modifier = Modifier.fillMaxWidth()){
            Text(text = "Add Product")
        }


    }
}

