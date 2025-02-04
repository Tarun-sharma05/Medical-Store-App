package com.example.medicalstore_admin.Network.Response

data class GetAllOrdersResponseItem(
    val date_of_order_creation: String,
    val id: Int,
    val isApproved: Int,
    val name: String,
    val order_id: String,
    val product_category: String,
    val product_id: String,
    val product_name: String,
    val product_price: Double,
    val quantity: Int,
    val total_price: Double,
    val user_id: String
)