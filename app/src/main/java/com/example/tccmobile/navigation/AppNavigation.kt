import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tccmobile.navigation.Routes
import com.example.tccmobile.ui.screens.loginScreen.LoginScreen
import com.example.tccmobile.ui.screens.registerScreen.RegisterScreen
import com.example.tccmobile.ui.screens.newTicketScreen.NewTicketScreen
import com.example.tccmobile.ui.screens.profileScreen.AppPermission
import com.example.tccmobile.ui.screens.profileScreen.Librarian
import com.example.tccmobile.ui.screens.profileScreen.LibrarianProfileScreen
import com.example.tccmobile.ui.screens.profileScreen.Student
import com.example.tccmobile.ui.screens.profileScreen.StudentProfileScreen
import com.example.tccmobile.ui.screens.profileScreen.generatePermissions


private val testGrantedPermissions: Set<AppPermission> = setOf(
    AppPermission.MANAGE_TICKETS,
    AppPermission.TRANSFER_TICKETS,
    AppPermission.FINALIZE_TICKETS,
)

private val testLibrarian: Librarian = Librarian(
    nameLibrarian = "Ana Costa",
    role = "Bibliotecária Sênior",
    emailLibrarian = "ana.costa@unifor.br",
    phoneLibrarian = "(85) 3477-3000",
    corrigidos = 45,
    pendentes = 8,
    avaliacaoMedia = 4.8f,
    tempoMedio = "2.3 dias",
    permissoes = generatePermissions(testGrantedPermissions)
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


@SuppressLint("ViewModelConstructorInComposable")
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
                onLogout = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.STUDENT_PROFILE_SCREEN) { inclusive = true }
                    }
                }
            )
        }
    }
    }