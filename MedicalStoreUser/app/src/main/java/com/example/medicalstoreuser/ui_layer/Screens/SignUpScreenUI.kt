package com.example.medicalstoreuser.ui_layer.Screens


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstoreuser.R
import com.example.medicalstoreuser.ui_layer.Common.MultiColorText
import com.example.medicalstoreuser.ui_layer.Navigation.ScreenRoutes
import com.example.medicalstoreuser.ui_layer.ViewModel.AppViewModel


@Composable
fun SignUpScreenUI(navController: NavController, viewModel: AppViewModel = hiltViewModel()){

        val state = viewModel.signUpUserState.collectAsState()
           val context = LocalContext.current





       var userName by remember { mutableStateOf("") }
       var userPassword by remember { mutableStateOf("") }
       var userEmail by remember { mutableStateOf("") }
       var phoneNumber by remember { mutableStateOf("") }
       var address by remember { mutableStateOf("") }
       var pinCode by remember { mutableStateOf("") }

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
                userName = ""
                userPassword = ""
                userEmail = ""
                phoneNumber  = ""
                address = ""
                pinCode = ""

            }
        }
    }


    LazyColumn(modifier = Modifier
        .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        item{
            Spacer(modifier = Modifier.height(40.dp))
            Image(painter = painterResource(id = R.drawable.medical_app_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape))

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = userName,
                onValueChange = {
                    userName = it},
                label = { Text(text = "Name") },
                placeholder = { Text(text = "Enter Name") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)

            )


            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = userEmail,
                onValueChange = {
                    userEmail = it
                },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Enter Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)

            )

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = userPassword,
                onValueChange = {
                    userPassword = it
                },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter Password") },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)

            )

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = phoneNumber ,
                onValueChange = {
                    phoneNumber = it
                },
                label = { Text(text = "Phone") },
                placeholder = { Text(text = "Enter Phone Number ") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)

            )

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = address,
                onValueChange = {
                    address = it
                },
                label = { Text(text = "Address") },
                placeholder = { Text(text = "Enter Address") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)

            )

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(value = pinCode,
                onValueChange = {
                    pinCode = it
                },
                label = { Text(text = "PinCode") },
                placeholder = { Text(text = "Enter PinCode") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

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

            Spacer(modifier = Modifier.height(40.dp))
            Button(onClick = {
               viewModel.SignUp(
                   name = userName,
                   password = userPassword,
                   email = userEmail,
                   phoneNumber = phoneNumber,
                   address = address,
                   pinCode = pinCode,
               )
            },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                colors = ButtonDefaults.buttonColors(

                )

            ){
                Text(text = "SignUp")
            }

            Spacer(modifier = Modifier.height(30.dp))
           MultiColorText("Already have an account ?  ","Login", modifier = Modifier.clickable {

                      navController.navigate(ScreenRoutes.LoginScreen)
           })

        }
    }
}