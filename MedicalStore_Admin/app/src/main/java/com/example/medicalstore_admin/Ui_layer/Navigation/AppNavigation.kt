package com.example.medicalstore_admin.Ui_layer.Navigation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ProductionQuantityLimits
import androidx.compose.material.icons.filled.Reorder
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicalstore_admin.Ui_layer.Screens.AddProductScreenUI
import com.example.medicalstore_admin.Ui_layer.Screens.HomeScreenUI
import com.example.medicalstore_admin.Ui_layer.Screens.OrdersScreenUI
import com.example.medicalstore_admin.Ui_layer.Screens.Products
import com.example.medicalstore_admin.Ui_layer.Screens.Sell_history
import com.example.medicalstore_admin.Ui_layer.Screens.UpdateProductScreenUI


@Composable
fun AppNavigation(){
    val token = remember{ mutableStateOf("") }
    val navController = rememberNavController()



    var selected by remember{ mutableStateOf(0) }
    val items = listOf(
        bottomNavItem(name ="Home", icon = Icons.Filled.Home),
        bottomNavItem(name ="Orders", icon = Icons.Filled.Reorder),
        bottomNavItem(name ="Sell", icon = Icons.Filled.Sell),
        bottomNavItem(name= "Product", icon = Icons.Filled.ProductionQuantityLimits)
    )

//    val StartScreen = remember(token.value) {
//        if (token.value.isEmpty()) {
//            StartScreen
//        } else {
//            HomeScreen
//        }
//    }

    Box {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = Color(0xFFFFFFFF),            //(0xFF00FF00),
//                    tonalElevation = 0.dp,
//                    modifier = Modifier.height(95.dp),

                    modifier = Modifier
                        .height(70.dp) // Reduced height for a balanced design
                        .navigationBarsPadding() // Handles system navigation bar padding
                    ) {
                    items.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selected == index,
                            onClick = { selected = index
                                when (selected) {
                                    0 -> navController.navigate(HomeScreen)
                                    1 -> navController.navigate(OrdersScreen)
                                    2 -> navController.navigate(Sell_history)
                                    3 -> navController.navigate(Products)
                                }
                            },
                            icon = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Icon(
                                        imageVector = item.icon, contentDescription = item.name,
                                        modifier = Modifier.size(24.dp)
//                                         .padding(bottom = 2.dp)
                                    )

                                    Text(
                                        text = item.name,
                                        style = MaterialTheme.typography.labelSmall,
//                                         modifier = Modifier.padding(top = 0.dp),
                                        color = if (selected == index) Color.Black else Color.Gray
                                    )

                                }
                            },

//                            label = {
//                                     Text(text = item.name,
//                                         style = MaterialTheme.typography.labelSmall,
////                                         modifier = Modifier.padding(top = 0.dp),
//                                     color = if(selected == index) Color.Black else Color.Gray)
//                            },
                            colors = NavigationBarItemDefaults.colors(
//                                selectedIconColor = MaterialTheme.colorScheme.primary,
//                                indicatorColor = MaterialTheme.colorScheme.surface

                                selectedIconColor = Color.Black, // Icon color when selected
                                unselectedIconColor = Color.Gray, // Icon color when unselected
                                indicatorColor = Color.Transparent // Indicator background color

                            )

                         )
                    }
                }
            }
        ) {innerPadding ->
//            Box(
//
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(innerPadding)
//                    .height(30.dp)
//            ) {
                NavHost(navController = navController, startDestination = HomeScreen) {
                    composable<HomeScreen> {
                        HomeScreenUI(navController = navController)
                    }

                    composable<AddProductScreen> {
                        AddProductScreenUI(navController = navController)
                    }

                    composable<OrdersScreen> {
                        OrdersScreenUI(navController = navController)

                    }

                    composable<Sell_history> {
                        Sell_history(navController = navController)
                    }

                    composable<Products> {
                        Products(navController = navController)
                    }

//                    composable<updateProduct> {
//                        UpdateProductScreenUI(
//                            navController = navController,
//                        )
//                    }

                    composable(Screen.UpdateProduct.route) { backStackEntry ->
                        val productID = backStackEntry.arguments?.getString("productID")
                        Log.d("Navigation", "Received productID: $productID")

                        UpdateProductScreenUI(
                            productID = productID.toString(),
                            navController = navController
                        )
                    }


                }
//            }


        }

    }

}


data class bottomNavItem(
    val name: String,
    val icon: ImageVector
)