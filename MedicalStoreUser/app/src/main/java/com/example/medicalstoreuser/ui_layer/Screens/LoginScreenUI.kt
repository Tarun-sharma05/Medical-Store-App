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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstoreuser.R
import com.example.medicalstoreuser.ui_layer.Common.MultiColorText
import com.example.medicalstoreuser.ui_layer.Navigation.ScreenRoutes
import com.example.medicalstoreuser.ui_layer.Navigation.SubNavigation
import com.example.medicalstoreuser.ui_layer.ViewModel.AppViewModel


@Composable
fun LoginScreenUI(navController: NavController, viewModel: AppViewModel = hiltViewModel()) {
    val state = viewModel.loginUserState.collectAsState()
    val userId by viewModel.userId.collectAsState(initial = null)
    val context = LocalContext.current


    var userPassword by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }

    // If user is already logged in, navigate to HomeScreen
//    LaunchedEffect(userId) {
//        if (userId != null) {
//            Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
//            navController.navigate(ScreenRoutes.HomeScreen) {
//                popUpTo(ScreenRoutes.LoginScreen) { inclusive = true } // Remove login from backstack
//            }
//        }
//    }

    // Show loading or error states
    when {
        state.value.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.value.Error != null -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = state.value.Error.toString())
            }
        }
        state.value.Data != null -> {
//            Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
//            navController.navigate(SubNavigation.HomeNavGraph) {
//                popUpTo(SubNavigation.AuthNavGraph) { inclusive = true } // Remove login from backstack
//            }
            if (userId == "Invalid User"){
                Toast.makeText(context, "Invalid Email or password", Toast.LENGTH_SHORT).show()

            }else{
                Toast.makeText(context, "Login Successfully", Toast.LENGTH_SHORT).show()
            navController.navigate(SubNavigation.HomeNavGraph) {
                popUpTo(SubNavigation.AuthNavGraph) { inclusive = true } // Remove login from backstack
            }
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.medical_app_logo),
                contentDescription = "Logo",
                modifier = Modifier.size(150.dp).clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = userEmail,
                onValueChange = { userEmail = it.trim() },
                label = { Text(text = "Email") },
                placeholder = { Text(text = "Enter Email") },
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
            )

            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = userPassword,
                onValueChange = { userPassword = it.trim() },
                label = { Text(text = "Password") },
                placeholder = { Text(text = "Enter Your Password") },
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp)
            )

            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    if (userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
                        viewModel.Login(email = userEmail, password = userPassword)
                    } else {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
                colors = ButtonDefaults.buttonColors()
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.height(30.dp))
            MultiColorText("Don't have an account ?", "SignUp", modifier = Modifier.clickable {
                navController.navigate(ScreenRoutes.SignUpScreen)
            })
        }
    }
}
