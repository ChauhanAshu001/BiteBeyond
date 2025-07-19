package com.nativenomad.adminbitebeyond.presentation.home

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.presentation.common.OrderCard
import com.nativenomad.adminbitebeyond.presentation.common.ShimmerOrderCard
import com.nativenomad.adminbitebeyond.utils.UtilityFunction.formatTimeStamp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeScreenViewModel: HomeScreenViewModel= hiltViewModel()) {
    val orders=homeScreenViewModel.orders.collectAsState()
    val uiState=homeScreenViewModel.uiState.collectAsState()

    val statusOptions = listOf("Pending", "Accepted", "Dispatched", "Delivered")
    var expanded by remember { mutableStateOf(false) }

    //code for red blinking dot
    val infiniteTransition = rememberInfiniteTransition(label = "blink")
    val alpha by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700),
            repeatMode = RepeatMode.Reverse
        ),
        label = "blinkAlpha"
    )

    LaunchedEffect(Unit) {
        homeScreenViewModel.fetchOrders()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Current Orders",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Home Icon",
                        tint = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.emeraldGreen)
                )
            )
        }
    ) { paddingValues ->
        if(uiState.value is HomeScreenEvents.Loading){
            LazyColumn(modifier = Modifier.padding(paddingValues)
                .padding(8.dp)) {
                items(5) {
                    ShimmerOrderCard()
                }
            }
        }
        else {
            LazyColumn(
                modifier = Modifier.padding(paddingValues)
                    .padding(8.dp)
            ) {
                items(orders.value) { order ->
                    // Header for each order
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        elevation = CardDefaults.cardElevation(6.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = colorResource(R.color.white)
                        )
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "\uD83D\uDC64 User ID: ${order.userId}",
                                style = MaterialTheme.typography.titleSmall,
                                color = colorResource(R.color.lightGreen)
                            )
                            Text(
                                "\uD83C\uDFE0  Address: ${order.address}",
                                style = MaterialTheme.typography.bodyMedium,

                                )
                            Text(
                                "\uD83D\uDD52 Ordered at: ${formatTimeStamp(order.timestamp)}",
                                style = MaterialTheme.typography.bodySmall
                            )
                            Text(
                                "\uD83D\uDCB0 Total: ₹${order.totalAmount}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                "\uD83D\uDE9A Status: ${order.status}",
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            // List of OrderItems
                            order.items.forEach { item ->
                                OrderCard(orderItem = item)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                //  Status dropdown
                                OutlinedButton(onClick = { expanded = true }) {
                                    Text("Status: ${order.status}")
                                }

                                //  Blinking dot
                                Box(
                                    modifier = Modifier
                                        .padding(start = 6.dp)
                                        .size(10.dp)
                                        .clip(CircleShape)
                                        .background(Color.Red.copy(alpha = alpha))
                                )

                                // Dropdown menu
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    statusOptions.forEach { option ->
                                        DropdownMenuItem(
                                            text = { Text(option) },
                                            onClick = {
                                                homeScreenViewModel.onStatusUpdate(
                                                    order.restaurantId,
                                                    order.userId,
                                                    order.orderId,
                                                    option
                                                )
                                                expanded = false
                                            }
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            } //lazy column of else ends here
        }  //else ends here
    }
}