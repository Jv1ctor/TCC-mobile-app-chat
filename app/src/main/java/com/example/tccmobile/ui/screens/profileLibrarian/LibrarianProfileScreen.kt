package com.example.tccmobile.ui.screens.profileScreen

import AppVersionFooter
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.components.utils.BottomNavItem
import com.example.tccmobile.ui.components.utils.BottomNavigationBar
import com.example.tccmobile.ui.components.utils.ProfileScreenHeader
import com.example.tccmobile.ui.theme.AzulSuperClaro
import com.example.tccmobile.ui.theme.BlueEscuro
import com.example.tccmobile.ui.theme.Branco
import com.example.tccmobile.ui.theme.CianoAzul
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.NotificationRed
import com.example.tccmobile.ui.theme.StatusTextConcluido



@Composable
fun StatsCardLibrarian(
    title: String,
    value: String,
    icon: ImageVector,
    iconTint: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(90.dp),
        colors = CardDefaults.cardColors(containerColor = BlueEscuro),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp).padding(end = 4.dp)
                )

                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Branco
                )
            }

            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = AzulSuperClaro
            )
        }
    }
}


@Composable
fun LibrarianContactCard(dataLibrarian: Librarian, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BlueEscuro.copy()),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(50.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkBlue.copy(alpha = 0.8f)),
                    border = BorderStroke(1.dp, CianoAzul),
                ) {
                    Box(
                        modifier = Modifier.size(72.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val initials = dataLibrarian.nameLibrarian.split(" ").take(2).map { it.first() }
                            .joinToString("")
                        Text(
                            text = initials,
                            style = MaterialTheme.typography.headlineMedium,
                            color = Branco
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = dataLibrarian.nameLibrarian,
                        style = MaterialTheme.typography.titleMedium,
                        color = AzulSuperClaro
                    )
                    Text(
                        text = dataLibrarian.role,
                        style = MaterialTheme.typography.titleMedium,
                        color = AzulSuperClaro.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LibrarianContactDetail(
                icon = Icons.Default.MailOutline,
                title = "E-mail",
                value = dataLibrarian.emailLibrarian
            )

            Spacer(modifier = Modifier.height(16.dp))

            LibrarianContactDetail(
                icon = Icons.Default.Call,
                title = "Telefone",
                value = dataLibrarian.phoneLibrarian
            )
        }
    }
}
@SuppressLint("DefaultLocale")
@Composable
fun StatCardsLibrarianSection(data: Librarian) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            StatsCardLibrarian(
                title = "Recebidos",
                value = data.recebidos.toString(),
                icon = Icons.Default.CheckCircle,
                iconTint = StatusTextConcluido,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))


        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
        }
    }
}





@Composable
fun LibrarianContactDetail(icon: ImageVector, title: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = AzulSuperClaro.copy(alpha = 0.5f)),
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = Branco,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = Branco.copy(alpha = 0.7f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                color = AzulSuperClaro
            )
        }
    }
}


@Composable
fun LibrarianProfileScreen(
    data: Librarian,
    currentRoute: String,
    navigateBarItems: List<BottomNavItem>,
    onLogout: () -> Unit,
) {
    Scaffold(
        containerColor = Branco,
        topBar = { ProfileScreenHeader() },
        bottomBar = {
            BottomNavigationBar(
                items = navigateBarItems,
                currentRoute = currentRoute,
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(40.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                LibrarianContactCard(data)

                Spacer(modifier = Modifier.height(10.dp))

                StatCardsLibrarianSection(data)

                Spacer(modifier = Modifier.height(10.dp))

            }

            Box(modifier = Modifier.padding(horizontal = 60.dp)){
                Button(
                    onClick = onLogout,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Branco,
                        contentColor = NotificationRed
                    ),
                    border = BorderStroke(1.5.dp, NotificationRed)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Sair",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "Sair da Conta",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))

            AppVersionFooter()
        }
    }
}




//@Preview(showBackground = true)
//@Composable
//fun PreviewLibrarianProfileScreen() {
//    val previewGrantedPermissions = setOf(
//        AppPermission.MANAGE_TICKETS,
//        AppPermission.TRANSFER_TICKETS,
//        AppPermission.FINALIZE_TICKETS
//    )
//
//    val testLibrarian = Librarian(
//        nameLibrarian = "Ana Costa",
//        role = "Bibliotecária Sênior",
//        emailLibrarian = "ana.costa@unifor.br",
//        phoneLibrarian = "(85) 3477-3000",
//        recebidos = 45,
//        permissoes = generatePermissions(previewGrantedPermissions)
//    )
//
//    MaterialTheme {
//        LibrarianProfileScreen(
//            data = testLibrarian,
//            currentRoute = "profile",
//            navigateBarItems = List<BottomNavItem>,
//            onLogout = () -> Uni
//        )
//    }
//}
