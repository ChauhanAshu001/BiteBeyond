package com.nativenomad.adminbitebeyond.presentation.enteringScreens.locationInfo

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.adminbitebeyond.domain.repository.RestaurantDataRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LocationInfoViewModel@Inject constructor(
    private val restaurantDataRepo:RestaurantDataRepo,
    private val application: Application
):ViewModel() {
    private val _pincode=restaurantDataRepo.pincode
    val pincode=_pincode.asStateFlow()

    private val _state=restaurantDataRepo.state
    val state=_state.asStateFlow()

    private val _country=restaurantDataRepo.country
    val country=_country.asStateFlow()

    private val _address=restaurantDataRepo.address
    val address=_address.asStateFlow()

    private val _navigateEvent= MutableSharedFlow<LocationInfoNavigationEvent>()
    val navigateEvent=_navigateEvent.asSharedFlow()

    private val _uiState= MutableStateFlow<LocationInfoEvents>(LocationInfoEvents.Nothing)
    val uiState=_uiState.asStateFlow()

    fun setPincode(pincode:String){
        viewModelScope.launch {
            restaurantDataRepo.setPincode(pincode)
        }
    }
    fun setState(state:String){
        viewModelScope.launch {
            restaurantDataRepo.setState(state)
        }
    }
    fun setCountry(country:String){
        viewModelScope.launch {
            restaurantDataRepo.setCountry(country)
        }
    }
    fun setAddress(address:String){
        viewModelScope.launch {
            restaurantDataRepo.setAddress(address)
        }
    }

    fun onEvent(locationInfoNavigationEvent: LocationInfoNavigationEvent){
        when(locationInfoNavigationEvent){
            is LocationInfoNavigationEvent.NavigateToMenuAdd->{
                viewModelScope.launch{
                    _navigateEvent.emit(LocationInfoNavigationEvent.NavigateToMenuAdd)
                }
            }
        }
    }


    fun saveRestaurantData(){
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value= LocationInfoEvents.Loading

            restaurantDataRepo.calculateLatLong()
            try{
                restaurantDataRepo.saveRestaurantData()
                _uiState.value= LocationInfoEvents.Success
                withContext(Dispatchers.Main){ Toast.makeText(application,"Data Saved",Toast.LENGTH_SHORT).show()}
            }catch (e:Exception){
                _uiState.value= LocationInfoEvents.Error
            }
        }
    }
}