package com.example.flightsearch

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.flightsearch.data.di.AppContainer
import com.example.flightsearch.data.di.AppDataContainer
import com.example.flightsearch.data.repository.UserPreferenceRepository

private const val FLIGHT_QUERY_PREFERENCE_NAME = "flight_query_preference"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = FLIGHT_QUERY_PREFERENCE_NAME
)
class FlightSearchApplication: Application() {
    lateinit var container: AppContainer
    lateinit var userPreferenceRepository: UserPreferenceRepository

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
        userPreferenceRepository = UserPreferenceRepository(dataStore)
    }
}