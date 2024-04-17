package com.example.flightsearch.ui.screen.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.flightsearch.ui.screen.home.Home
import com.example.flightsearch.ui.screen.home.HomeScreen
import com.example.flightsearch.ui.screen.query.QueryScreen

@Composable
fun FlightNavHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController ,
        startDestination = Home.route
    ) {
        composable(route = Home.route) {
            HomeScreen(
                modifier = modifier
            )
        }
        composable(route = QueryScreen.route) {
            QueryScreen(
                modifier = modifier
            )
        }

    }
}
