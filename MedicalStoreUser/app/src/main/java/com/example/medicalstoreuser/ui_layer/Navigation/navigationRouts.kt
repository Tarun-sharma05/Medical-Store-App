package com.example.medicalstoreuser.ui_layer.Navigation

import kotlinx.serialization.Serializable


sealed class ScreenRoutes {
    //Screen Routes
    @Serializable
     object LoginScreen

    @Serializable
    data object SignUpScreen

    @Serializable
    data object HomeScreen

    @Serializable
    data object ProductScreen

    @Serializable
    data object CategoryScreen

    @Serializable
    data object ProfileScreen

    @Serializable
    object CartScreen

    @Serializable
    object LogoutScreen

    @Serializable
    object AmbulanceScreen

    @Serializable
    object ProductDetailScreen

    @Serializable
    object EditProfileScreen
}

sealed class SubNavigation{
    //Graph Routes
    @Serializable
    data object AuthNavGraph : SubNavigation()

    @Serializable
    data object HomeNavGraph : SubNavigation()
}

sealed class Screen(val route: String) {
    object ProductDetailScreen : Screen("Product_Detail_Screen/{productID}") {
        fun createRoute(productID: String) = "Product_Detail_Screen/$productID"
    }
}


