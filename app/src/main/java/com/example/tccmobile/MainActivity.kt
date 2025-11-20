package com.example.tccmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tccmobile.Components.Teste

import com.example.tccmobile.ui.theme.TCCMobileTheme
import com.example.tccmobile.TicketApp.TicketApp

import com.example.tccmobile.TicketApp.TicketApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicketApp()  // 👈 Chama a tela normalmente
        }
    }
}