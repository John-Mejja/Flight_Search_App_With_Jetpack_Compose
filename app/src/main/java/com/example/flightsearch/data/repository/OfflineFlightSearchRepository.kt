package com.example.flightsearch.data.repository

import com.example.flightsearch.data.database.Airport
import com.example.flightsearch.data.database.Favorite
import com.example.flightsearch.data.database.FlightDao
import com.example.flightsearch.data.model.AirportRoute
import com.example.flightsearch.data.model.AirportIataAndName
import kotlinx.coroutines.flow.Flow

class OfflineFlightSearchRepository(
    private val flightDao: FlightDao
) : FlightSearchRepository {
    override suspend fun getAutoCompletionSuggestions(query: String): List<AirportIataAndName> {
        return flightDao.getSearchSuggestions(query)
    }

    override fun getFavoriteRoutesStream(): Flow<List<AirportRoute>> {
        return flightDao.getFavoriteRoute()
    }

    override suspend fun getAirport(iataCode: String): Airport {
        return flightDao.getAirport(iataCode)
    }

    override suspend fun getOtherAirportsIataCodeAndName(iataCode: String): List<AirportIataAndName> {
        return flightDao.getOtherAirportsNotMatchingIataCode(iataCode)
    }

    override suspend fun insertFavoriteRoute(favorite: Favorite) {
        return flightDao.insert(favorite)
    }

    override suspend fun deleteFavoriteRoute(favorite: Favorite) {
        return flightDao.delete(favorite)
    }
}