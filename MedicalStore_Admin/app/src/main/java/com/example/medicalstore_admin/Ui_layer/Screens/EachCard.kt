package com.example.medicalstore_admin.Ui_layer.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

//@Composable
//fun EachCard(
//    userName: String,
//    user_id: String,
//    userEmail: String,
//    userPhone: String,
//    userAddress: String,
//    userPinCode: String,
//    userDate_of_account_creation: String,
//    userLevel: Int,
//    navHostController: NavHostController,
//    isApproved: Int,
//    onClickApprove: () -> Unit,
//){
//    Card (
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        elevation = CardDefaults.cardElevation(4.dp),
//        shape = RoundedCornerShape(8.dp)
//    ){
//        Column(
//            modifier = Modifier
//                .padding(16.dp)
//                .fillMaxWidth()
//        ) {
//            Column(
//                modifier = Modifier.fillMaxWidth()
//                    .padding(bottom = 8.dp),
//            ) {
//                Text(text = "Name: $userName", style = MaterialTheme.typography.titleLarge)
//                Text(text = "User ID: $user_id", style = MaterialTheme.typography.bodyMedium)
//                Text(text = "Email: $userEmail", style = MaterialTheme.typography.bodyMedium)
//                Text(text = "Phone: $userPhone", style = MaterialTheme.typography.bodyMedium)
//                Text(text = "Address: $userAddress", style = MaterialTheme.typography.bodyMedium)
//                Text(text = "Pin Code: $userPinCode", style = MaterialTheme.typography.bodyMedium)
//                Text(
//                    text = "Account Creation Date: $userDate_of_account_creation",
//                    style = MaterialTheme.typography.bodySmall
//                )
//                Text(text = "User Level: $userLevel", style = MaterialTheme.typography.bodySmall)
//
//            }
//
//             if (isApproved == 0) {
//                 Row(
//                     modifier = Modifier.fillMaxSize(),
//                     horizontalArrangement = Arrangement.SpaceBetween
//                 ) {
//                     Button(
//                         onClick = { onClickApprove() },
//                         colors = ButtonDefaults.buttonColors(colorScheme.primary),
//                     ) {
//                         Text(text = "Approve", fontSize = 14.sp)
//                     }
//
//                     Spacer(modifier = Modifier.height(8.dp))
//
//                     Button(
//                         onClick = {
////                        navHostController.navigate(
////                            Routs.UserDetailsData(
////                                userName = userName,
////                                userId = userId,
////                                userEmail = userEmail,
////                                userPhone = userPhone,
////                                userAddress = userAddress,
////                                userPinCode = userPinCode,
////                                userDate_of_account_creation = userDate_of_account_creation,
////                                userLevel = userLevel
////                            )
////                        )
//                         },
//                         colors = ButtonDefaults.buttonColors(
//                             containerColor = MaterialTheme.colorScheme.secondary
//                         )
//                     ) {
//                         Text(text = "Delete", fontSize = 14.sp)
//                     }
//
//
//                 }
//             }
//             else{
//                 Button(
//                     onClick = {onClickApprove()},
//                     colors = ButtonDefaults.buttonColors(
//                         containerColor = MaterialTheme.colorScheme.secondary
//                     )
//                 ) {
//                     Text(text = "Block", fontSize = 14.sp)
//                 }
//
//             }
//        }
//    }
//
//}