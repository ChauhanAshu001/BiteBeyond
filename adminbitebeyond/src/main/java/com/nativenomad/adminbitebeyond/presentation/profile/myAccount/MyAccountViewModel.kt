package com.nativenomad.adminbitebeyond.presentation.profile.myAccount

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.adminbitebeyond.domain.repository.RestaurantDataRepo
import com.nativenomad.admnibitebeyond.remote.FreeImageApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class MyAccountViewModel@Inject constructor(
    private val restaurantDataRepo: RestaurantDataRepo,
    private val application: Application,
    private val freeImageApi: FreeImageApi
): ViewModel() {
    private val _restaurantName=restaurantDataRepo.restaurantName
    val restaurantName=_restaurantName.asStateFlow()

    private val _imageUrl=restaurantDataRepo.imageUrl
    val imageUrl=_imageUrl.asStateFlow()

    private val _imageUri= MutableStateFlow<Uri?>(null)
    val imageUri=_imageUri.asStateFlow()

    private val _restaurantDescription=restaurantDataRepo.restaurantDescription
    val restaurantDescription=_restaurantDescription.asStateFlow()

    private val _address=restaurantDataRepo.address
    val address=_address.asStateFlow()

    private val _pincode=restaurantDataRepo.pincode
    val pincode=_pincode.asStateFlow()

    private val _state=restaurantDataRepo.state
    val state=_state.asStateFlow()

    private val _country=restaurantDataRepo.country
    val country=_country.asStateFlow()

    private val _uiState= MutableStateFlow<MyAccountEvents>(MyAccountEvents.Nothing)
    val uiState=_uiState.asStateFlow()

    fun setRestaurantName(name:String){
        viewModelScope.launch {
            restaurantDataRepo.setRestaurantName(name)
        }
    }

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
    fun setImageUrl(url:String){
        viewModelScope.launch {
            restaurantDataRepo.setImageUrl(url)
        }
    }

    fun setImageUri(uri:Uri){
        _imageUri.value=uri
    }

    fun setRestaurantDescription(description:String){
        viewModelScope.launch {
            restaurantDataRepo.setRestaurantDescription(description)
        }
    }

    private fun uriToBitmap(context: Application = application, uri: Uri): Bitmap? {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            return BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            Log.e("ProfilePhotoViewModel", "Error converting URI to Bitmap: ${e.message}")
            return null
        }
    }

    private fun encodeImageToBase64(bitmap: Bitmap): String {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        return Base64.encodeToString(byteArray, Base64.DEFAULT)
    }

    private fun resizeBitmap(bitmap: Bitmap, maxSize: Int = 1024): Bitmap {
        val ratio = minOf(
            maxSize.toFloat() / bitmap.width,
            maxSize.toFloat() / bitmap.height
        )
        return Bitmap.createScaledBitmap(
            bitmap,
            (bitmap.width * ratio).toInt(),
            (bitmap.height * ratio).toInt(),
            true
        )
    }

    fun saveProfile(){
        viewModelScope.launch(Dispatchers.IO) {


            val bitmap = imageUri.value?.let { uriToBitmap(application, it) }
            if (bitmap != null) {
                _uiState.value = MyAccountEvents.Loading
                val resizedImage = resizeBitmap(bitmap)
                val base64 = encodeImageToBase64(resizedImage)
                val response = freeImageApi.uploadImage(
                    base64Image = base64
                )
                if (response.isSuccessful) {
                    val imageUrl1 = response.body()?.image?.url
                    setImageUrl(imageUrl1.toString())
                    restaurantDataRepo.calculateLatLong()
                    try {
                        restaurantDataRepo.saveRestaurantData()
                        _uiState.value= MyAccountEvents.Success
                        withContext(Dispatchers.Main) {   //code inside withContext runs on the thread pool which is specified in parenthesis (Dispatchers.IO)
                            Toast.makeText(
                                application,
                                "Profile Saved",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }catch (e:Exception){
                        _uiState.value= MyAccountEvents.Error
                    }
                }
                else {
                    _uiState.value = MyAccountEvents.Error
                }
            }
            else if(bitmap==null && imageUri.value!=null){
                //if user changed the image of restaurant but it can't be converted to bitmap then it's an error
                _uiState.value = MyAccountEvents.Error
            }
            else{
                //maybe user's kept the image he used at entering screen and only changed the data in text fields so this else if block will run
                restaurantDataRepo.calculateLatLong()
                try {
                    restaurantDataRepo.saveRestaurantData()
                    _uiState.value= MyAccountEvents.Success
                    withContext(Dispatchers.Main) {   //code inside withContext runs on the thread pool which is specified in parenthesis (Dispatchers.IO)
                        Toast.makeText(
                            application,
                            "Profile Saved",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }catch (e:Exception){
                    _uiState.value= MyAccountEvents.Error
                }
            }
        }
    }


}