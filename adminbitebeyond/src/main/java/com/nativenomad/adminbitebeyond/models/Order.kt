package com.nativenomad.adminbitebeyond.models

data class Order(
    val userId: String = "",
    val restaurantId: String = "",
    val restaurantName: String = "",
    val items: List<OrderItem> = emptyList(),
    val totalAmount: Int = 0,
    val address: String = "",
    val timestamp: Long = 0,
    val status:String="",
    val orderId:String=""
)