package com.mxrampage.fintecimalchallenge.dashboard

import com.mxrampage.fintecimalchallenge.models.Location
import com.squareup.moshi.Json

data class PlacesNetworkModel(
    @Json(name = "streetName") val entryStreetName: String,
    @Json(name = "suburb") val entrySuburb: String,
    @Json(name = "visited") val isVisited: Boolean,
    @Json(name = "location") val entryLocation: Location
)
