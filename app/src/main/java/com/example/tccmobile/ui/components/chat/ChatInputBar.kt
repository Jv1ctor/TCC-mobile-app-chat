package com.example.tccmobile.ui.components.chat

import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.AttachFile
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.Blue
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.LightBlue
import com.example.tccmobile.ui.theme.White

@Composable
fun ChatInputBar(
    message: String,
    fileName: String,
    onMessageChange: (String) -> Unit, // função que avisa que usuário digitou uma algo novo
    onSendClick: () -> Unit,        // ação quando clica no aviãozinho
    onAttachClick: () -> Unit,       // ação quando clica no clipe
    onRemoveFile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .padding(vertical = 16.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if(fileName.isNotBlank()){
            Box(
                modifier = Modifier
                    .padding(12.dp) // margem externa
                    .clip(RoundedCornerShape(8.dp))
                    .background(Blue)
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Description,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = White
                    )

                    Text(
                        text = fileName,
                        fontSize = 16.sp,
                        color = White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f) // ocupa o espaço para empurrar o X para a direita
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    )

                    IconButton(
                        onClick = { onRemoveFile() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Remover",
                            tint = White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

        }





        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconButton(// botão de anexo
                onClick = onAttachClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightBlue)
            ) {
                Icon(
                    imageVector = Icons.Outlined.AttachFile,
                    contentDescription = "Anexar",
                    tint = DarkBlue //ícone azul escuro pra dar contraste né veyr
                )
            }


            TextField( //input
                value = message,
                onValueChange = onMessageChange,
                placeholder = {
                    Text(
                        "Digite sua mensagem...",
                        color = Color.Gray, // cor do texto de dica
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp),

                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, // fundo branco quando tá digitando
                    unfocusedContainerColor = Color.White, // fundo branco quando não tá
                    focusedIndicatorColor = Color.Transparent, // remove a linha de baixo
                    unfocusedIndicatorColor = Color.Transparent, // remove a linha de baixo
                    cursorColor = Color.DarkGray, // cor do traço que pisca
                    focusedTextColor = Color.Black, // cor do texto digitado
                    unfocusedTextColor = Color.Black
                ),
                singleLine = true // impede que o texto quebre linha e aumente a altura da barra
            )


            IconButton(//botão de enviar
                onClick = onSendClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(LightBlue)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Enviar",
                    tint = DarkBlue
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(//texto auxiliar
            text = "Envie mensagens ou anexe documentos PDF",
            color = LightBlue,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Preview
@Composable
fun ChatInputPreview() {
    ChatInputBar(
        message = "",
        onMessageChange = {},
        onSendClick = {},
        fileName = "teste.doc",
        onAttachClick = {},
        onRemoveFile = {}
    )
}