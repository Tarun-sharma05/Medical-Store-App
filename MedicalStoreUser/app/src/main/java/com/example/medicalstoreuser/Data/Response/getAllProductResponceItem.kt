package com.example.medicalstoreuser.Data.Response

data class getAllProductResponceItem(
    val id: Int,
    val product_id: String,
    val product_name: String,
    val product_price: Double,
    val product_stock: Int,
    val product_category: String,
    val product_expiry_date: String

    )