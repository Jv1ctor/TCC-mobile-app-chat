package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tccmobile.ui.screens.newTicketScreen.NewTicketState
import com.example.tccmobile.ui.theme.AzulLetra
import com.example.tccmobile.ui.theme.Black
import com.example.tccmobile.ui.theme.BlueEscuro
import com.example.tccmobile.ui.theme.CinzaAzul
import com.example.tccmobile.ui.theme.Branco
import com.example.tccmobile.ui.theme.CianoAzul
import com.example.tccmobile.ui.theme.Cinza

@Composable
fun NewTicketForm(
    state: NewTicketState,
    onThemeChange: (String) -> Unit,
    onCursoChange: (String) -> Unit,
    onObservationsChange: (String) -> Unit,
    onAttachTccClick: () -> Unit,
    onOpenTicketClick: () -> Unit,
) {
    val isFormValid =
        state.temaTcc.isNotBlank() && state.curso.isNotBlank() && state.anexoUri != null

    Card(
        colors = CardDefaults.cardColors(containerColor = BlueEscuro),
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 12.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            InputForm(
                value = state.temaTcc,
                onValueChange = onThemeChange,
                label = "Tema do TCC *",
                placeholder = "Ex: Sistema Web para Gestão de Biblioteca",
            )

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Informe o título ou o tema do seu trabalho",
                style = MaterialTheme.typography.bodyMedium.copy(color = Branco), //Fonte ariamo - figma
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))

            InputForm(
                value = state.curso,
                onValueChange = onCursoChange,
                label = "Curso *",
                placeholder = "Ex: Engenharia de Software",
            )

            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Informe o seu curso de graduação",
                style = MaterialTheme.typography.bodyMedium.copy(color = Branco), //Fonte ariamo - figma
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(24.dp))

            InputForm(
                value = state.observacoes,
                onValueChange = onObservationsChange,
                label = "Observações (opcional)",
                placeholder = "Adicione informações sobre seu TCC",
            )
            Spacer(modifier = Modifier.height(24.dp))

            Card(
                colors = CardDefaults.cardColors(containerColor = CinzaAzul),
                elevation = CardDefaults.cardElevation(2.dp),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Próximos passos após o envio",
                        style = MaterialTheme.typography.titleMedium.copy(color = AzulLetra)
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "• A bibliotecária analisará seu trabalho em breve.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Black)
                    )
                    Text(
                        text = "• Você receberá o feedback e poderá fazer as correções.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Black)
                    )
                    Text(
                        text = "• O processo continua até a aprovação final.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Black)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onAttachTccClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CinzaAzul,
                    contentColor = CianoAzul
                )
            ) {
                Icon(Icons.Filled.AttachFile, contentDescription = "Anexar TCC")
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = state.anexoNomeArquivo.ifBlank {
                        "Anexar TCC"
                    }
                )
            }

            if (state.ticketError != null && state.anexoUri == null) {
                Text(
                    state.ticketError,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Start
                )
            }


            Spacer(modifier = Modifier.height(16.dp))


            Button(
                onClick = onOpenTicketClick,
                enabled = isFormValid,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = CianoAzul,
                    disabledContainerColor = Cinza,
                    contentColor = Branco
                )
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Branco)
                } else {
                    Icon(Icons.Filled.Send, contentDescription = "Abrir Ticket")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Abrir Ticket")
                }
            }
        }
    }
}
