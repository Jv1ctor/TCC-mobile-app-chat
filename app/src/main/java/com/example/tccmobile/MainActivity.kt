package com.example.tccmobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.tccmobile.ui.components.DashboardBibliotecario.DashboardHeader // Importa o componente Header
import com.example.tccmobile.ui.screens.bibliodashscreen.DashboardScreen
import com.example.tccmobile.ui.theme.white
import androidx.compose.ui.tooling.preview.Preview
import com.example.tccmobile.navigation.AppNavigation
import com.example.tccmobile.ui.theme.TCCMobileTheme

/**
 * Ponto de entrada principal da aplicação.
 * Responsável por configurar o tema e iniciar o Compose.
 *
 * Agora exibe APENAS o DashboardHeader.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Aqui você aplicaria o seu tema (Theme.kt)
            MaterialTheme {
                // A tela usa Surface para aplicar a cor de fundo DarkBlueBackground
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = white
                ) {
                    DashboardScreen()
                }
            }
        }
    }
}

/**
 * Função Composable simples que apenas exibe o Header.
 */
@Composable
fun SimpleHeaderDisplay() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // O SEU HEADER PERSONALIZADO
        DashboardHeader(onBackClicked = {
            // Implementação de clique: Apenas um log no console por enquanto.
            println("Botão Voltar Clicado na Main Activity")
        })

        // Espaço em branco abaixo do header. Futuramente, aqui será o Dashboard.
    }
            TCCMobileTheme {
                AppNavigation()
            }
        }
    }
}