package com.nativenomad.bitebeyond.models

data class Order (
    val orderId: String = "",
    val restaurantId: String = "",
    val items: List<OrderItem> = emptyList(),
    val totalAmount: Int = 0,
    val discount: Int = 0,
    val finalAmount: Int = 0,
    val address: String = "",
    val userId: String = "",
    val status: String = "Pending",
    val timestamp: Long = System.currentTimeMillis()
)