package com.nativenomad.bitebeyond.presentation.restaurantDetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.models.Offers
import com.nativenomad.bitebeyond.ui.theme.BiteBeyondTheme

@Composable
fun OfferCard(offer: Offers) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = offer.promoCode,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.lightOrange) // Optional: Green tone for bold emphasis
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = offer.offerDescription,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
private fun CardPreview(offer: Offers =Offers(offerDescription = "Flat 20rs off on order above 100", promoCode = "FLAT20") ){
    BiteBeyondTheme {
        OfferCard(offer)
    }
}
