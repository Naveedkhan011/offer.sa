package presentation.screen.login

import SHARED_PREFERENCE
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import isLoading
import kotlinx.serialization.Serializable
import layoutDirection
import models.LoggedUser
import models.LoginResponse
import models.enums.ToastType
import navigator
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.company_logo
import offer.composeapp.generated.resources.ic_baseline_alternate_email_24
import offer.composeapp.generated.resources.ic_baseline_lock_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.components.OtpDialog
import presentation.screen.forgot_password.ForgotPasswordScreen
import presentation.screen.signup_screen.SignUpScreen
import showToastUsingLaunchEffect
import utils.AppColors
import utils.AppConstants
import utils.AppConstants.Companion.getButtonColors
import utils.AppConstants.Companion.getOutlineTextFieldColors
import utils.LogInManager
import utils.language.language_manager.LanguageManager


// Data classes for UI state and API request/response
@Serializable
data class SignInRequest(val username: String, val password: String)
data class LoginUiState(val apiStatus: SignInState = SignInState.None)

@Serializable
data class VerifyEmailOTPRequest(
    val username: String,
    val password: String,
    val type: String,
    val otp: String
)

enum class UiStates {
    AUTH,
    OTP_VALIDATION,
    CHANGE_PASSWORD,
    LOGIN_SUCCESSFULLY,
}

lateinit var signInViewModel: SignInViewModel
lateinit var uiSignInState: LoginUiState

class LoginScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        navigator = LocalNavigator.currentOrThrow
        signInViewModel = getScreenModel<SignInViewModel>()
        uiSignInState = signInViewModel.uiSignInState.collectAsState().value
        var otpResponse by remember { mutableStateOf(LoginResponse()) }

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Scaffold(topBar = {
                CenterAlignedTopAppBar(title = { Text(text = LanguageManager.currentStrings.signIn) },
                    navigationIcon = {
                        IconButton(onClick = {
                            if (signInViewModel.signUpStates == UiStates.OTP_VALIDATION)
                                signInViewModel.signUpStates = UiStates.AUTH
                            else
                                navigator.pop()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "back"
                            )
                        }
                    })
            }) {

                Box(
                    modifier = Modifier.fillMaxSize()
                        .padding(top = it.calculateTopPadding(), start = 20.dp, end = 20.dp)
                ) {

                    loginCompose()

                    if (signInViewModel.signUpStates == UiStates.OTP_VALIDATION) {
                        otpDialog(otpResponse)
                    }

                    when (val state = uiSignInState.apiStatus) {
                        is SignInState.None -> {
                            print("SignInState Entered None case")
                        }

                        is SignInState.Loading -> {
                            isLoading = true
                        }

                        is SignInState.OtpSent -> {
                            isLoading = false
                            otpResponse = state.response
                            showToastUsingLaunchEffect(otpResponse.message, ToastType.SUCCESS)
                        }

                        is SignInState.OtpVerified -> {
                            isLoading = false
                            showToastUsingLaunchEffect(state.response.message!!, ToastType.SUCCESS)
                            handleLoginSuccess(state.response)
                        }

                        is SignInState.PasswordChanged -> {
                            isLoading = false
                            val message = state.message
                            showToastUsingLaunchEffect(message, ToastType.SUCCESS)
                            navigator.pop()
                        }

                        is SignInState.Error -> {
                            isLoading = false
                            val message = state.message
                            showToastUsingLaunchEffect(message, ToastType.ERROR)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun otpDialog(loginResponse: LoginResponse) {
        OtpDialog(
            phoneNumber = "966 *** *** **${loginResponse.lastTwoDigitMob}",
            onOtpEntered = { enteredOtp ->
                signInViewModel.verifyOtp(enteredOtp)
                println("OTP Entered: $enteredOtp") // Handle OTP submission
            },
            onResendOtp = {
                signInViewModel.sendOTP()
                println("Resend OTP triggered") // Handle resend logic
            },
            onClose = {
                if (signInViewModel.signUpStates == UiStates.CHANGE_PASSWORD) {
                    navigator.pop()
                } else
                    signInViewModel.signUpStates = UiStates.AUTH
            },
            timeRemaining = loginResponse.otpExpiryInSec
        )
    }


    @OptIn(ExperimentalResourceApi::class)
    @Composable
    private fun loginCompose() {
        var passwordVisibility by remember { mutableStateOf(false) }
        var newPasswordVisibility by remember { mutableStateOf(false) }
        var confirmPasswordVisibility by remember { mutableStateOf(false) }
        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Logo
            Image(
                painter = painterResource(Res.drawable.company_logo),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )

            /* // Sign-in title
             Text(
                 text = LanguageManager.currentStrings.signIn,
                 style = MaterialTheme.typography.headlineMedium,
                 color = Color(0xFF1A1B2D)
             )*/

            // Email label and text field
            Spacer(modifier = Modifier.height(30.dp))
            OutlinedTextField(
                value = signInViewModel.email,
                onValueChange = {
                    signInViewModel.email = it
                    signInViewModel.isValidEmail()
                },
                label = { Text(LanguageManager.currentStrings.email) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_baseline_alternate_email_24),
                        contentDescription = null,
                        tint = AppColors.AppColor
                    )
                },
                enabled = signInViewModel.signUpStates != UiStates.CHANGE_PASSWORD,
                isError = signInViewModel.emailError != null,
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )
            signInViewModel.emailError?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    textAlign = TextAlign.Start,
                    color = AppColors.Red,
                    fontSize = 12.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = signInViewModel.password,
                onValueChange = { signInViewModel.password = it },
                label = { Text(text = LanguageManager.currentStrings.password) },
                leadingIcon = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_baseline_lock_24),
                        contentDescription = null,
                        tint = AppColors.AppColor
                    )
                },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {

                    val icon =
                        if (passwordVisibility) Icons.Default.Favorite else Icons.Default.Build

                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = icon, contentDescription = "Show password"
                        )
                    }
                },
                enabled = signInViewModel.signUpStates != UiStates.CHANGE_PASSWORD,
                isError = signInViewModel.passwordError != null,
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )
            signInViewModel.passwordError?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    text = it,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }

            if (signInViewModel.signUpStates == UiStates.CHANGE_PASSWORD) {

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = signInViewModel.newPassword,
                    onValueChange = {
                        signInViewModel.newPassword = it
                        signInViewModel.isValidPasswords()
                    },
                    label = { Text(text = LanguageManager.currentStrings.newPassword) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_baseline_lock_24),
                            contentDescription = null,
                            tint = AppColors.AppColor
                        )
                    },
                    visualTransformation = if (newPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {

                        val icon =
                            if (newPasswordVisibility) Icons.Default.Favorite else Icons.Default.Build

                        IconButton(onClick = { newPasswordVisibility = !newPasswordVisibility }) {
                            Icon(
                                imageVector = icon, contentDescription = "Show password"
                            )
                        }
                    },
                    isError = signInViewModel.newPasswordError != null,
                    modifier = Modifier.fillMaxWidth(),
                    colors = getOutlineTextFieldColors()
                )
                signInViewModel.newPasswordError?.let {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = signInViewModel.confirmPassword,
                    onValueChange = {
                        signInViewModel.confirmPassword = it
                        signInViewModel.isValidConfirmPasswords()
                    },
                    label = { Text(text = LanguageManager.currentStrings.confirmPassword) },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_baseline_lock_24),
                            contentDescription = null,
                            tint = AppColors.AppColor
                        )
                    },
                    visualTransformation = if (confirmPasswordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {

                        val icon =
                            if (confirmPasswordVisibility) Icons.Default.Favorite else Icons.Default.Build

                        IconButton(onClick = {
                            confirmPasswordVisibility = !confirmPasswordVisibility
                        }) {
                            Icon(
                                imageVector = icon, contentDescription = "Show password"
                            )
                        }
                    },
                    isError = signInViewModel.confirmPasswordError != null,
                    modifier = Modifier.fillMaxWidth(),
                    colors = getOutlineTextFieldColors()
                )
                signInViewModel.confirmPasswordError?.let {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start,
                        text = it,
                        color = Color.Red,
                        fontSize = 12.sp
                    )
                }
            }

            if (signInViewModel.signUpStates != UiStates.CHANGE_PASSWORD) {
                // Forgot Password
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    modifier = Modifier
                        .background(Color.White, RoundedCornerShape(10.dp))
                        .align(Alignment.CenterHorizontally),
                    onClick = {
                        navigator.push(ForgotPasswordScreen())
                    }
                ) {

                    Text(
                        text = LanguageManager.currentStrings.forgotPassword,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = AppColors.textColor,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Sign In Button
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (signInViewModel.signUpStates == UiStates.CHANGE_PASSWORD) {
                        signInViewModel.changePassword()
                    } else {
                        signInViewModel.sendOTP()
                    }
                },
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                colors = getButtonColors()
            ) {
                Text(
                    text = if (signInViewModel.signUpStates == UiStates.CHANGE_PASSWORD)
                        LanguageManager.currentStrings.updatePassword
                    else LanguageManager.currentStrings.signIn,
                    color = Color.White
                )
            }

            // Create Account
            Spacer(modifier = Modifier.height(8.dp))

            if (signInViewModel.signUpStates != UiStates.CHANGE_PASSWORD) {
                Row {
                    Text(
                        text = LanguageManager.currentStrings.doNotHaveAccount,
                        color = Color.Gray,
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = LanguageManager.currentStrings.signUp,
                        fontWeight = FontWeight.Bold,
                        color = AppColors.AppColor,
                        modifier = Modifier.clickable {
                            navigator.push(SignUpScreen())
                        })
                }
            }
        }
    }
}

private fun handleLoginSuccess(loggedUser: LoggedUser) {
    saveUser(loggedUser)
    LogInManager.setLoggedInValue(true)
    if (loggedUser.user?.changePassword != true)
        navigator.pop()
}

private fun saveUser(loggedUser: LoggedUser) {
    SHARED_PREFERENCE.put(
        AppConstants.SharedPreferenceKeys.LOGGED_IN_USER,
        loggedUser.toString()
    )
}

