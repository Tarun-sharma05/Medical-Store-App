package com.example.medicalstoreuser.Data.Response

data class CreateOrderResponse(
    val message: String,
    val order_id: String,
    val status: Int
)