package presentation.screen.signup_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import kotlinx.coroutines.delay
import layoutDirection
import models.enums.SignUpStates
import models.enums.ToastType
import navigator
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.company_logo
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.components.TermsAndConditionsText
import presentation.screen.main_screen.MyHomeScreen
import showToast
import utils.AppColors
import utils.AppConstants
import utils.AppConstants.Companion.CustomButton
import utils.AppConstants.Companion.getCheckBoxColors
import utils.language.language_manager.LanguageManager


class SignUpScreen : Screen {

    @OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val signUpUiState = getScreenModel<SignupViewModel>()

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
            Scaffold(topBar = {
                CenterAlignedTopAppBar(title = { Text(text = LanguageManager.currentStrings.signIn) },
                    navigationIcon = {
                        IconButton(onClick = {
                            handleBackPress(signUpUiState)
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "back"
                            )
                        }
                    })
            }) {

                Box(
                    Modifier.fillMaxSize()
                        .padding(top = it.calculateTopPadding(), start = 20.dp, end = 20.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Logo
                        Image(
                            painter = painterResource(Res.drawable.company_logo),
                            contentDescription = null,
                            modifier = Modifier.size(100.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        when (signUpUiState.signUpStates) {
                            SignUpStates.EMAIL_VALIDATION -> {
                                emailComposeUi(signUpUiState)
                            }

                            SignUpStates.EMAIL_OTP_VALIDATION -> {
                                emailComposeUi(signUpUiState)
                            }

                            SignUpStates.IQAMA_VALIDATION -> {
                                iqamaComposeUi(signUpUiState)
                            }

                            SignUpStates.IQAMA_OTP_VALIDATION -> {
                                iqamaComposeUi(signUpUiState)
                            }

                            SignUpStates.MOBILE_VALIDATION -> {
                                mobileComposeUi(signUpUiState)
                            }

                            SignUpStates.MOBILE_OTP_VALIDATION -> {
                                mobileComposeUi(signUpUiState)
                            }

                            SignUpStates.PASSWORD_VALIDATION -> {
                                passwordComposeUi(signUpUiState)
                            }

                            else -> {
                                showToast("SignUp Successfully", ToastType.SUCCESS)
                                navigator.replaceAll(MyHomeScreen())
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Column(Modifier.fillMaxWidth().align(Alignment.BottomCenter)) {
                        CustomButton(
                            onClick = {
                                handleButtonClick(signUpUiState)
                            },
                            text = getButtonText(signUpUiState),
                            enable = (signUpUiState.isTermsChecked) || signUpUiState.buttonEnabled
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Login Option
                        Row(
                            modifier = Modifier
                                .fillMaxWidth() // Make the Row take up the full width
                                .padding(bottom = 15.dp, top = 10.dp, start = 10.dp, end = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center // Center the content horizontally
                        ) {
                            Text(
                                text = LanguageManager.currentStrings.alreadyHaveAccount,
                                color = Color.Gray,
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = LanguageManager.currentStrings.signIn,
                                fontWeight = FontWeight.Bold,
                                color = AppColors.AppColor,
                                modifier = Modifier.clickable {
                                    navigator.pop()
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    private fun handleBackPress(signInViewModel: SignupViewModel) {

        when (signInViewModel.signUpStates) {
            SignUpStates.EMAIL_VALIDATION -> {
                navigator.pop()
            }

            SignUpStates.EMAIL_OTP_VALIDATION -> {
                signInViewModel.signUpStates = SignUpStates.EMAIL_VALIDATION
            }

            SignUpStates.IQAMA_VALIDATION -> {
                signInViewModel.signUpStates = SignUpStates.EMAIL_VALIDATION
            }

            SignUpStates.IQAMA_OTP_VALIDATION -> {
                signInViewModel.signUpStates = SignUpStates.IQAMA_VALIDATION
            }

            SignUpStates.MOBILE_VALIDATION -> {
                signInViewModel.signUpStates = SignUpStates.IQAMA_VALIDATION
            }

            SignUpStates.MOBILE_OTP_VALIDATION -> {
                signInViewModel.signUpStates = SignUpStates.MOBILE_VALIDATION
            }

            else -> {
                signInViewModel.signUpStates = SignUpStates.MOBILE_VALIDATION
            }
        }

    }

    private fun handleButtonClick(signInViewModel: SignupViewModel) {
        when (signInViewModel.signUpStates) {
            SignUpStates.EMAIL_VALIDATION -> {
                signInViewModel.sendOtpToEmail()
            }

            SignUpStates.EMAIL_OTP_VALIDATION -> {
                signInViewModel.verifyEmailOTP(signInViewModel.emailOTPVerification)
            }

            SignUpStates.IQAMA_VALIDATION -> {
                signInViewModel.sendNationalIdOtp()
            }

            SignUpStates.IQAMA_OTP_VALIDATION -> {
                signInViewModel.verifyNationalIdByOtp(
                    signInViewModel.email,
                    signInViewModel.nationalIdOTP
                )
            }

            SignUpStates.MOBILE_VALIDATION -> {
                signInViewModel.sendVerificationCodeSMS(
                    signInViewModel.email,
                    signInViewModel.mobileNumber
                )
            }

            SignUpStates.MOBILE_OTP_VALIDATION -> {
                signInViewModel.verifySMSCodeAny(signInViewModel.email, signInViewModel.mobileOtp)
            }

            else -> {
                signInViewModel.onSignup()
            }
        }
    }

    private fun getButtonText(signInViewModel: SignupViewModel): String {
        return when (signInViewModel.signUpStates) {
            SignUpStates.EMAIL_VALIDATION -> {
                LanguageManager.currentStrings.sendOtp
            }

            SignUpStates.EMAIL_OTP_VALIDATION -> {
                LanguageManager.currentStrings.verifyOtp
            }

            SignUpStates.IQAMA_VALIDATION -> {
                LanguageManager.currentStrings.sendOtp
            }

            SignUpStates.IQAMA_OTP_VALIDATION -> {
                LanguageManager.currentStrings.verifyOtp
            }

            SignUpStates.MOBILE_VALIDATION -> {
                LanguageManager.currentStrings.sendOtp
            }

            SignUpStates.MOBILE_OTP_VALIDATION -> {
                LanguageManager.currentStrings.verifyOtp
            }

            else -> {
                LanguageManager.currentStrings.register
            }
        }
    }

    @Composable
    private fun passwordComposeUi(signInViewModel: SignupViewModel) {
        Spacer(modifier = Modifier.height(8.dp))

        // Password Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signInViewModel.password,
            onValueChange = { signInViewModel.password = it },
            label = { Text("Password") },
            isError = signInViewModel.passwordError != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Next
            ),
            colors = AppConstants.getOutlineTextFieldColors()
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

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signInViewModel.confirmPassword,
            onValueChange = { signInViewModel.confirmPassword = it },
            label = { Text("Confirm Password") },
            isError = signInViewModel.confirmPasswordError != null,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password, imeAction = ImeAction.Done
            ),
            colors = AppConstants.getOutlineTextFieldColors()
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

        Spacer(modifier = Modifier.height(16.dp))
        // Terms and Conditions Checkbox
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()
        ) {
            Checkbox(
                checked = signInViewModel.isTermsChecked,
                onCheckedChange = { signInViewModel.isTermsChecked = it },
                colors = getCheckBoxColors()
            )

            TermsAndConditionsText(
                onTermsClick = {
                    // Navigate to the "Terms and Conditions" screen or perform some action
                    println("Terms and Conditions clicked")
                },
                onPrivacyClick = {
                    // Navigate to the "Privacy Policy" screen or perform some action
                    println("Privacy Policy clicked")
                }
            )
        }
    }

    @Composable
    private fun mobileComposeUi(signInViewModel: SignupViewModel) {

        Spacer(modifier = Modifier.height(8.dp))
        // Mobile Number Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signInViewModel.mobileNumber,
            onValueChange = { signInViewModel.mobileNumber = it },
            label = { Text("Mobile Number *") },
            isError = signInViewModel.mobileNumberError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
            ),
            trailingIcon = {
                if (signInViewModel.verifyingMobile) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            },
            leadingIcon = { Text("+966") },
            enabled = signInViewModel.signUpStates == SignUpStates.MOBILE_VALIDATION,
            colors = AppConstants.getOutlineTextFieldColors()
        )

        signInViewModel.mobileNumberError?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start,
                text = it,
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        LaunchedEffect(signInViewModel.mobileNumber) {
            delay(500L)
            signInViewModel.checkMobileAvailability(signInViewModel.mobileNumber)
        }

        if (signInViewModel.signUpStates == SignUpStates.MOBILE_OTP_VALIDATION) {
            Spacer(modifier = Modifier.height(8.dp))

            // Mobile Number Field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = signInViewModel.mobileOtp,
                onValueChange = { signInViewModel.mobileOtp = it },
                label = { Text("Enter otp") },
                isError = signInViewModel.mobileNumberError != null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone, imeAction = ImeAction.Next
                ),

                colors = AppConstants.getOutlineTextFieldColors()
            )
            signInViewModel.mobileOtpError?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    text = it,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }

    @Composable
    private fun iqamaComposeUi(signInViewModel: SignupViewModel) {
        Spacer(modifier = Modifier.height(8.dp))
        // National ID Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signInViewModel.nationalId,
            onValueChange = { signInViewModel.nationalId = it },
            label = { Text("National ID *") },
            isError = signInViewModel.nationalIdError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
            ),
            trailingIcon = {
                if (signInViewModel.verifyingNationalID) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            },
            enabled = signInViewModel.signUpStates == SignUpStates.IQAMA_VALIDATION,
            colors = AppConstants.getOutlineTextFieldColors()
        )

        LaunchedEffect(signInViewModel.nationalId) {
            delay(500L)
            signInViewModel.verifyIqama()
        }

        signInViewModel.nationalIdError?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                textAlign = TextAlign.Start,
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        if (signInViewModel.signUpStates == SignUpStates.IQAMA_OTP_VALIDATION) {
            Spacer(modifier = Modifier.height(16.dp))

            // National id otp Field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = signInViewModel.nationalIdOTP,
                onValueChange = { signInViewModel.nationalIdOTP = it },
                label = { Text(LanguageManager.currentStrings.otp) },
                isError = signInViewModel.emailOTPError != null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ),
                colors = AppConstants.getOutlineTextFieldColors()
            )

            signInViewModel.nationalIdOTPError?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    textAlign = TextAlign.Start,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }

    }

    @Composable
    private fun emailComposeUi(signInViewModel: SignupViewModel) {

        // Email Field
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = signInViewModel.email,
            onValueChange = { signInViewModel.email = it },
            label = { Text(LanguageManager.currentStrings.emailStar) },
            isError = signInViewModel.emailError != null,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email, imeAction = ImeAction.Next
            ),
            trailingIcon = {
                if (signInViewModel.verifyingEmail) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        strokeWidth = 2.dp
                    )
                }
            },
            enabled = signInViewModel.signUpStates == SignUpStates.EMAIL_VALIDATION,
            colors = AppConstants.getOutlineTextFieldColors()
        )

        LaunchedEffect(signInViewModel.email) {
            delay(500L)
            signInViewModel.emailAvailable()
        }

        signInViewModel.emailError?.let {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = it,
                textAlign = TextAlign.Start,
                color = Color.Red,
                fontSize = 12.sp
            )
        }

        if (signInViewModel.signUpStates == SignUpStates.EMAIL_OTP_VALIDATION) {
            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = signInViewModel.emailOTPVerification,
                onValueChange = { signInViewModel.emailOTPVerification = it },
                label = { Text(LanguageManager.currentStrings.otp) },
                isError = signInViewModel.emailOTPError != null,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number, imeAction = ImeAction.Next
                ),
                colors = AppConstants.getOutlineTextFieldColors()
            )

            signInViewModel.emailOTPError?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    textAlign = TextAlign.Start,
                    color = Color.Red,
                    fontSize = 12.sp
                )
            }
        }
    }
}


