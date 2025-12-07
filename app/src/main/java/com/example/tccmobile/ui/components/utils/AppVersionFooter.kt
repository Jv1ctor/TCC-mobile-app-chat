import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tccmobile.ui.theme.Black


@Composable
fun AppVersionFooter(
    universityName: String = "Universidade de Fortaleza - Unifor",
    appVersion: String = "Sistema de Gestão de TCC - Versão 1.0.0",
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 16.dp)
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FooterText(text = universityName,)
        FooterText(text = appVersion)
    }
}

@Composable
private fun FooterText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall,
        color = Black
    )
}
