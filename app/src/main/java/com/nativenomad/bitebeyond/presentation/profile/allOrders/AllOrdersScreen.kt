package com.nativenomad.bitebeyond.presentation.profile.allOrders

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.profile.components.pastOrders.OrderCard
import com.nativenomad.bitebeyond.utils.FormatTimeStamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllOrdersScreen(pastOrderViewModel: PastOrderViewModel= hiltViewModel()) {
    val orders=pastOrderViewModel.orders.collectAsState()

    LaunchedEffect(Unit) {
        pastOrderViewModel.fetchOrders()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "All Orders",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = "All Orders Icon",
                        tint = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.darkOrange)
                )
            )
        }
    ) { paddingValues ->
        if (orders.value.isEmpty()) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 150.dp, start = 16.dp, end = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.order_empty),
                    contentDescription = "No past orders"
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("Ouch! Hungry", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                Text("Seems like you have not ordered any food yet", color = Color.Gray)
            }
        }
        else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                items(orders.value) { order ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.white)
                        )
                    ) {
                        val statusEmoji = when (order.status.lowercase()) {
                            "pending" -> "\uD83D\uDD52"     // 🕒 Clock (Pending)
                            "accepted" -> "\u2705"          // ✅ Check Mark (Accepted)
                            "dispatched" -> "\uD83D\uDE9A"  // 🚚 Delivery Truck (Dispatched)
                            "delivered" -> "\uD83D\uDCE6"   // 📦 Package (Delivered)
                            else -> "\u2753"                // ❓ Question Mark (Unknown)
                        }
                        Text(
                            text = "\uD83D\uDC64 User ID: ${order.userId}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 4.dp),
                            color= colorResource(R.color.lightOrange)
                        )
                        Text(
                            text = "\uD83C\uDFE0 Address: ${order.address}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 4.dp),
                            color= colorResource(R.color.lightOrange)
                        )
                        Text(
                            text = "\uD83E\uDDFE Order ID: ${order.orderId}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 4.dp),
                            color= colorResource(R.color.lightOrange)
                        )
                        Text(
                            text = "$statusEmoji Order Status: ${order.status}",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 4.dp),
                            color= Color.Red
                        )
                        Text(
                            text = "Ordered at: ${FormatTimeStamp().formatTimeStamp(order.timestamp)}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )

                        order.items.forEach { item ->
                            OrderCard(orderItem = item)
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))
                }
            }
        }
    }

}