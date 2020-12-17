package com.mxrampage.fintecimalchallenge.dashboard

import com.mxrampage.fintecimalchallenge.database.PlacesDAO
import com.mxrampage.fintecimalchallenge.database.PlacesRoomModel
import com.mxrampage.fintecimalchallenge.network.APIService
import javax.inject.Inject

class DashboardRepository @Inject constructor(
    private val apiService: APIService,
    private val placesDAO: PlacesDAO
) {
    suspend fun getPlacesFromNetwork() = apiService.getAllPlacesFromNetwork()
    suspend fun getPlacesFromLocalStorage() = placesDAO.getAllPlacesFromLocalStorage()
    suspend fun savePlacesOnLocalStorage(placesRoomModel: PlacesRoomModel) =
        placesDAO.savePlacesOnLocalStorage(placesRoomModel)
}
