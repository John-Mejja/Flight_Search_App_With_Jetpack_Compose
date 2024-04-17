package com.example.flightsearch.ui.screen.navigation

import androidx.annotation.StringRes

interface Navigation {
    val route: String
    @get:StringRes
    val title: Int
}