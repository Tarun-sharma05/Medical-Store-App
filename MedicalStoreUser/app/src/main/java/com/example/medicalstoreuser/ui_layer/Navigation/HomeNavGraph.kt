package com.example.medicalstoreuser.ui_layer.Navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingBag
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.medicalstoreuser.Data.User_Pref.UserPreferenceManager
import com.example.medicalstoreuser.ui_layer.Screens.CategoriesScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.HomeScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.ProductScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.ProfileScreenUI


@Composable
fun HomeNavGraph(userPreferenceManager: UserPreferenceManager) {

    val navController = rememberNavController()
    var selected by remember { mutableStateOf(0) }

    val items = mutableListOf(
        bottomNavItem(name = "Home", icon = Icons.Filled.Home, route = ScreenRoutes.HomeScreen.toString()),
        bottomNavItem(name = "Categories", icon = Icons.Filled.Category, route = ScreenRoutes.CategoryScreen.toString()),
        bottomNavItem(name = "Product", icon = Icons.Filled.ShoppingBag, route = ScreenRoutes.ProductScreen.toString()),
        bottomNavItem(name = "Profile", icon = Icons.Filled.Person, route = ScreenRoutes.ProfileScreen.toString())
        )


    // Get the current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Determine whether to show the bottom navigation bar
    val showBottomBar = when (currentRoute) {
        ScreenRoutes.HomeScreen.toString(),
        ScreenRoutes.CategoryScreen.toString(),
        ScreenRoutes.ProductScreen.toString(),
        ScreenRoutes.ProfileScreen.toString() -> true
        else -> false
    }



    Box {
        Scaffold(
            bottomBar = {
                if(showBottomBar) {
                    NavigationBar(
                        containerColor = Color(0xFFFFFFFF),
                        modifier = Modifier
                            .height(70.dp)
                            .navigationBarsPadding()
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        val selectedIndex = items.indexOfFirst { it.name == currentRoute }

                        items.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = selectedIndex == index,
                                onClick = {
                                    selected = index
//                                when (selected) {
//                                    0 -> navController.navigate(ScreenRoutes.HomeScreen.route)
//                                    1 -> navController.navigate(ScreenRoutes.CategoryScreen.route)
//                                    2 -> navController.navigate(ScreenRoutes.ProductScreen.route)
//                                    3 -> navController.navigate(ScreenRoutes.ProfileScreen.route)
//                                }
                                    val route = when (index) {
                                        0 -> ScreenRoutes.HomeScreen
                                        1 -> ScreenRoutes.CategoryScreen
                                        2 -> ScreenRoutes.ProductScreen
                                        3 -> ScreenRoutes.ProfileScreen
                                        else -> ScreenRoutes.HomeScreen
                                    }

                                    navController.navigate(route) {
                                        popUpTo(ScreenRoutes.HomeScreen) { inclusive = false }
                                        launchSingleTop = true
                                    }
                                },
                                icon = {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Icon(
                                            item.icon, contentDescription = item.name,
                                            modifier = Modifier.height(24.dp),
                                        )

                                        Text(
                                            text = item.name,
                                            style = MaterialTheme.typography.labelSmall,
//                                         modifier = Modifier.padding(top = 0.dp),
                                            color = if (selected == index) Color.Black else Color.Gray
                                        )

                                    }
                                },

                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = Color.Black,
                                    unselectedIconColor = Color.Gray,
                                    indicatorColor = Color.Transparent
                                )
                            )

                        }
                    }
                }

            }
        ) { innerPadding ->


//            NavHost(
//                navController = navController,
//                startDestination = ScreenRoutes.HomeScreen,
//                modifier = Modifier.padding(innerPadding)
//            ) {
//                composable(ScreenRoutes.HomeScreen.toString()) {
//                    HomeScreenUI(navController = navController)
//                }
//                composable(ScreenRoutes.CategoryScreen.toString()) {
//                    CategoriesScreenUI(navController = navController)
//                }
//                composable(ScreenRoutes.ProductScreen.toString()) {
//                    ProductScreenUI(navController = navController)
//                }
//                composable(ScreenRoutes.ProfileScreen.toString()) {
//                    ProfileScreenUI(navController = navController, userPreferenceManager = userPreferenceManager)
//                }
//
//             }



        }


    }
}

data class bottomNavItem(
    val name: String,
    val icon: ImageVector,
    val route : String
)

