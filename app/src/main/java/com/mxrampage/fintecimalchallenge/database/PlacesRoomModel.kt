package com.mxrampage.fintecimalchallenge.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mxrampage.fintecimalchallenge.models.Location

@Entity(tableName = "places_table")
data class PlacesRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val entryStreetName: String,
    val entrySuburb: String,
    val isVisited: Boolean,
    @Embedded
    val entryLocation: Location

)
