package com.example.medicalstoreuser.ui_layer.Screens

import android.util.Log
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.medicalstoreuser.R
import com.example.medicalstoreuser.ui_layer.Common.MultiColorText
import com.example.medicalstoreuser.ui_layer.Navigation.SignUpScreen
import com.example.medicalstoreuser.ui_layer.ViewModel.AppViewModel


@Composable
fun LoginScreenUI(
       navController: NavController,
       viewModel: AppViewModel = hiltViewModel()
) {

    val state = viewModel.loginUserState.collectAsState()
     val context = LocalContext.current

//      else{
//          Toast.makeText(context, "Loading", LENGTH_SHORT).show()
//      }



    var userPassword by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }


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
//            Toast.makeText(context, "${state.value.Data?.body()?.message}", Toast.LENGTH_SHORT).show()

            Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
        }
    }



    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.medical_app_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = userEmail,
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
            OutlinedTextField(
                value = userPassword,
                onValueChange = {
                    userPassword = it
                },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter Your Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)

            )




            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                      viewModel.Login(
                          email = userEmail,
                          password = userPassword
                      )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                colors = ButtonDefaults.buttonColors(

                )

            ) {
                Text(text = "Login")
            }


            Spacer(modifier = Modifier.height(30.dp))
            MultiColorText("Don't have an account ?", "SignUp", modifier = Modifier.clickable {
                navController.navigate(SignUpScreen)
            })
        }
    }
}

//@Composable
//fun MultiColorText(){
//    val annotatedString = buildAnnotatedString {
//           withStyle(style = SpanStyle(color = Color.Black)){
//               append("Don't have an account ?  ")
//           }
//           withStyle(style = SpanStyle(color = Color.Blue)){
//               append(" SignUp")
//           }
//        append("!")
//    }
//}