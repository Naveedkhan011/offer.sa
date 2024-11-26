package presentation.screen.forgot_password

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import kotlinx.coroutines.delay
import layoutDirection
import models.enums.ToastType
import navigator
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.company_logo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import showToastUsingLaunchEffect
import utils.AppColors
import utils.AppConstants
import utils.AppConstants.Companion.getButtonColors
import utils.language.language_manager.LanguageManager


class ForgotPasswordScreen : Screen {

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val forgotPasswordViewModel = getScreenModel<ForgotPasswordViewModel>()

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Scaffold(topBar = {
                CenterAlignedTopAppBar(title = { Text(text = LanguageManager.currentStrings.forgotPassword) },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigator.pop()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "back"
                            )
                        }
                    })
            }) {



                Column(
                    modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(26.dp))


                    // Logo
                    Image(
                        painter = painterResource(Res.drawable.company_logo),
                        contentDescription = null,
                        modifier = Modifier.size(120.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    // Email Field
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = forgotPasswordViewModel.email,
                        onValueChange = { forgotPasswordViewModel.email = it },
                        label = { Text(LanguageManager.currentStrings.emailStar) },
                        isError = forgotPasswordViewModel.emailError != null,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
                        ),
                        trailingIcon = {
                            if (forgotPasswordViewModel.verifyingEmail) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(24.dp),
                                    strokeWidth = 2.dp
                                )
                            }
                        },
                        colors = AppConstants.getOutlineTextFieldColors()
                    )

                    LaunchedEffect(forgotPasswordViewModel.email) {
                        delay(500L)
                        forgotPasswordViewModel.isValidEmail()
                    }

                    forgotPasswordViewModel.emailError?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = it,
                            textAlign = TextAlign.Start,
                            color = AppColors.Red,
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))


                    Button(
                        enabled = forgotPasswordViewModel.buttonEnabled,
                        onClick = {
                            forgotPasswordViewModel.sendTemporaryPassword()
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        colors = getButtonColors()
                    ) {
                        Text(text = LanguageManager.currentStrings.sendOtp, color = Color.White)
                    }

                    if (forgotPasswordViewModel.success){
                        forgotPasswordViewModel.success = false
                        showToastUsingLaunchEffect(forgotPasswordViewModel.responseMessage, ToastType.SUCCESS)
                        forgotPasswordViewModel.reset()
                        navigator.pop()
                    }

                }
            }
        }
    }
}