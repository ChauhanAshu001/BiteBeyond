package com.nativenomad.adminbitebeyond.presentation.profile

import androidx.lifecycle.ViewModel
import com.nativenomad.adminbitebeyond.domain.repository.RestaurantDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(
    private val restaurantDataRepo: RestaurantDataRepo
): ViewModel() {
    private val _restaurantName=restaurantDataRepo.restaurantName
    val restaurantName=_restaurantName.asStateFlow()

    private val _restaurantDescription=restaurantDataRepo.restaurantDescription
    val restaurantDescription=_restaurantDescription.asStateFlow()
}