package com.example.bettersleepdemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bettersleepdemo.common.ui.theme.BetterSleepDemoTheme
import com.example.bettersleepdemo.features.play_music.presentation.MediaPlayViewModel
import com.example.bettersleepdemo.features.play_music.presentation.composable.SoundListComposable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: MediaPlayViewModel = hiltViewModel()
            val uiState by viewModel.mediaUiState.collectAsState()
            BetterSleepDemoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SoundListComposable(
                        modifier = Modifier.padding(innerPadding),
                        uiState = uiState,
                        onUIEvent = viewModel::onEvent
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BetterSleepDemoTheme {
        Greeting("Android")
    }
}