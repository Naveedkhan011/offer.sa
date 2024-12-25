package utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import utils.AppConstants.Companion.getOutlineTextFieldColors

@Composable
fun Styles(){


    /*@Composable
    fun DisplayOutLineBox(
        value: String = "",
        readOnly: Boolean = false,
        icon: ImageVector? = null,
        isError : Boolean = false,
        label: String = "",
        onTrailingIconClick: () -> Unit = {}
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            isError = isError,
            label = { Text(text = "label") },
            readOnly = true, // Prevent manual editing
            modifier = Modifier.fillMaxWidth().clickable {
                onTrailingIconClick()
            },
            readOnly = readOnly,
            trailingIcon = {
                if (icon != null){
                    Icon(imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onTrailingIconClick()
                        })
                }
            },
            colors = getOutlineTextFieldColors()
        )
    }*/


}