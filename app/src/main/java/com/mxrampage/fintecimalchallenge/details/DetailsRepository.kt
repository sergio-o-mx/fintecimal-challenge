package com.mxrampage.fintecimalchallenge.details

import com.mxrampage.fintecimalchallenge.database.PlacesDAO
import javax.inject.Inject

class DetailsRepository @Inject constructor(
    private val placesDAO: PlacesDAO
) {
    suspend fun getSpecificPlaceById(placeId: Long) = placesDAO.getPlaceById(placeId)
}
