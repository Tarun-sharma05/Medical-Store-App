package com.example.medicalstoreuser.ui_layer.Navigation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.medicalstoreuser.Data.User_Pref.UserPreferenceManager
import com.example.medicalstoreuser.ui_layer.Screens.AmbulanceScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.CategoriesScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.EditProfileScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.HomeScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.LoginScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.ProductDetailsScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.ProductScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.ProfileScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.SignUpScreenUI


@Composable
fun AppNavigation ( userPreferenceManager: UserPreferenceManager){
    val navController = rememberNavController()
    val userid by userPreferenceManager.userId.collectAsState(initial = null)
    Log.d("TAG", "AppNavigation: userid = $userid")

    val startDestination = if (userid.isNullOrEmpty() || userid == "Bad Gateway" || userid == "Invalid User") {
        SubNavigation.AuthNavGraph
    } else {
        SubNavigation.HomeNavGraph
    }

    val items = listOf(
        bottomNavigationItem(name = "Home", icon = Icons.Filled.Home),
        bottomNavigationItem(name = "Ambulance", icon = Icons.Filled.DirectionsCar),
        bottomNavigationItem(name = "Categories", icon = Icons.Filled.Category),
        bottomNavigationItem(name = "Product", icon = Icons.Filled.ShoppingBag),
//        bottomNavigationItem(name = "Profile", icon = Icons.Filled.Person)
    )

    var selectedItemIndex by remember { mutableStateOf(0) }
    val currentDestinationAsState = navController.currentBackStackEntryAsState()
    val currentDestination = currentDestinationAsState.value?.destination?.route
    val shouldShowBottomBar = remember{ mutableStateOf(false) }


    LaunchedEffect(currentDestination) {
        shouldShowBottomBar.value = when (currentDestination) {
            ScreenRoutes.LoginScreen::class.qualifiedName, ScreenRoutes.SignUpScreen::class.qualifiedName -> false
            else -> true
        }
    }



    Scaffold (
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (shouldShowBottomBar.value) {
                NavigationBar() {
                    items.forEachIndexed { index, bottomNavigationItem ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index

                                when (selectedItemIndex) {
                                    0 -> navController.navigate(ScreenRoutes.HomeScreen)
                                    1-> navController.navigate(ScreenRoutes.AmbulanceScreen)
                                    2 -> navController.navigate(ScreenRoutes.CategoryScreen)
                                    3 -> navController.navigate(ScreenRoutes.ProductScreen)
//                                    3 -> navController.navigate(ScreenRoutes.ProfileScreen)
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = bottomNavigationItem.icon,
                                    contentDescription = bottomNavigationItem.name,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        )

                    }
                }
            }

        }
    ){innerPadding ->

     Box(modifier = Modifier.fillMaxSize().padding(bottom = if (shouldShowBottomBar.value) innerPadding.calculateBottomPadding() else 0.dp)){


         NavHost(navController = navController, startDestination = startDestination) {
             navigation<SubNavigation.AuthNavGraph>(startDestination = ScreenRoutes.LoginScreen) {
                 composable<ScreenRoutes.LoginScreen> {
                     LoginScreenUI(navController)
                 }
                 composable<ScreenRoutes.SignUpScreen> {
                     SignUpScreenUI(navController)
                 }
             }

             //////////////////////////////////////////////////////////////////
             navigation<SubNavigation.HomeNavGraph>(startDestination = ScreenRoutes.HomeScreen) {
                 composable<ScreenRoutes.HomeScreen> {
                     HomeScreenUI(navController = navController)
                 }
                 composable<ScreenRoutes.ProfileScreen> {
                     ProfileScreenUI(
                         navController = navController,
                         userPreferenceManager = userPreferenceManager
                     )
                 }
                 composable<ScreenRoutes.CategoryScreen> {
                     CategoriesScreenUI(navController = navController)
                 }
                 composable<ScreenRoutes.ProductScreen> {
                     ProductScreenUI(navController = navController)
                 }
                 composable<ScreenRoutes.AmbulanceScreen> {
                     AmbulanceScreenUI(navController = navController)
                 }

                 composable(Screen.ProductDetailScreen.route) { backStackEntry ->
                     val productID = backStackEntry.arguments?.getString("productID")
                     Log.d("Navigation", "Received productID: $productID")

                     ProductDetailsScreenUI(
                         productID = productID.toString(),
                         navController = navController,
                         userPreferenceManager = userPreferenceManager
                     )
                 }
                 composable<ScreenRoutes.EditProfileScreen> {
                     EditProfileScreenUI(navController = navController)
                 }


             }

         }

     }

    }


}

data class bottomNavigationItem(val name: String, val icon: ImageVector, /*val unselectedIcon: ImageVector*/)



