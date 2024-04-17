package com.example.flightsearch.ui.screen.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import com.example.flightsearch.R
import com.example.flightsearch.ui.screen.home.Home
import com.example.flightsearch.ui.screen.query.QueryScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightTopAppBar(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    onSearchIconClicked :() -> Unit,
    scrollBehaviour: TopAppBarScrollBehavior,
    navigateBack :() -> Unit
) {
    val currentRoute = currentDestination?.route
    val title = when (currentRoute) {
        Home.route -> Home.title
        QueryScreen.route -> QueryScreen.title
        else -> Home.title
    }
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(start = dimensionResource(id = R.dimen.padding_large))
            )
        },
        navigationIcon = {
            if (currentRoute == Home.route) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = stringResource(id = R.string.menu)
                )
            } else {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(id = R.string.back),
                    modifier = Modifier
                        .clickable {
                            navigateBack()
                        }
                )
            }
        },
        actions = {
            if (currentRoute == Home.route) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search),
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.top_app_bar_icon_size))
                        .padding(end = dimensionResource(id = R.dimen.padding_large))
                        .clickable { onSearchIconClicked() }
                )
            }
        },
        scrollBehavior = scrollBehaviour,
        modifier = modifier
    )
}
