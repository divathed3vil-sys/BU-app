package com.bu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.bu.app.navigation.BuNavGraph
import com.bu.designsystem.theme.BUTheme
import com.bu.designsystem.theme.Bg0
import dagger.hilt.android.AndroidEntryPoint

/**
 * Single-activity shell. Everything is Compose; navigation is doctrine-simple.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BUTheme {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(Bg0),
                ) {
                    BuNavGraph()
                }
            }
        }
    }
}
