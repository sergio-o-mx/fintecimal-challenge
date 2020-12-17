package com.mxrampage.fintecimalchallenge.dashboard

import com.mxrampage.fintecimalchallenge.database.PlacesRoomModel

sealed class DashboardUIStateManager {
    object Loading : DashboardUIStateManager()
    class Message(val message: String) : DashboardUIStateManager()
    class Response(val places: List<PlacesRoomModel>) : DashboardUIStateManager()
    class Error(val exception: Exception) : DashboardUIStateManager()
}
