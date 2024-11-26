package presentation.components

import Loader
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import models.enums.ToastType
import utils.AppColors

@Composable
fun CustomToast(
    loader: Loader,
    onDismiss: () -> Unit,
    duration: Long = 3000L // Toast duration in milliseconds
) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = loader.isToastVisible,
        enter = slideInVertically {
            // Slide in from 40 dp from the top.
            with(density) { -40.dp.roundToPx() }
        } + expandVertically(
            // Expand from the top.
            expandFrom = Alignment.Top
        ) + fadeIn(
            // Fade in with the initial alpha of 0.3f.
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ){

        if (loader.isToastVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                // Toast content at the bottom
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(16.dp)
                        .background(
                            color = when (loader.type) {
                                ToastType.SUCCESS -> AppColors.ToastSuccess.copy(alpha = 1f)
                                ToastType.ERROR -> Color.Red.copy(alpha = 1f)
                                ToastType.INFO -> AppColors.AppColor.copy(alpha = 1f)
                            },
                            shape = CircleShape
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = loader.messageToDisplay,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            // Automatically dismiss the toast after the duration
            LaunchedEffect(Unit) {
                delay(duration)
                onDismiss()
            }
        }

    }
}

