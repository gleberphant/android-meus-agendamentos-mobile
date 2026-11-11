package com.github.gleberphant.appagendamentos

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.github.gleberphant.appagendamentos.ui.theme.AppAgendamentosTheme
import com.github.gleberphant.appagendamentos.views.AppNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppAgendamentosTheme {
                AppNavigator()
            }
        }
    }
}
