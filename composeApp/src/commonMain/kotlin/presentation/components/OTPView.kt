package presentation.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import utils.AppColors
import utils.language.language_manager.LanguageManager


@Composable
fun OtpDialog(
    phoneNumber: String,
    onOtpEntered: (String) -> Unit,
    onResendOtp: () -> Unit,
    onClose: () -> Unit,
    timeRemaining: Int = 180
) {
    var otpValues by remember { mutableStateOf(List(4) { "" }) }
    var timeRemaining by remember { mutableStateOf(timeRemaining) } // Timer in seconds (3 minutes)
    val resendEnabled = timeRemaining == 0

    LaunchedEffect(key1 = timeRemaining) {
        if (timeRemaining > 0) {
            kotlinx.coroutines.delay(1000L) // 1-second delay
            timeRemaining -= 1
        }
    }

    Dialog(onDismissRequest = { onClose() }) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Close Icon
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(onClick = { onClose() }) {
                        Icon(Icons.Default.Close, contentDescription = "Close Dialog")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Enter the 4-digit otp",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtext
                Text(
                    text = "+$phoneNumber",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // OTP Input
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    val focusRequesters = remember { List(4) { FocusRequester() } }
                    for (index in 0..3) {
                        OTPTextField(
                            value = otpValues[index],
                            onValueChange = { newValue ->
                                if (newValue.length <= 1) {
                                    otpValues =
                                        otpValues.toMutableList().apply { this[index] = newValue }
                                    if (newValue.isNotEmpty() && index < 3) {
                                        focusRequesters[index + 1].requestFocus()
                                    }
                                }
                                if (otpValues.joinToString("").length == 4) {
                                    onOtpEntered(otpValues.joinToString(""))
                                }
                            },
                            modifier = Modifier
                                .weight(1f)
                                .focusRequester(focusRequesters[index])
                                .padding(horizontal = 4.dp),
                            onBackspace = {
                                if (index > 0 && otpValues[index].isEmpty()) {
                                    focusRequesters[index - 1].requestFocus()
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Timer Text
                Text(
                    text = "Time remaining: ${timeRemaining / 60}:${(timeRemaining % 60).toString().padStart(2, '0')}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Resend Button
                Button(
                    onClick = {
                        timeRemaining = 180
                        onResendOtp()
                    },
                    enabled = resendEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (resendEnabled) AppColors.AppColor else Color.Gray,
                        contentColor = if (resendEnabled) Color.White else Color.LightGray
                    )
                ) {
                    Text(LanguageManager.currentStrings.resendOTP)
                }
            }
        }
    }
}

@Composable
fun OTPTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onBackspace: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(50.dp)
            .border(1.dp, Color.Gray, shape = RoundedCornerShape(8.dp)) // Border for each box
            .background(Color.White)
    ) {
        BasicTextField(
            value = value,
            onValueChange = { newValue ->
                if (newValue.isEmpty()) {
                    onBackspace()
                }
                onValueChange(newValue)
            },
            modifier = Modifier.fillMaxSize().padding(top = 7.dp),
            textStyle = TextStyle(
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                color = Color.Black
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            decorationBox = { innerTextField ->
                innerTextField() // No default decoration, removing the underline
            }
        )
    }
}




