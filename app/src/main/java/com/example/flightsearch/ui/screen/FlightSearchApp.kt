package com.example.flightsearch.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.flightsearch.R
import com.example.flightsearch.ui.screen.components.FlightTopAppBar
import com.example.flightsearch.ui.screen.home.Home
import com.example.flightsearch.ui.screen.navigation.FlightNavHost
import com.example.flightsearch.ui.screen.query.QueryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightSearchApp(
    modifier: Modifier = Modifier
) {
    val navHostController = rememberNavController()
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold (
        topBar = {
            FlightTopAppBar(
                currentDestination = currentDestination,
                onSearchIconClicked = {
                    navHostController.navigate(QueryScreen.route){
                        popUpTo(Home.route)
                    } },
                scrollBehaviour = scrollBehavior,
                navigateBack = { navHostController.navigateUp() },
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(id = R.dimen.padding_large),
                        vertical = dimensionResource(id = R.dimen.padding_small) )
            )
        },
        modifier = modifier
    ){ innerPadding ->
        FlightNavHost(
            navHostController = navHostController,
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = dimensionResource(id = R.dimen.padding_large),
                    end = dimensionResource(id = R.dimen.padding_large )
                )
        )

    }
}