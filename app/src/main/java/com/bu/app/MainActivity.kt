package com.bu.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.bu.app.navigation.BuRoot
import com.bu.designsystem.theme.BuTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The single Activity. Everything else is Compose state over the ledger.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BuTheme {
                BuRoot()
            }
        }
    }
}
