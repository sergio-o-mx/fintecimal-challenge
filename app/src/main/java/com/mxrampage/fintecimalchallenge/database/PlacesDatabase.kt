package com.mxrampage.fintecimalchallenge.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlacesRoomModel::class], version = 1, exportSchema = false)
abstract class PlacesDatabase : RoomDatabase() {
    abstract fun placesDao(): PlacesDAO
}
