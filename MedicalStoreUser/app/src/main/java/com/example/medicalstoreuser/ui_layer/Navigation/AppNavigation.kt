package com.example.medicalstoreuser.ui_layer.Navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.medicalstoreuser.Data.User_Pref.UserPreferenceManager
import com.example.medicalstoreuser.R
import com.example.medicalstoreuser.ui_layer.Screens.HomeScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.LoginScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.SignUpScreenUI


@Composable
fun AppNavigation (
   userPreferenceManager: UserPreferenceManager
){

    val token = remember{ mutableStateOf("") }
    val navController = rememberNavController()
    val userid by  userPreferenceManager.userId.collectAsState(initial = null)

    LaunchedEffect(key1 = Unit) {
        userPreferenceManager.userId.collect{
            if (it != null){
                token.value = it
            }
        }
    }

    val startScreen = remember(token.value){
        if (token.value.isNotEmpty()){
            HomeScreen
        }else{
            LoginScreen
        }
    }

    if (userid != null){
        HomeScreen
    }else{
        LoginScreen
    }
    var selected by remember{ mutableStateOf(0) }


    val bottom_Nav_Item = listOf(
        bottomNavItem(
            name = "Home",
            icon =  Icons.Default.Home
        ),
        bottomNavItem(
            name = "Login",
            icon = Icons.Default.Add
        ),
        bottomNavItem(
            name = "SignUp",
            icon = Icons.Filled.Done
        ),

    )


    Box{
        Scaffold(
            bottomBar = {
                NavigationBar {
                    bottom_Nav_Item.forEachIndexed{ index, bottomNavItem ->
                        NavigationBarItem(
                            selected = selected == index ,
                            onClick = { selected = index

                                when(selected){
                                    0 -> navController.navigate(HomeScreen)
                                    1 -> navController.navigate(LoginScreen)
                                    2 -> navController.navigate(SignUpScreen)
                                }
                                      },
                            icon = {Icon(bottomNavItem.icon, contentDescription = null)},
                        )
                        
                    }
                }
            }
        ){innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)){

                NavHost(navController = navController, startDestination = startScreen) {

                    composable<LoginScreen> {
                        LoginScreenUI(navController)
                    }

                    composable<SignUpScreen> {
                        SignUpScreenUI(navController)

                    }

                    composable<HomeScreen>{
                        HomeScreenUI(navController)
                    }
                }
            }

        }
    }





}

data class bottomNavItem(
    val name: String,
    val icon: ImageVector
)


