package com.example.medicalstore_admin.Ui_layer.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicalstore_admin.Ui_layer.Screens.AddProductScreenUI
import com.example.medicalstore_admin.Ui_layer.Screens.HomeScreenUI

@Composable
fun AppNavigation(){

    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = HomeScreen) {
      composable<HomeScreen>{
          HomeScreenUI(navHostController = navHostController)
      }

        composable<AddProductScreen>{
            AddProductScreenUI(navHostController = navHostController)
        }

    }
}