package com.nativenomad.bitebeyond.data.repository

import android.net.Uri
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.nativenomad.bitebeyond.domain.repository.ProfileDataRepository
import com.nativenomad.bitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.bitebeyond.models.UserProfile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfileDataRepositoryImpl(
    private val databaseOpUseCases: DatabaseOpUseCases
):ProfileDataRepository {
    private val uid = FirebaseAuth.getInstance().currentUser?.uid.toString()

    private val _name= MutableStateFlow("")
    override val name= _name

    private val _email= MutableStateFlow("")
    override val email= _email

    private val _gender= MutableStateFlow("Male")
    override val gender= _gender

    private val _phoneNumber= MutableStateFlow("")
    override val phoneNumber= _phoneNumber

    private val _imageUrl= MutableStateFlow<Uri?>(null)
    override val imageUrl= _imageUrl

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    init{
        coroutineScope.launch {
            getUserData()
        }
    }

    override suspend fun getUserData() {
        databaseOpUseCases.getUserData(userId = uid).collect { userProfile ->
            _name.value = userProfile?.name?:""
            _email.value = userProfile?.email?:""
            _gender.value = userProfile?.gender?:""
            _phoneNumber.value = userProfile?.phoneNumber?:""
            _imageUrl.value = userProfile?.imageUrl?.toUri()
        }
    }

    override suspend fun saveUserData(userId: String, user: UserProfile) {
        databaseOpUseCases.saveUserData(userId=userId,user=user)

    }
}