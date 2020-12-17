package com.mxrampage.fintecimalchallenge.details

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxrampage.fintecimalchallenge.database.PlaceMarked
import com.mxrampage.fintecimalchallenge.database.PlacesRoomModel
import kotlinx.coroutines.launch

class DetailsViewModel @ViewModelInject constructor(
    private val repository: DetailsRepository
) : ViewModel() {
    private var _place = MutableLiveData<DetailsUIStateManager>()
    val place: LiveData<DetailsUIStateManager>
        get() = _place

    fun getSpecificPlaceById(placeId: Long) {
        viewModelScope.launch {
            _place.postValue(DetailsUIStateManager.QueryResponse(repository.getSpecificPlaceById(placeId)))
        }
    }

    fun markPlaceAsVisited(place: PlacesRoomModel) {
        viewModelScope.launch {
            val placeMarked = PlaceMarked(
                place.id,
                true
            )
            val updateResponse = repository.markPlaceAsVisited(placeMarked)
            _place.postValue(DetailsUIStateManager.UpdateResponse(updateResponse))
        }
    }
}
