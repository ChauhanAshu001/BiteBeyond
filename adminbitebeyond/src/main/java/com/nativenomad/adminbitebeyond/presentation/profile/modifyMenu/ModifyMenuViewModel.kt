package com.nativenomad.adminbitebeyond.presentation.profile.modifyMenu

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.adminbitebeyond.domain.repository.RestaurantMenuRepo
import com.nativenomad.adminbitebeyond.models.Menu
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
class ModifyMenuViewModel@Inject constructor(
    private val restaurantMenuRepo: RestaurantMenuRepo,
    private val application: Application,
    private val freeImageApi: FreeImageApi
):ViewModel() {
    private val _menu= restaurantMenuRepo.menu
    val menu=_menu.asStateFlow()

    private val _name= MutableStateFlow("")
    val name= _name.asStateFlow()

    private val _imageUri= MutableStateFlow<Uri?>(null)
    val imageUri=_imageUri.asStateFlow()

    private val _cost= MutableStateFlow("")
    val cost=_cost.asStateFlow()

    private val _foodCategory= MutableStateFlow("")
    val foodCategory=_foodCategory.asStateFlow()

    private val _navigateEvent= MutableSharedFlow<ModifyMenuNavigationEvent>()
    val navigateEvent=_navigateEvent.asSharedFlow()

    private val _uiState= MutableStateFlow<ModifyMenuEvents>(ModifyMenuEvents.Nothing)
    val uiState=_uiState.asStateFlow()

    fun setImageUri(uri: Uri){
        _imageUri.value=uri
    }
    fun setName(name:String){
        _name.value=name
    }
    fun setCost(cost:String){
        _cost.value=cost
    }
    fun setFoodCategory(foodCategory:String){
        _foodCategory.value=foodCategory
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

    fun addItem(context: Application=application,
                uri: Uri?= _imageUri.value,
                name:String=_name.value,
                cost:String=_cost.value,
                foodCategory:String=_foodCategory.value
    ) {
        viewModelScope.launch(Dispatchers.IO) {


            val bitmap = uri?.let { uriToBitmap(context, it) }
            if (bitmap != null) {
                _uiState.value= ModifyMenuEvents.Loading
                val resizedImage = resizeBitmap(bitmap)
                val base64 = encodeImageToBase64(resizedImage)
                val response = freeImageApi.uploadImage(
                    base64Image = base64
                )
                if (response.isSuccessful) {
                    val imageUrl1 = response.body()?.image?.url
                    val menuItem = Menu(
                        name = name,
                        imageUrl = imageUrl1,
                        cost = cost,
                        foodCategory = foodCategory
                    )
                    try{
                        restaurantMenuRepo.addItem(menuItem)
                        _uiState.value = ModifyMenuEvents.Success
                        withContext(Dispatchers.Main) {   //code inside withContext runs on the thread pool which is specified in parenthesis (Dispatchers.IO)
                            Toast.makeText(
                                application,
                                "Item added to menu Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    catch (e:Exception){
                        _uiState.value= ModifyMenuEvents.Error
                    }
                } else {
                    _uiState.value = ModifyMenuEvents.Error
                }

            }
            else{
                _uiState.value= ModifyMenuEvents.Error
            }
        }
        //clearing all fields again to let user add new item if he wants to
        _name.value=""
        _cost.value=""
        _imageUri.value=null
        _foodCategory.value=""
    }

    fun deleteItem(foodItem:Menu){
        viewModelScope.launch {
            restaurantMenuRepo.removeItem(foodItem)
        }
    }

    fun onEvent(modifyMenuNavigationEvent: ModifyMenuNavigationEvent){
        when(modifyMenuNavigationEvent){
            is ModifyMenuNavigationEvent.NavigateToProfileScreen->{
                viewModelScope.launch{
                    _navigateEvent.emit(ModifyMenuNavigationEvent.NavigateToProfileScreen)
                }
            }
        }
    }
}