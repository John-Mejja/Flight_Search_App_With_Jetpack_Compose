package com.example.flightsearch.ui.screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun NothingToShow(
    modifier: Modifier = Modifier,
    message: String
) {
    Box(modifier = modifier){
        Text(
            text = message,
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}