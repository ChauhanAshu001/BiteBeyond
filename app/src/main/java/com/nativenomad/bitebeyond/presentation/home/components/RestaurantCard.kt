package com.nativenomad.bitebeyond.presentation.home.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nativenomad.bitebeyond.R
import com.nativenomad.bitebeyond.models.Restaurants



fun Modifier.shimmerEffect()=composed{
    val transition = rememberInfiniteTransition()
    val alpha=transition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000),
            repeatMode = RepeatMode.Reverse
        )
    ).value
    background(color= colorResource(R.color.shimmer).copy(alpha=alpha))
}

@Composable
fun RestaurantCard(restaurant: Restaurants, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)

    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                model = restaurant.imageUrl,
                contentDescription = restaurant.restaurantName,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = restaurant.restaurantName,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1
            )

            Text(
                text = restaurant.distance,
                color = colorResource(id = R.color.lightOrange),
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = "⭐ ${restaurant.rating}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
fun ShimmerRestaurantCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Box(
                modifier = Modifier
                    .height(140.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth(0.7f)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.3f)
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Box(
                modifier = Modifier
                    .height(12.dp)
                    .fillMaxWidth(0.2f)
                    .shimmerEffect()
            )
        }
    }
}

