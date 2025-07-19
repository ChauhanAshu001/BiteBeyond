package com.nativenomad.bitebeyond.presentation.profile.components.pastOrders

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.models.OrderItem
import com.nativenomad.bitebeyond.presentation.home.components.shimmerEffect

@Composable
fun OrderCard(orderItem: OrderItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(text = "🍽 ${orderItem.name}",
                style = MaterialTheme.typography.titleMedium,
                color= colorResource(R.color.lightOrange)
            )
            Text(
                text = "💵 Cost: ₹${orderItem.price}",
                style = MaterialTheme.typography.bodyMedium,
                color= colorResource(R.color.black)
            )
            Text(
                text = "🔢 Quantity: ${orderItem.quantity}",
                style = MaterialTheme.typography.bodyMedium,
                color= colorResource(R.color.black)
            )
        }
    }
}


@Composable
fun ShimmerOrderCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(60.dp)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(60.dp)
                    .shimmerEffect()
            )
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .width(60.dp)
                    .shimmerEffect()
            )
        }
    }
}