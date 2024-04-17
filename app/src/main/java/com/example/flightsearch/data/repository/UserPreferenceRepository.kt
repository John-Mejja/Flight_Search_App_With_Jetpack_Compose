package com.example.flightsearch.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPreferenceRepository(
    private val datastore: DataStore<Preferences>
) {
    private companion object {
        val FLIGHT_QUERY = stringPreferencesKey("flight_query")
        const val TAG = "UserPreferencesRepo"
    }

    val flightQuery: Flow<String> = datastore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG,"Error reading preferences.",it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
        preferences[FLIGHT_QUERY] ?: ""
    }
    suspend fun saveFlightQueryPreference(flightIataCode: String) {
        datastore.edit { mutablePreferences ->
            mutablePreferences[FLIGHT_QUERY] = flightIataCode
        }
    }
}