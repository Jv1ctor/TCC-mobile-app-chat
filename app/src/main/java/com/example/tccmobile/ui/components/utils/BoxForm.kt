package com.example.tccmobile.ui.components.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tccmobile.ui.theme.DarkBlue


@Composable
fun BoxForm(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit // Aceita conteúdo
) {
    Column (
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(DarkBlue)
            .padding(vertical = 24.dp, horizontal = 20.dp),
        content = content
    )
}
//
//@Preview(showBackground = true)
//@Composable
//fun BoxPreview() {
//    TCCMobileTheme {
//        BoxForm(
//            modifier = Modifier
//                .padding(10.dp)
//                .fillMaxSize()
//                .clip(RoundedCornerShape(5))
//                .background(Color.Blue),
//            content =
//        )
//    }
//}