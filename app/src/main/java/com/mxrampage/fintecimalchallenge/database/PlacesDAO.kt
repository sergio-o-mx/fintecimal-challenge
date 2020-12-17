package com.mxrampage.fintecimalchallenge.database

import androidx.room.*

@Dao
interface PlacesDAO {
    @Query("SELECT * FROM places_table ORDER BY id DESC")
    suspend fun getAllPlacesFromLocalStorage(): List<PlacesRoomModel>

    @Query("SELECT * FROM places_table WHERE id = :placeId")
    suspend fun getPlaceById(placeId: Long): PlacesRoomModel

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun savePlacesOnLocalStorage(placesRoomModel: PlacesRoomModel)
}
