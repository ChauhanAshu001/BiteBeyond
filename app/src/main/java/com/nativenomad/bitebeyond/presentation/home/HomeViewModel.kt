package com.nativenomad.bitebeyond.presentation.home

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.bitebeyond.domain.usecases.permissions.PermissionUseCases
import com.nativenomad.bitebeyond.models.Category
import com.nativenomad.bitebeyond.models.Restaurants
import com.nativenomad.bitebeyond.presentation.signIn_signUp.signUp.SignUpEvent
import com.nativenomad.bitebeyond.utils.CalculateDistanceClass
import com.nativenomad.bitebeyond.utils.GetUserLocationNameClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val application: Application,
    private val databaseOpUseCases: DatabaseOpUseCases,
    private val permissionUseCases: PermissionUseCases,
    private val calculateDistanceClass: CalculateDistanceClass,
    private val userLocationNameClass: GetUserLocationNameClass
) : ViewModel() {


    private val _categories = mutableStateListOf<Category>()
    val categories: SnapshotStateList<Category> = _categories

    private val _restaurants = mutableStateListOf<Restaurants>()
    val restaurants: SnapshotStateList<Restaurants> = _restaurants


    private val _uiState= MutableStateFlow<SignUpEvent>(SignUpEvent.Nothing)
    val uiState=_uiState.asStateFlow()

    private val _locationName = MutableStateFlow("Fetching location...")
    val locationName = _locationName.asStateFlow()

    init {
        observeCategories()
        observeRestaurantsWithDistance()
    }

    private fun observeCategories() {
        viewModelScope.launch {
            _uiState.value= SignUpEvent.Loading
            databaseOpUseCases.getCategories().collectLatest { list ->
                _categories.clear()
                _categories.addAll(list)
                checkIfDataLoaded()

            }
        }
    }

    private fun observeRestaurantsWithDistance() {
        viewModelScope.launch {

            val userLocation = permissionUseCases.getUserLocation()
            _uiState.value= SignUpEvent.Loading
            databaseOpUseCases.getRestaurants().collectLatest { list ->
                val updated = list.map { restaurant ->
                    val distance = if (userLocation != null)
                        calculateDistanceClass.calculateDistance(
                            userLocation = userLocation,
                            lat = restaurant.latitude,
                            lng = restaurant.longitude
                        )
                    else 0.0
                    restaurant.copy(distance = distance.toString())
                }

                _restaurants.clear()
                _restaurants.addAll(updated)
                checkIfDataLoaded()
            }

        }
    }

    private fun checkIfDataLoaded() {
        if (_categories.size!=0 && _restaurants.size!=0) {
            _uiState.value = SignUpEvent.Success
        }
    }

    fun fetchUserLocationAndName() {
        viewModelScope.launch {
            val location = permissionUseCases.getUserLocation() //this will give me lat and lng of user
            if (location != null) {
                val name = userLocationNameClass.getUserLocationName(context=application, location) //this will give the user's location name according to lat and lng
                _locationName.value = name
            } else {
                _locationName.value = "Location not found"
            }
        }
    }


}
