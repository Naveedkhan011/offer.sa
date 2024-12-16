package presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun DashedBorderCard(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val stroke = Stroke(
                width = 4f, // Border thickness
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(10f, 10f), // Dash length, Gap length
                    phase = 0f
                )
            )
            drawRoundRect(
                color = Color.Gray.copy(alpha = 0.5f), // Border color
                style = stroke,
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(8.dp.toPx())
            )
        }

        // Card Content
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            content()
        }
    }
}
