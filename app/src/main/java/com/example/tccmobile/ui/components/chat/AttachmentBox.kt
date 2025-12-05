package com.example.tccmobile.ui.components.chat

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.Blue
import com.example.tccmobile.ui.theme.DarkBlue
import com.example.tccmobile.ui.theme.LightBlue

@Composable
fun AttachmentBox(//caixinha com ícone e nome do arquivo
    fileName: String,
    backgroundColor: Color,
    isLoading: Boolean,
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

            if(isLoading){
                CircularProgressIndicator(
                    modifier = Modifier.size(23.dp),
                    color = Blue,
                    trackColor = LightBlue,
                )
            }else{
                Icon(
                    imageVector = Icons.Outlined.Download, // ícone de download na ponta
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}