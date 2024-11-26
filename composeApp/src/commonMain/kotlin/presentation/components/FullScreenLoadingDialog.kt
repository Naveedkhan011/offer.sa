package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FullScreenLoadingDialog(isLoading: Boolean) {
    if (isLoading) {
        // Full-screen Box for overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)) // Dim background
                .clickable(enabled = false) {} // Disable clicks on background
        ) {
            // Dialog for loading spinner or content
            Dialog(
                onDismissRequest = { /* Prevent dismissal */ },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false // Full screen dialog
                )
            ) {
                // Centered content (spinner)
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.medium
                        )
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
