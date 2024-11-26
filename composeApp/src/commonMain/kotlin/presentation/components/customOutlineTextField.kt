package presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.AppColors
import utils.AppConstants.Companion.getOutlineTextFieldColors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun customOutlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    errorValue: String? = null,
    painter: DrawableResource? = null,
    trailingIcon: ImageVector? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        label = {
            if (label.length > 30) {
                Text(
                    text = label.take(30) + "...",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                )
            } else Text(text = label)
        },
        trailingIcon = {
            if (trailingIcon != null) {
                Icon(Icons.Default.Info, contentDescription = "Info")
            }

            if (painter != null) {
                Icon(painter = painterResource(painter), contentDescription = null)
            }
        },
        modifier = modifier,
        isError = errorValue != null,
        colors = getOutlineTextFieldColors()
    )

    errorValue?.let {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = it,
            textAlign = TextAlign.Start,
            color = AppColors.Red,
            fontSize = 12.sp
        )
    }
}