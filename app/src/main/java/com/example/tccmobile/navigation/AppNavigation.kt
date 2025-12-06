package com.example.tccmobile.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tccmobile.ui.components.utils.BottomNavItem
import com.example.tccmobile.ui.screens.bibliotecarioTicketsScreen.BiblioTicketsScreen
import com.example.tccmobile.ui.screens.loginScreen.LoginScreen
import com.example.tccmobile.ui.screens.registerScreen.RegisterScreen
import com.example.tccmobile.ui.screens.studentTicketsScreen.StudentsTicketsScreen

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

    NavHost(navController = navController, startDestination = Routes.BIBLIO_TICKETS) {

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
            StudentsTicketsScreen (
                navigateBarItems = listOf(
                    BottomNavItem(
                        label= "Meus Envios",
                        icon= Icons.Outlined.Description,
                        route= Routes.HOME,
                        onClick= { route ->
                            navController.navigate(route)
                        }
                    ),
                    BottomNavItem(
                        label= "Perfil",
                        icon= Icons.Outlined.Person,
                        route= Routes.PROFILE,
                        onClick= { route ->
                            navController.navigate(route)
                        }
                    )
                ),
                currentRoute = Routes.HOME,
                onTicketClick = { ticketId ->
                    println("Cliquei no ticket: $ticketId")
                }
            )
        }
        composable(Routes.BIBLIO_TICKETS) {
            BiblioTicketsScreen(
                navigateBarItems = listOf(
                    BottomNavItem(
                        label = "Tickets",
                        icon = Icons.Outlined.Description,
                        route = Routes.BIBLIO_TICKETS,
                        onClick = { route ->
                            navController.navigate(route)
                        }
                    ),
                    BottomNavItem(
                        label = "Dashboard",
                        icon = Icons.Outlined.Dashboard,
                        route = "dashboard_route", // Defina a rota correta para o dashboard
                        onClick = { route ->
                            navController.navigate(route)
                        }
                    ),
                    BottomNavItem(
                        label = "Perfil",
                        icon = Icons.Outlined.Person,
                        route = Routes.PROFILE,
                        onClick = { route ->
                            navController.navigate(route)
                        }
                    )
                ),
                currentRoute = Routes.BIBLIO_TICKETS,
                onTicketClick = { ticketId ->
                    println("Cliquei no ticket: $ticketId")
                },
                onDashboardClick = {
                    navController.navigate(Routes.BIBLIO_DASHBOARD)
                }
            )
        }
        composable(Routes.BIBLIO_DASHBOARD) {
            Text(text = "Dashboard Screen")
        }
    }
}