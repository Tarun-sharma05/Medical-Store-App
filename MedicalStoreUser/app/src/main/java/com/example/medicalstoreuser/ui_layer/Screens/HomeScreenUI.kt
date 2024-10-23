package com.example.medicalstoreuser.ui_layer.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController


@Composable
fun HomeScreenUI(navController: NavController) {

    Column (modifier = Modifier.fillMaxSize()){
        Text(text = "Home Screen", textAlign = TextAlign.Center )
    }
}