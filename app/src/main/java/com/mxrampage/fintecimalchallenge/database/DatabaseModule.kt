package com.mxrampage.fintecimalchallenge.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {
    @Provides
    @Singleton
    fun providePlacesDatabase(@ApplicationContext appContext: Context): PlacesDatabase {
        return Room.databaseBuilder(
            appContext,
            PlacesDatabase::class.java,
            "places_database"
        ).build()
    }

    @Provides
    fun providePlacesDAO(placesDatabase: PlacesDatabase): PlacesDAO {
        return placesDatabase.placesDao()
    }
}
