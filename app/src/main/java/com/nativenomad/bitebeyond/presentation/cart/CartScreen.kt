package com.nativenomad.bitebeyond.presentation.cart

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.nativenomad.bitebeyond.PaymentActivity
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.presentation.cart.components.CartFoodItemCard
import com.nativenomad.bitebeyond.presentation.navgraph.Routes
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun CartScreen(cartViewModel: CartViewModel = hiltViewModel(),
               navController: NavController
               ) {
    val cartItems = cartViewModel.cartItems.collectAsState()
    val address = cartViewModel.address.collectAsState()
    val promoCode = cartViewModel.promoCode.collectAsState()
    val discountAmount = cartViewModel.discountAmount.collectAsState()
    val total = cartViewModel.total.collectAsState()
    val finalTotal = cartViewModel.finalTotal.collectAsState()

    val isAddressSaved = cartViewModel.addressChanged.collectAsState()
    val isPromoApplied = cartViewModel.promoApplied.collectAsState()
    val errorMessage = remember { mutableStateOf("") }

    LaunchedEffect(isPromoApplied.value) {
        errorMessage.value = if (!isPromoApplied.value) "Enter a valid Promo Code" else ""
    }

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Toast.makeText(context, "Payment successful", Toast.LENGTH_SHORT).show()
            cartViewModel.saveOrdersAfterPayment()
//            navController.navigate(Routes.OrderSuccess.route)
        } else {
            Toast.makeText(context, "Payment failed or cancelled", Toast.LENGTH_SHORT).show()
        }
    }

    if (cartItems.value.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 150.dp, start = 16.dp, end = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.order_empty),
                contentDescription = "Empty Cart"
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text("Ouch! Hungry", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text("Seems like you have not ordered any food yet", color = Color.Gray)
        }
    } else {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp, start = 16.dp, end = 16.dp,bottom=93.dp)
        ) {
            item {
                Text("Delivery Address", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = address.value,
                        onValueChange = {
                            cartViewModel.setAddress(it)
                            cartViewModel.saveAddress(false)
                        },
                        label = { Text("Address") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (isAddressSaved.value) Color.Green else Color.Gray,
                            unfocusedBorderColor = if (isAddressSaved.value) Color.Green else Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { cartViewModel.saveAddress(true) },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lightOrange))
                    ) {
                        Text("Save")
                    }
                }

                Spacer(Modifier.height(24.dp))
                Text("Apply Promo Code", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    OutlinedTextField(
                        value = promoCode.value,
                        onValueChange = { cartViewModel.setPromoCode(it) },
                        label = { Text("Promo Code") },
                        modifier = Modifier.weight(1f),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = if (isPromoApplied.value) Color.Green else Color.Gray,
                            unfocusedBorderColor = if (isPromoApplied.value) Color.Green else Color.Gray
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = { cartViewModel.applyPromoCode() },
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lightOrange))
                    ) {
                        Text("Apply")
                    }
                }

                if (errorMessage.value.isNotEmpty()) {
                    Text(
                        text = errorMessage.value,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp, top = 4.dp)
                    )
                }

                Spacer(Modifier.height(24.dp))
                Text("Your Items", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
            }

            items(cartItems.value.entries.toList()) { (food, qty) ->
                CartFoodItemCard(food = food, quantity = qty, onEvent = cartViewModel::onEvent)
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                Spacer(Modifier.height(12.dp))
                Text("Payment Summary", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text("Total Order Value: ₹${total.value}")
                    Text("Discount: -₹${discountAmount.value}", color = Color(0xFF2E7D32)) // dark green
                    Text("Final Total: ₹${finalTotal.value}", fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(16.dp))
                Button(
                    onClick = {
                        if(cartViewModel.checkLoginOrNot()){
                            val intent = Intent(context, PaymentActivity::class.java)
                            intent.putExtra("amount", finalTotal.value)
                            launcher.launch(intent)
                        }
                        else{
                            Toast.makeText(context,"Login Before Ordering",Toast.LENGTH_SHORT).show()
                            navController.navigate(Routes.SignUpNavigation.route)
                        }


                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.lightOrange))
                ) {
                    Text("Order Now")
                }

                Spacer(Modifier.height(32.dp))
            }
        }
    }
}
