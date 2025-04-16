package com.example.medicalstoreuser.Data.Response

data class AddOrderResponse(
    val message: String,
    val order_id: String,
    val status: Int
)