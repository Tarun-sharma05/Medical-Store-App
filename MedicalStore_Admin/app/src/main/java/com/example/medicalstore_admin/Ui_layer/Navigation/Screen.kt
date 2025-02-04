package com.example.medicalstore_admin.Ui_layer.Navigation

sealed class Screen(val route: String) {
    object UpdateProduct : Screen("update_product/{productID}") {
        fun createRoute(productID: String) = "update_product/$productID"
    }

}
