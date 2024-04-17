package com.example.flightsearch.data.di

import com.example.flightsearch.data.repository.FlightSearchRepository

interface AppContainer {
    val flightSearchRepository: FlightSearchRepository
}