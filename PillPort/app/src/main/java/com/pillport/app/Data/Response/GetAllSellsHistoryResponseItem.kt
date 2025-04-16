package com.pillport.app.Data.Response

data class GetAllSellsHistoryResponseItem(
    val date_of_order_creation: String,
    val date_of_sale: String,
    val id: Int,
    val name: String,
    val order_id: String,
    val product_category: String,
    val product_id: String,
    val product_name: String,
    val product_price: Double,
    val quantity: Int,
    val status: String,
    val total_price: Double,
    val user_id: String
)