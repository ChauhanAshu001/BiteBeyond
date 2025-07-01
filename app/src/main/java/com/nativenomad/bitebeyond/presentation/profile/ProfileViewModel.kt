package com.nativenomad.bitebeyond.presentation.profile

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nativenomad.bitebeyond.domain.repository.ProfileDataRepository
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.bitebeyond.models.UserProfile
import com.nativenomad.bitebeyond.remote.FreeImageApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel@Inject constructor(private val application: Application,
                                          private val freeImageApi: FreeImageApi,
                                          private val profileDataRepository: ProfileDataRepository,
                                          private val databaseOpUseCases: DatabaseOpUseCases
):ViewModel() {


    val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    private val _name = profileDataRepository.name
    val name = _name.asStateFlow()

    private val _email = profileDataRepository.email
    val email = _email.asStateFlow()

    private val _gender = profileDataRepository.gender
    val gender = _gender.asStateFlow()

    private val _phoneNumber = profileDataRepository.phoneNumber
    val phoneNumber = _phoneNumber.asStateFlow()

    private val _imageUrl = profileDataRepository.imageUrl
    val imageUrl = _imageUrl.asStateFlow()

    private val _uiState = MutableStateFlow<ProfileEvent>(ProfileEvent.Nothing)
    val uiState = _uiState.asStateFlow()


    fun setName(name: String) {
        _name.value = name
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setGender(gender: String) {
        _gender.value = gender
    }

    fun setPhoneNumber(phone: String) {
        _phoneNumber.value = phone
    }

    fun setImageUri(imageUrl: Uri) {
        _imageUrl.value = imageUrl
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

    //below function reduce image size taken from local storage so that encoding to base 64 and uploading to freeimage.host time reduces
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


    fun uploadUserData(
        context: Application = application,
        uri: Uri? = _imageUrl.value,
        name: String = _name.value,
        email: String = _email.value,
        gender: String = _gender.value,
        phoneNumber: String = _phoneNumber.value
    ) {
        viewModelScope.launch(Dispatchers.IO) {

            if (uid != "null") {
                _uiState.value = ProfileEvent.Loading
                val bitmap = uri?.let { uriToBitmap(context, it) }
                if (bitmap != null) {
                    val resizedImage = resizeBitmap(bitmap)
                    val base64 = encodeImageToBase64(resizedImage)
                    val response = freeImageApi.uploadImage(
                        base64Image = base64
                    )
                    if (response.isSuccessful) {
                        val imageUrl1 = response.body()?.image?.url
                        val user = UserProfile(
                            name = name,
                            email = email,
                            imageUrl = imageUrl1,
                            gender = gender,
                            phoneNumber = phoneNumber
                        )
                        profileDataRepository.saveUserData(userId = uid, user = user)
                        _uiState.value = ProfileEvent.Success
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                application,
                                "Profile Updated Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }       //code inside withContext runs on the thread pool which is specified in parenthesis (Dispatchers.IO)
                    } else {
                        _uiState.value = ProfileEvent.Failed
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    Toast.makeText(application, "Please Login/SignIn First", Toast.LENGTH_SHORT)
                        .show()   //Toast shows only on main thread
                }

            }
        }

    }
}