package com.nativenomad.adminbitebeyond.presentation.OffersScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nativenomad.adminbitebeyond.R
import com.nativenomad.adminbitebeyond.presentation.OffersScreen.components.AppliedOfferCard
import com.nativenomad.adminbitebeyond.presentation.OffersScreen.components.OfferCard
import com.nativenomad.adminbitebeyond.presentation.OffersScreen.components.ShimmerAppliedOfferCard
import com.nativenomad.adminbitebeyond.presentation.OffersScreen.components.ShimmerOfferCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OffersScreen(offersViewModel: OffersViewModel= hiltViewModel()) {

    val allOffers = offersViewModel.allOffers.collectAsState()
    val myOffers = offersViewModel.myOffers.collectAsState()
    val uiState = offersViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Restaurant Offers",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id=R.drawable.offers),
                        contentDescription = "Offers Icon",
                        tint = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.emeraldGreen)
                )
            )
        }
    ){paddingValues->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)  /*It’s a PaddingValues object automatically provided by the Scaffold. It tells you how much space is occupied by the topBar, bottomBar, snackbarHost, etc., so you can avoid overlapping your content with those system elements.*/
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            Text(
                text = "Offers you can provide",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (uiState.value == OfferScreenEvents.Loading) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(2) {
                        ShimmerOfferCard()
                    }
                }
            } else {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 4.dp)
                ) {
                    items(allOffers.value) { offer ->
                        OfferCard(offer = offer, onAddClick = { offersViewModel.addOffer(it) })
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Applied Offers",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            if (uiState.value == OfferScreenEvents.Loading) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(5) {
                        ShimmerAppliedOfferCard()
                    }
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(myOffers.value) { offer ->
                        AppliedOfferCard(offer, onDeleteClick = { offersViewModel.deleteOffer(it) })
                    }
                }
            }
        }
    }

}
