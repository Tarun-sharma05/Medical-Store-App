package com.example.medicalstore_admin.Network.Response

data class AddOrderResponse(
    val message: String,
    val order_id: String,
    val status: Int
)