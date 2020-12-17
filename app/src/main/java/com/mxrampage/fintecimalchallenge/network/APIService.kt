package com.mxrampage.fintecimalchallenge.network

import com.mxrampage.fintecimalchallenge.dashboard.PlacesNetworkModel
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("/api/interview")
    suspend fun getAllPlacesFromNetwork(): Response<List<PlacesNetworkModel>>
}
