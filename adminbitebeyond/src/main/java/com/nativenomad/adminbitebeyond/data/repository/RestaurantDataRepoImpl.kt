package com.nativenomad.adminbitebeyond.data.repository

import android.app.Application
import android.location.Geocoder
import com.nativenomad.adminbitebeyond.data.local.RestaurantDao
import java.util.Locale
import com.nativenomad.adminbitebeyond.domain.repository.RestaurantDataRepo
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.adminbitebeyond.models.RestaurantEntity
import com.nativenomad.adminbitebeyond.utils.UtilityFunction.getUid
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class RestaurantDataRepoImpl(private val application: Application,
                             private val restaurantDao: RestaurantDao,
                             private val databaseOpUseCases: DatabaseOpUseCases
): RestaurantDataRepo {


    private val _pincode= MutableStateFlow("")
    override val pincode= _pincode

    private val _country= MutableStateFlow("")
    override val country= _country

    private val _state= MutableStateFlow("")
    override val state=_state

    private val _address= MutableStateFlow("")
    override val address= _address

    private val _restaurantName= MutableStateFlow("")
    override val restaurantName=_restaurantName

    private val _imageUrl= MutableStateFlow("")
    override val imageUrl=_imageUrl

    private val _restaurantDescription= MutableStateFlow("")
    override val restaurantDescription=_restaurantDescription

    private val _lat=MutableStateFlow(0.0)
    override val lat=_lat

    private val _longitude=MutableStateFlow(0.0)
    override val longitude=_longitude

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val entity = restaurantDao.getRestaurant()

            entity?.let {
                _restaurantName.value = it.restaurantName
                _imageUrl.value = it.imageUrl
                _restaurantDescription.value = it.restaurantDescription
                _address.value = it.address
                _pincode.value = it.pincode
                _state.value = it.state
                _country.value = it.country
                _lat.value = it.latitude
                _longitude.value = it.longitude
            }
        }
    }

    override suspend fun setPincode(pincode: String) {
        _pincode.value=pincode
    }

    override suspend fun setCountry(country: String) {
        _country.value=country
    }

    override suspend fun setState(state: String) {
        _state.value=state
    }

    override suspend fun setAddress(address: String) {
        _address.value=address
    }

    override suspend fun setRestaurantName(name: String) {
        _restaurantName.value=name
    }

    override suspend fun setImageUrl(url: String) {
        _imageUrl.value=url
    }

    override suspend fun setRestaurantDescription(description: String) {
        _restaurantDescription.value=description

    }

    override suspend fun calculateLatLong(){
        try {
            val geocoder = Geocoder(application, Locale.getDefault())
            val fullAddress = "${_address.value}, ${_pincode.value}, ${_state.value}, ${_country.value}"

            val addressList = geocoder.getFromLocationName(fullAddress, 1)

            if (!addressList.isNullOrEmpty()) {
                val location = addressList[0]
                _lat.value = location.latitude
                _longitude.value = location.longitude
            } else {
                // log if location not found
                _lat.value = 0.0
                _longitude.value = 0.0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _lat.value = 0.0
            _longitude.value = 0.0
        }
    }
    override suspend fun saveRestaurantData() {
        val entity = RestaurantEntity(
            uid=getUid(),
            id = "admin_restaurant",
            restaurantName = _restaurantName.value,
            imageUrl = _imageUrl.value,
            restaurantDescription = _restaurantDescription.value,
            address = _address.value,
            pincode = _pincode.value,
            state = _state.value,
            country = _country.value,
            latitude = _lat.value,
            longitude = _longitude.value
        )

        restaurantDao.insertRestaurant(entity) //saving to room for profile section in admin app

        databaseOpUseCases.saveRestaurantData(entity) //saving to firebase realtime database for fetching in user's app

    }


}