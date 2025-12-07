package com.example.tccmobile.ui.screens.profileScreen

import AppVersionFooter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Schedule
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
import com.example.tccmobile.ui.theme.*

@Composable
fun StatsCardStudent(
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
            modifier = Modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
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
fun StudentContactDetail(icon: ImageVector, title: String, value: String) {
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
                modifier = Modifier.size(40.dp).padding(8.dp)
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
fun StudentContactCard(data: Student, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = BlueEscuro.copy(alpha = 0.95f)),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Card(
                    shape = RoundedCornerShape(50.dp),
                    colors = CardDefaults.cardColors(containerColor = DarkBlue.copy(alpha = 0.8f)),
                    border = BorderStroke(1.dp, CianoAzul),
                ) {
                    Box(
                        modifier = Modifier.size(72.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        val initials = data.nameStudent.split(" ").take(2).map { it.first() }.joinToString("")
                        Text(initials, style = MaterialTheme.typography.headlineMedium, color = Branco)
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(data.nameStudent, style = MaterialTheme.typography.titleMedium, color = AzulSuperClaro)
                    Text(data.matricula, style = MaterialTheme.typography.titleSmall, color = AzulSuperClaro.copy(alpha = 0.7f))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            StudentContactDetail(Icons.AutoMirrored.Filled.MenuBook, "Curso", data.curso)
            Spacer(modifier = Modifier.height(16.dp))
            StudentContactDetail(Icons.Default.MailOutline, "E-mail", data.emailStudent)
            Spacer(modifier = Modifier.height(16.dp))
            StudentContactDetail(Icons.Default.Call, "Telefone", data.phoneStudent)
        }
    }
}

@Composable
fun StatCardsStudentSection(data: Student) {
    Column(Modifier.fillMaxWidth()) {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.fillMaxWidth()) {
            StatsCardStudent("TCCs Enviados", data.tccsEnviados.toString(), Icons.Default.CheckCircle, StatusTextConcluido, Modifier.weight(1f))
            StatsCardStudent("Em Análise", data.tccsEmAnalise.toString(), Icons.Default.Schedule, Orange, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(10.dp))

        }
    }


@Composable
fun StudentProfileScreen(
    data: Student,
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
                .verticalScroll(rememberScrollState()), // conteúdo rolável
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

                StudentContactCard(data)

                Spacer(modifier = Modifier.height(25.dp))

                StatCardsStudentSection(data)

                Spacer(modifier = Modifier.height(10.dp))
            }

            Box(modifier= Modifier.padding(horizontal = 30.dp)){
                Button(
                    onClick = onLogout,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Branco,
                        contentColor = NotificationRed
                    ),
                    border = BorderStroke(1.5.dp, NotificationRed)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ExitToApp,
                        contentDescription = "Sair",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        "Sair da Conta",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AppVersionFooter()
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewStudentProfileScreen() {
//    val previewTestStudent = Student(
//        nameStudent = "Maria Oliveira",
//        matricula = "20205678",
//        curso = "Engenharia de Software",
//        emailStudent = "maria.oliveira@unifor.br",
//        phoneStudent = "(85) 99876-5432",
//        tccsEnviados = 2,
//        tccsEmAnalise = 1,
//    )
//    MaterialTheme { StudentProfileScreen(previewTestStudent) {} }
//}
