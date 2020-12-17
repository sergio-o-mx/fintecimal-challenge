package com.mxrampage.fintecimalchallenge.details

import com.mxrampage.fintecimalchallenge.database.PlacesRoomModel

sealed class DetailsUIStateManager {
    object Loading : DetailsUIStateManager()
    class QueryResponse(val place: PlacesRoomModel) : DetailsUIStateManager()
    class UpdateResponse(val updated: Int) : DetailsUIStateManager()
}
