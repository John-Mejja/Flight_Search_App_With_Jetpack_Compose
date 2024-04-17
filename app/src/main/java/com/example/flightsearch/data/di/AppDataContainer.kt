package com.example.flightsearch.data.di

import android.content.Context
import com.example.flightsearch.data.database.FlightSearchDatabase
import com.example.flightsearch.data.repository.FlightSearchRepository
import com.example.flightsearch.data.repository.OfflineFlightSearchRepository

class AppDataContainer(
    private val context: Context
): AppContainer {
    override val flightSearchRepository: FlightSearchRepository by lazy {
        OfflineFlightSearchRepository(
            flightDao = FlightSearchDatabase.getDatabase(context).FlightDao()
        )
    }
}