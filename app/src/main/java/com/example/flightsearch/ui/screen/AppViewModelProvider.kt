package com.example.flightsearch.ui.screen

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.ui.screen.home.HomeScreenViewModel
import com.example.flightsearch.ui.screen.query.QueryViewModel

object AppViewModelProvider {
    val Factory: ViewModelProvider.Factory = viewModelFactory {
        initializer {
            HomeScreenViewModel(
                flightSearchRepository = flightSearchApplication().container.flightSearchRepository
            )
        }
        initializer {
            QueryViewModel(
                flightSearchRepository = flightSearchApplication().container.flightSearchRepository,
                userPreferenceRepository = flightSearchApplication().userPreferenceRepository
            )
        }
    }
}
fun CreationExtras.flightSearchApplication(): FlightSearchApplication =
    (this[APPLICATION_KEY] as FlightSearchApplication)