
package com.example.offlinetask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.offlinetask.ui.navigation.AppNavigation
import com.example.offlinetask.ui.theme.OfflineTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OfflineTaskTheme {
                AppNavigation()
            }
        }
    }
}
