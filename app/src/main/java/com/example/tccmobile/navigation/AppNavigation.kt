package com.example.tccmobile.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tccmobile.ui.screens.loginScreen.LoginScreen
import com.example.tccmobile.ui.screens.registerScreen.RegisterScreen
import com.example.tccmobile.ui.screens.studentTicketsScreen.DashboardTicketsScreen

//@Composable
//fun AppNavigation() {
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = Routes.LOGIN) {
//        composable(Routes.LOGIN) {
//            LoginScreen(
//                onNavigateToRegister = {
//                    navController.navigate(Routes.REGISTER)
//                },
//                onLoginSuccess = {
//                    navController.navigate(Routes.HOME) {
//                        popUpTo(Routes.LOGIN) { inclusive = true }
//                    }
//                }
//            )
//        }
//        composable(Routes.REGISTER) {
//            RegisterScreen(
//                onNavigateToLogin = {
//                    navController.popBackStack()
//                },
//                onRegisterSuccess = {
//                    navController.popBackStack()
//                }
//            )
//        }
//        // composable(Routes.HOME) { /* ...Sua tela principal... */ }
//    }
//}


// IR DIRETO PARA A TELA DOS TICKETS (SÓ DESCOMENTAR O QUE ESTÁ EMBAIXO E COMENTAR ACIMA) PARA TESTAR
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.REGISTER) {
            RegisterScreen(
                onNavigateToLogin = {
                    navController.popBackStack()
                },
                onRegisterSuccess = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.HOME) {
            DashboardTicketsScreen (
                onNavigate = { route ->
                    if (route != Routes.HOME) {
                        navController.navigate(route)
                    }
                },
                onTicketClick = { ticketId ->
                    println("Cliquei no ticket: $ticketId")
                }
            )
        }
    }
}