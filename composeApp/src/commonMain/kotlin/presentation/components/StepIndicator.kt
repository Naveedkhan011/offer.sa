package presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StepIndicator(
    steps: List<String>, // Titles of steps
    currentStep: Int, // Index of the currently active step (0-based)
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        steps.forEachIndexed { index, title ->
            val isCompleted = index < currentStep
            val isActive = index == currentStep
            val isUpcoming = index > currentStep

            // Step Circle
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = when {
                                isCompleted -> Color(0xFF459FFF) // Completed - Blue
                                isActive -> Color(0xFF459FFF) // Active - Blue
                                else -> Color(0xFFE0E0E0) // Upcoming - Gray
                            },
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (isCompleted) {
                        // Checkmark for completed steps
                        Canvas(modifier = Modifier.size(20.dp)) {
                            val size = size.minDimension
                            val strokeWidth = size / 6
                            drawLine(
                                color = Color.White,
                                start = Offset(size * 0.2f, size * 0.5f),
                                end = Offset(size * 0.4f, size * 0.7f),
                                strokeWidth = strokeWidth
                            )
                            drawLine(
                                color = Color.White,
                                start = Offset(size * 0.4f, size * 0.7f),
                                end = Offset(size * 0.8f, size * 0.3f),
                                strokeWidth = strokeWidth
                            )
                        }
                    } else if (isActive) {
                        // Inner white border for the active step
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .background(Color.White, CircleShape)
                        )
                    }
                }

                // Title Text
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = title,
                    fontSize = 12.sp,
                    color = when {
                        isCompleted || isActive -> Color.Black
                        else -> Color.Gray
                    },
                    fontWeight = if (isActive) FontWeight.Bold else FontWeight.Normal
                )
            }

            // Connector Line (if not the last step)
            if (index < steps.size - 1) {
                Canvas(
                    modifier = Modifier
                        .weight(1f)
                        .height(2.dp)
                ) {
                    val lineColor = if (isCompleted) Color(0xFF459FFF) else Color(0xFFE0E0E0)
                    drawLine(
                        color = lineColor,
                        start = Offset(0f, size.height / 2),
                        end = Offset(size.width, size.height / 2),
                        strokeWidth = size.height
                    )
                }
            }
        }
    }
}





