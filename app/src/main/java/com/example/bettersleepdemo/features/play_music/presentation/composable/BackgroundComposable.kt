package com.example.bettersleepdemo.features.play_music.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.bettersleepdemo.R

//Background composable
@Composable
fun BoxScope.Background() {
    Image(
        modifier = Modifier.align(Alignment.BottomCenter),
        painter = painterResource(R.drawable.bg_lake),
        contentDescription = ""
    )
    Image(
        modifier = Modifier
            .align(Alignment.TopCenter)
            .fillMaxWidth()
            .fillMaxWidth(),
        painter = painterResource(R.drawable.bg_main),
        contentDescription = "",
        contentScale = ContentScale.FillWidth
    )
    Image(
        modifier = Modifier
            .align(Alignment.Center)
            .fillMaxWidth(),
        painter = painterResource(R.drawable.bg_main),
        contentDescription = "",
        contentScale = ContentScale.FillWidth
    )
}