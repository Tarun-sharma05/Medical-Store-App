package com.pillport.app.ui_layer.Screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.pillport.app.ui_layer.ViewModel.AppViewModel

@Composable
fun CartScreenUI(viewModel: AppViewModel = hiltViewModel(), navController: NavController) {

    Text(text = "Cart Screen")

}