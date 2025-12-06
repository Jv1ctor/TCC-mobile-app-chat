package com.example.tccmobile.navigation

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tccmobile.ui.screens.chatStudent.ChatStudentScreen
import com.example.tccmobile.ui.screens.loginScreen.LoginScreen
import com.example.tccmobile.ui.screens.registerScreen.RegisterScreen
import com.example.tccmobile.ui.screens.newTicketScreen.NewTicketScreen
import com.example.tccmobile.data.supabase.SupabaseClient.client
import io.github.jan.supabase.auth.auth
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonPrimitive
import kotlin.text.get
import com.example.tccmobile.ui.components.utils.BottomNavItem
import com.example.tccmobile.ui.components.utils.BottomNavigationBar
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
                    navController.navigate(Routes.ticket("123")) {
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
        
        
        //Precisa ser implementado sua rota correta quando o dashboard for criado
        composable(Routes.NEW_TICKET){
            NewTicketScreen(
                onBackClick = {

                    navController.popBackStack()
                },
                onTicketCreated = {
                    navController.popBackStack()
                }
            )
        }
        
        
        composable(
            route = Routes.TICKET,
            arguments = listOf(
                navArgument("id"){ type = NavType.StringType }
            ))
        { entry ->
            val id = entry.arguments?.getString("id")
            var isStudent by remember { mutableStateOf<Boolean?>(null) }

            LaunchedEffect(Unit) {
                val session = client.auth.currentSessionOrNull()
                isStudent = session?.user?.userMetadata?.get("isStudent")?.jsonPrimitive?.boolean
                Log.d("AUTH_LOG", "isStudent atualizado: $isStudent")
            }

            if(!id.isNullOrEmpty() && isStudent != null){
                ChatStudentScreen(
                    ticketId = id,
                    isStudent = isStudent!!,
                    onBackClick = {
                        navController.navigate(Routes.LOGIN)
                    }
                )
            }
        }



        composable(Routes.HOME) {
            DashboardTicketsScreen (
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
}