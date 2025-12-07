package com.example.tccmobile.navigation

import com.example.tccmobile.data.supabase.SupabaseClient.client
import io.github.jan.supabase.auth.auth
import kotlinx.serialization.json.boolean
import kotlinx.serialization.json.jsonPrimitive
import com.example.tccmobile.ui.components.utils.BottomNavItem
import com.example.tccmobile.ui.screens.bibliotecarioTicketsScreen.BiblioTicketsScreen
import com.example.tccmobile.ui.screens.studentTicketsScreen.StudentsTicketsScreen
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Text
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
import com.example.tccmobile.navigation.Routes
import androidx.navigation.navArgument
import com.example.tccmobile.ui.screens.chatStudent.ChatStudentScreen
import com.example.tccmobile.ui.screens.loginScreen.LoginScreen
import com.example.tccmobile.ui.screens.registerScreen.RegisterScreen
import com.example.tccmobile.ui.screens.newTicketScreen.NewTicketScreen
import com.example.tccmobile.ui.screens.profileScreen.AppPermission
import com.example.tccmobile.ui.screens.profileScreen.Librarian
import com.example.tccmobile.ui.screens.profileScreen.LibrarianProfileScreen
import com.example.tccmobile.ui.screens.profileScreen.Student
import com.example.tccmobile.ui.screens.profileScreen.StudentProfileScreen

private val testLibrarian: Librarian = Librarian(
    nameLibrarian = "Ana Costa",
    role = "Bibliotecária Sênior",
    emailLibrarian = "ana.costa@unifor.br",
    phoneLibrarian = "(85) 3477-3000",
    recebidos = 45,

)

private val testStudent: Student = Student(
    nameStudent = "João Silva",
    curso = "Engenharia de Software",
    matricula = "20210011234",
    emailStudent = "ana.costa@unifor.br",
    phoneStudent = "(85) 3477-3000",
    tccsEnviados = 2,
    tccsEmAnalise = 8,

)


@SuppressLint("ViewModelConstructorInComposable", "ComposableDestinationInComposeScope")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Routes.REGISTER)
                },
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
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
        composable(Routes.NEW_TICKET) {
            NewTicketScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onTicketCreated = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.LIBRARIAN_PROFILE_SCREEN) {
            LibrarianProfileScreen(
                data = testLibrarian,
                currentRoute = Routes.LIBRARIAN_PROFILE_SCREEN,
                navigateBarItems =listOf(
                        BottomNavItem(
                            label = "Meus Envios",
                            icon = Icons.Outlined.Description,
                            route = Routes.HOME,
                            onClick = { route ->
                                navController.navigate(route)
                            }
                        ),
                        BottomNavItem(
                            label = "Perfil",
                            icon = Icons.Outlined.Person,
                            route = Routes.LIBRARIAN_PROFILE_SCREEN,
                            onClick = { route ->
                                navController.navigate(route)
                            }
                        )
                    ),
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LIBRARIAN_PROFILE_SCREEN) { inclusive = true }
                    }
                }
                )
        }

        composable(Routes.STUDENT_PROFILE_SCREEN) {
            StudentProfileScreen(
                data =testStudent,
                currentRoute = Routes.STUDENT_PROFILE_SCREEN,
                navigateBarItems =listOf(
                    BottomNavItem(
                        label = "Meus Envios",
                        icon = Icons.Outlined.Description,
                        route = Routes.HOME,
                        onClick = { route ->
                            navController.navigate(route)
                        }
                    ),
                    BottomNavItem(
                        label = "Perfil",
                        icon = Icons.Outlined.Person,
                        route = Routes.STUDENT_PROFILE_SCREEN,
                        onClick = { route ->
                            navController.navigate(route)
                        }
                    )
                ),
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.STUDENT_PROFILE_SCREEN) { inclusive = true }
                    }
                }
            )
        }


        composable(
            route = Routes.TICKET,
            arguments = listOf(
                navArgument("id") { type = NavType.StringType }
            ))
        { entry ->
            val id = entry.arguments?.getString("id")
            var isStudent by remember { mutableStateOf<Boolean?>(null) }

            LaunchedEffect(Unit) {
                val session = client.auth.currentSessionOrNull()
                isStudent = session?.user?.userMetadata?.get("isStudent")?.jsonPrimitive?.boolean
                Log.d("AUTH_LOG", "isStudent atualizado: $isStudent")
            }

            if (!id.isNullOrEmpty() && isStudent != null) {
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
            StudentsTicketsScreen(
                currentRoute = Routes.HOME,
                navigateBarItems = listOf(
                    BottomNavItem(
                        label = "Meus Envios",
                        icon = Icons.Outlined.Description,
                        route = Routes.HOME,
                        onClick = { route ->
                            navController.navigate(route)
                        }
                    ),
                    BottomNavItem(
                        label = "Perfil",
                        icon = Icons.Outlined.Person,
                        route = Routes.STUDENT_PROFILE_SCREEN,
                        onClick = { route ->
                            navController.navigate(route)
                        }
                    )
                ),
                onClickNew = {
                    navController.navigate(Routes.NEW_TICKET)
                },
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
                        route = Routes.BIBLIO_DASHBOARD, // Defina a rota correta para o dashboard
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
