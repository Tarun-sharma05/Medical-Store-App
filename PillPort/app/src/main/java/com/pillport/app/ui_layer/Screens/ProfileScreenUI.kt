package com.pillport.app.ui_layer.Screens

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.pillport.app.Data.User_Pref.UserPreferenceManager
import com.pillport.app.ui_layer.Navigation.ScreenRoutes
import com.pillport.app.ui_layer.ViewModel.AppViewModel

@Composable
fun ProfileScreenUI(viewModel: AppViewModel = hiltViewModel(), navController: NavHostController, userPreferenceManager: UserPreferenceManager) {

    val userId by viewModel.userId.collectAsState(initial = null)
    val userState by viewModel.getSpecificUserState.collectAsState()
    val userData = userState.Data?.body()

    val context = LocalContext.current

    var address by remember { mutableStateOf("") }
    var block by remember { mutableStateOf("") }
    val date_of_account_creation by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val id by remember { mutableStateOf("") }
    var isApproved by remember { mutableStateOf("") }
    var level by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phone_number by remember { mutableStateOf("") }
    var pinCode by remember { mutableStateOf("") }
    val user_id by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        viewModel.getSpecificUser(userId.toString())
    }

//    LaunchedEffect(userData) {
//            name = userData.name
//            block = userData.block
//            date_of_account_creation = userData?.date_of_account_creation ?:
//            email = userData.email
//            level = userData.level.toString()
//            name = userData.name
//            password = userData.password
//            phone_number = userData.phone_number
//            pinCode = userData.pinCode
//    }

//      val userId by userPreferenceManager.userId.collectAsState(initial = null)

    // 🔹 Ensure that navigation does NOT happen instantly before userId is available
//    LaunchedEffect(userId) {
//        if (userId.isNullOrEmpty()) {
//            navController.navigate(SubNavigation.AuthNavGraph) { // 🔄 Navigate to Authentication Graph
//                popUpTo(SubNavigation.HomeNavGraph) { inclusive = true } // 🔄 Clear Home Graph
//            }
//        }
//    }





    when {
        userState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }


        }

        userState.Error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${userState.Error}", color = Color.Red)
            }
        }

//        userData != null -> {
//
//            Log.d("ProductDetailsScreenUI", "User Data: $userData")
//
//            UserProfileScreen(
//                name = userData?.name?: "N/A",
//                email = userData.email,
//                phoneNumber = userData.phone_number,
//                address = userData.address,
//                pinCode = userData.pinCode,
//                dateOfAccountCreation = date_of_account_creation,
//                isApproved = true,
//                onEditClick = {
//                    navController.navigate(ScreenRoutes.EditProfileScreen)
//                },
//                onLogoutClick = {
//                    viewModel.LogOut()
//                    navController.navigate(ScreenRoutes.LoginScreen)
//                }
//
//            )
//
//        }

        /////////////////////////////////////////////
        userData != null -> {
            Log.d("ProfileScreenUI", "User Data: $userData")

            // Use safe calls to prevent crashes
            UserProfileScreen(
                name = userData?.name ?: "N/A",
                email = userData?.email ?: "N/A",
                phoneNumber = userData?.phone_number ?: "N/A",
                address = userData?.address ?: "N/A",
                pinCode = userData?.pinCode ?: "N/A",
                dateOfAccountCreation = userData?.date_of_account_creation ?: "N/A",
                isApproved = true,
                onEditClick = {
                    navController.navigate(ScreenRoutes.EditProfileScreen)
                },
                onLogoutClick = {
                    viewModel.LogOut()
                    navController.navigate(ScreenRoutes.LoginScreen)
                }
            )
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No user data available", color = Color.Gray)
            }
        }




    }

//    Column(
//        modifier = Modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        Text(text = "Name: $userId")
//        Text(text = "Name: $name ")
//        Text(text = "Email: $email ")
//        Text(text = "Phone: $phone_number ")
//        Text(text = "Address: $address ")
//        Text(text = "PinCode: $pinCode ")
//        Text(text = "Level: $level ")
//        Button(onClick = {
//            viewModel.LogOut()
//        }) {
//            Text(text = "Log Out")
//        }
//    }
    


}




@Composable
fun UserProfileScreen(
    name: String,
    email: String,
    phoneNumber: String,
    address: String,
    pinCode: String,
    dateOfAccountCreation: String,
    isApproved: Boolean,
    onEditClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Profile Header
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.firstOrNull()?.toString()?.uppercase() ?: "U",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = name, style = MaterialTheme.typography.headlineMedium)
        Text(text = email, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        Spacer(modifier = Modifier.height(16.dp))

        // User Details Section
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileDetailItem("📞 Phone", phoneNumber)
                ProfileDetailItem("📍 Address", address)
                ProfileDetailItem("📌 Pin Code", pinCode)
                ProfileDetailItem("📅 Joined On", dateOfAccountCreation)
                ProfileDetailItem(
                    "✅ Approval Status",
                    if (isApproved) "Verified ✅" else "Pending ⏳"
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onEditClick) {
                Text(text = "Edit Profile ✏️")
            }

            OutlinedButton(onClick = onLogoutClick) {
                Text(text = "Logout 🔓")
            }
        }
    }
}

@Composable
fun ProfileDetailItem(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
    }
}
