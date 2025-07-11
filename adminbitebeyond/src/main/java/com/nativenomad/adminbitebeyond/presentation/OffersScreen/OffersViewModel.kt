package com.nativenomad.adminbitebeyond.presentation.OffersScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativenomad.adminbitebeyond.domain.usecases.databaseOp.DatabaseOpUseCases
import com.nativenomad.adminbitebeyond.models.Offers
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val databaseOpUseCases: DatabaseOpUseCases
): ViewModel() {



    private val _allOffers = MutableStateFlow<List<Offers>>(emptyList())
    val allOffers = _allOffers.asStateFlow()

    private val _myOffers = MutableStateFlow<List<Offers>>(emptyList())
    val myOffers = _myOffers.asStateFlow()

    private val _uiState = MutableStateFlow<OfferScreenEvents>(OfferScreenEvents.Nothing)
    val uiState=_uiState.asStateFlow()

    init{
        getAllOffers()
        getMyOffers()
    }

    fun getAllOffers() {
        viewModelScope.launch {
            try {
                _uiState.value=OfferScreenEvents.Loading
                databaseOpUseCases.getAllOffers().collect { it ->
                    _allOffers.value = it
                    _uiState.value=OfferScreenEvents.Success
                }

            }
            catch (e:Exception){
                _uiState.value=OfferScreenEvents.Error
                Log.d("error in all offers loading",e.message.toString())
            }
        }
    }
    fun getMyOffers() {
        viewModelScope.launch {

            try {
                _uiState.value=OfferScreenEvents.Loading
                databaseOpUseCases.getMyOffers().collect { it ->
                    _myOffers.value = it
                    _uiState.value=OfferScreenEvents.Success
                }

            }
            catch (e:Exception){
                _uiState.value=OfferScreenEvents.Error
                Log.d("error in my offers loading",e.message.toString())
            }
        }
    }

    fun addOffer(offer:Offers){
        viewModelScope.launch {
            databaseOpUseCases.addOffer(offer)
        }
    }

    fun deleteOffer(offer:Offers){
        viewModelScope.launch {
            databaseOpUseCases.deleteOffer(offer)
        }
    }

}