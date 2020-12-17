package com.mxrampage.fintecimalchallenge.dashboard

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mxrampage.fintecimalchallenge.database.PlacesRoomModel
import kotlinx.coroutines.launch

class DashboardViewModel @ViewModelInject constructor(
    private val dashboardRepository: DashboardRepository
) : ViewModel() {
    private val getPlacesFromLocalStorageSuccessMessage = "Datos obtenidos localmente con exito"
    private var _response = MutableLiveData<DashboardUIStateManager>()
    val response: LiveData<DashboardUIStateManager>
        get() = _response

    init {
        checkIfLocalStorageIsEmpty()
    }

    private fun checkIfLocalStorageIsEmpty() {
        _response.value = DashboardUIStateManager.Loading
        viewModelScope.launch {
            val responseFromLocalStorage = dashboardRepository.getPlacesFromLocalStorage()
            if (responseFromLocalStorage.isEmpty()) {
                getPlacesFromNetwork()
            } else {
                _response.postValue(
                    DashboardUIStateManager.Message(getPlacesFromLocalStorageSuccessMessage)
                )
                _response.postValue(DashboardUIStateManager.Response(responseFromLocalStorage))
            }
        }
    }

    private suspend fun getPlacesFromNetwork() {
        viewModelScope.launch {
            try {
                val responseFromNetwork = dashboardRepository.getPlacesFromNetwork()
                _response.postValue(DashboardUIStateManager.Message(responseFromNetwork.message()))
                responseFromNetwork.body()?.let {
                    for (places in it) {
                        val placesRoomModel = PlacesRoomModel(
                            0,
                            places.entryStreetName,
                            places.entrySuburb,
                            places.isVisited,
                            places.entryLocation
                        )
                        dashboardRepository.savePlacesOnLocalStorage(placesRoomModel)
                    }
                }
                checkIfLocalStorageIsEmpty()
            } catch (exception: Exception) {
                _response.postValue(DashboardUIStateManager.Error(exception))
            }
        }
    }
}
