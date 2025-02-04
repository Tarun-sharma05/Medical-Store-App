package com.example.medicalstore_admin.Ui_layer.Screens

import android.graphics.drawable.Icon
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
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
fun HomeScreenUI(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {
    val scrollBehaviour = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val state = viewModel.updateUserState.collectAsState()
    val getAllUserState = viewModel.getAllUserState.collectAsState()
    val userData = getAllUserState.value.Data?.body()?: emptyList()
    val context = LocalContext.current


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
            Log.d("TAG", "HomeScreenUI: ${state.value.Data?.body()?.message}")
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
                            text = "Admin",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )


                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(imageVector = Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },



                scrollBehavior = scrollBehaviour
            )

        }
    ) { innerPadding ->

        when {
            getAllUserState.value.Loading -> {
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

            getAllUserState.value.Error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.value.Error.toString())
                }
            }

            getAllUserState.value.Data != null -> {



//                AllCardLazyColumn(viewModel = viewModel, navHostController = navHostController)


                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentPadding = PaddingValues(bottom = innerPadding.calculateBottomPadding() + 30.dp)
                    ) {

                        items(userData) { userDta ->
                            EachCard(
                                userName = userDta.name,
                                user_id = userDta.user_id,
                                userEmail = userDta.email,
                                userPhone = userDta.phone_number,
                                userAddress = userDta.address,
                                userPinCode = userDta.pinCode,
                                userDate_of_account_creation = userDta.date_of_account_creation,
                                userLevel = userDta.level,
                                navController = navController,
                                isApproved = userDta.isApproved,
                                onClickApprove = {
                                    viewModel.approveUser(
                                        user_id = userDta.user_id,
                                        isApproved = 1
                                    )
                                }
                            )
                        }


                    }

                }
            }




    }
}


//@Composable
//fun AllCardLazyColumn(viewModel: AppViewModel , navHostController: NavHostController){
//    val response = viewModel.getAllUserState.collectAsState()
//    val data = response.value.Data?.body() ?: emptyList()
//
//    LazyColumn(
//        modifier = Modifier
//            .padding()
//            .fillMaxSize()
//    ){
//        items(data){ userDta ->
//            EachCard(
//                userName = userDta.name,
//                user_id = userDta.user_id,
//                userEmail = userDta.email,
//                userPhone = userDta.phone_number,
//                userAddress = userDta.address,
//                userPinCode = userDta.pinCode,
//                userDate_of_account_creation = userDta.date_of_account_creation,
//                userLevel = userDta.level,
//                navHostController = navHostController,
//                isApproved = userDta.isApproved,
//                onClickApprove ={
//                    viewModel.approveUser(
//                        user_id = userDta.user_id,
//                        isApproved = 1
//                    )
//                }
//            )
//        }
//    }
//}



@Composable
fun EachCard(
    userName: String,
    user_id: String,
    userEmail: String,
    userPhone: String,
    userAddress: String,
    userPinCode: String,
    userDate_of_account_creation: String,
    userLevel: Int,
    navController: NavController,
    isApproved: Int,
    onClickApprove: () -> Unit,
){
    Box(modifier = Modifier.padding()) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = CardDefaults.cardElevation(4.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = 8.dp),
                ) {
                    Text(text = "Name: $userName", style = MaterialTheme.typography.titleLarge)
                    Text(text = "User ID: $user_id", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Email: $userEmail", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Phone: $userPhone", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        text = "Address: $userAddress",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Pin Code: $userPinCode",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "Account Creation Date: $userDate_of_account_creation",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Text(
                        text = "User Level: $userLevel",
                        style = MaterialTheme.typography.bodySmall
                    )

                }

                if (isApproved == 0) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { onClickApprove() },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        ) {

                            Text(text = "Approve", fontSize = 14.sp)
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            onClick = {
//                        navHostController.navigate(
//                            Routs.UserDetailsData(
//                                userName = userName,
//                                userId = userId,
//                                userEmail = userEmail,
//                                userPhone = userPhone,
//                                userAddress = userAddress,
//                                userPinCode = userPinCode,
//                                userDate_of_account_creation = userDate_of_account_creation,
//                                userLevel = userLevel
//                            )
//                        )
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorScheme.secondary
                            )
                        ) {
                            Text(text = "Delete", fontSize = 14.sp)
                        }


                    }
                } else {
                    Button(
                        onClick = { onClickApprove() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorScheme.secondary
                        )
                    ) {
                        Text(text = "Block", fontSize = 14.sp)
                    }

                }
            }
        }

    }




}

@Composable
fun SpaceCard(){

        Box(){
        Card (modifier = Modifier.fillMaxSize().padding(10.dp)){
            Text(text = " ")
        }
    }
}