package com.nativenomad.adminbitebeyond.presentation.enteringScreens.restaurantInfo

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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class RestaurantInfoViewModel@Inject constructor(
    private val restaurantDataRepo: RestaurantDataRepo,
    private val application: Application,
    private val freeImageApi: FreeImageApi
): ViewModel() {
    private val _restaurantName= MutableStateFlow("")
    val restaurantName=_restaurantName.asStateFlow()

    private val _imageUri= MutableStateFlow<Uri?>(null)
    val imageUri=_imageUri.asStateFlow()

    private val _restaurantDescription=MutableStateFlow("")
    val restaurantDescription=_restaurantDescription.asStateFlow()

    private val _navigateEvent=MutableSharedFlow<RestaurantInfoNavigationEvent>()
    val navigateEvent=_navigateEvent.asSharedFlow()

    fun setRestaurantName(name:String){
        _restaurantName.value=name
    }

    fun setImageUri(uri:Uri?) {
        _imageUri.value=uri

    }

    fun setRestaurantDescription(description:String){
        _restaurantDescription.value=description
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

    fun setRestaurantData(uri:Uri?= _imageUri.value){
        viewModelScope.launch(Dispatchers.IO) {
            val bitmap = uri?.let { uriToBitmap(application, it) }
            if (bitmap != null) {
                val resizedImage = resizeBitmap(bitmap)
                val base64 = encodeImageToBase64(resizedImage)
                val response = freeImageApi.uploadImage(
                    base64Image = base64
                )
                if (response.isSuccessful) {
                    val imageUrl1 = response.body()?.image?.url
                    restaurantDataRepo.setImageUrl(imageUrl1.toString())
                    restaurantDataRepo.setRestaurantName(restaurantName.value)
                    restaurantDataRepo.setRestaurantDescription(restaurantDescription.value)
                    //I will call restaurantDataRepo.saveRestaurantData() in LocationInfoViewModel
                }
            }
            else{
                withContext(Dispatchers.Main){ Toast.makeText(application,"Can't upload Photo",Toast.LENGTH_SHORT).show()}
            }
        }
    }

    fun onEvent(restaurantInfoNavigationEvent: RestaurantInfoNavigationEvent) {
        when (restaurantInfoNavigationEvent) {
            is RestaurantInfoNavigationEvent.NavigateToLocationInfoScreen -> {
                viewModelScope.launch {
                    _navigateEvent.emit(RestaurantInfoNavigationEvent.NavigateToLocationInfoScreen)
                }
            }
        }
    }

}