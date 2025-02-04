package com.example.medicalstore_admin.Network.Response

data class GetSpecificProductResponse(
    val id: Int,
    val product_category: String,
    val product_company: String,
    val product_description: String,
    val product_expiry_date: String,
    val product_id: String,
    val product_image: String,
    var product_name: String,
    val product_price: Double,
    val product_stock: Int
)