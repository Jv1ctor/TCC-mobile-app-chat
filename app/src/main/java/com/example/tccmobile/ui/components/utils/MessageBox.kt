package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.Blue
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.LightBlue


@Composable
fun MessageBox(
    message: String,
    date: String,
    isStudent: Boolean, //true = aluno, false = bibliotecária
    nameSubmitter: String? = null, //nome de quem mandou
    attachmentName: String? = null, //nome do arquivo anexo
    onAttachmentClick: (() -> Unit)? = null
) {


    val backgroundColor = if (isStudent) DarkBlue else Color.White // se for aluno, fundo azul escuro. se for bibliotecária, fundo branco.
    val textColor = if (isStudent) Color.White else Color.Black // se for aluno, letra branca pra ler no escuro. se não, letra preta.
    val alignment = if (isStudent) Alignment.End else Alignment.Start// se for aluno, joga pra direita. se não, joga pra esquerda.

    val borderStroke = if (!isStudent) BorderStroke(1.dp, Color(0xFFE0E0E0)) else null // só coloca borda se for o balão branco (da bibliotecária)

    val shape = if (isStudent) {// se for aluno, canto superior direito fica pontudo
        RoundedCornerShape(topStart = 12.dp, topEnd = 2.dp, bottomStart = 12.dp, bottomEnd = 12.dp)
    } else {
        RoundedCornerShape(topStart = 2.dp, topEnd = 12.dp, bottomStart = 12.dp, bottomEnd = 12.dp)
    }

    Column(// coluna principal que segura o balão e a data embaixo
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = alignment // aplica o alinhamento lá de cima
    ) {
        Card(
            modifier = Modifier
                .widthIn(max = 300.dp) // trava a largura máxima pra mensagem não esticar a tela toda
                .padding(bottom = 4.dp),
            shape = shape,
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            border = borderStroke
        ) {

            Column(modifier = Modifier.padding(12.dp)) {// conteúdo de dentro do balão
                if (!isStudent && nameSubmitter != null) {// só mostra o nome se NÃO for aluno e se tiver passado um nome.
                    Text(
                        text = nameSubmitter,
                        color = DarkBlue,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                }

                // texto da mensagem em si
                Text(
                    text = message,
                    color = textColor,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                if (attachmentName != null) {// área do anexo (só desenha se tiver arquivo)
                    Spacer(modifier = Modifier.height(8.dp))
                    val attachBg = if (isStudent) Blue else LightBlue
                    val attachContent = if (isStudent) Color.White else DarkBlue

                    AttachmentBox(
                        fileName = attachmentName,
                        backgroundColor = attachBg,
                        contentColor = attachContent,
                        onClick = onAttachmentClick ?: {}
                    )
                }
            }
        }

        Text(//data
            text = date,
            color = Color.Gray,
            fontSize = 11.sp,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@Composable
fun AttachmentBox(//caixinha com ícone e nome do arquivo
    fileName: String,
    backgroundColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = ButtonDefaults.buttonElevation(0.dp, 0.dp, 0.dp), // Tira a sombra
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Description, // ícone de folha de papel
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(// nome do arquivo que corta com "..." se for muito grande
                text = fileName,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = Icons.Outlined.Download, // ícone de download na ponta
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MessageDialogPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp) // espaço entre as mensagens
    ) {

        MessageBox(//aluno
            message = "Olá, segue meu TCC para análise.",
            date = "15/10/2024 às 14:35",
            isStudent = true, // define que sou eu (azul escuro, direita)
            attachmentName = "TCC_Joao_v1.pdf"
        )

        MessageBox(// biblio
            message = "Olá João! Recebido. Vou analisar e te dou retorno em breve.",
            date = "15/10/2024 às 14:40",
            isStudent = false, // define que é ela (branco, esquerda)
            nameSubmitter = "Bibliotecária"
        )

        MessageBox(//biblio
            message = "Fiz algumas anotações, veja o anexo.",
            date = "20/10/2024 às 10:00",
            isStudent = false,
            nameSubmitter = "Bibliotecária",
            attachmentName = "Correcoes_v1.pdf"
        )
    }
}