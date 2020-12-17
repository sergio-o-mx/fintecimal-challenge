package com.mxrampage.fintecimalchallenge.database

import androidx.room.Entity

@Entity
data class PlaceMarked(
    val id: Long,
    val isVisited: Boolean
)
