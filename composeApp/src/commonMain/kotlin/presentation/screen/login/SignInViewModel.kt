package presentation.screen.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import models.ChangePasswordResponse
import models.LoggedUser
import models.LoginResponse
import network.Ktor
import utils.language.language_manager.LanguageManager

class SignInViewModel : ScreenModel {

    override fun onDispose() {
        reset()
    }

    private val _uiSignInState: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val uiSignInState: StateFlow<LoginUiState> = _uiSignInState

    var signUpStates by mutableStateOf(UiStates.AUTH)
    var loggedUser by mutableStateOf(LoggedUser())

    var email by mutableStateOf("naveedkhan011@gmail.com")
    var password by mutableStateOf("")
    var newPassword by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)
    var newPasswordError by mutableStateOf<String?>(null)
    var confirmPasswordError by mutableStateOf<String?>(null)


    fun sendOTP() {
        if (validateForm()) {

            // Set UI state to loading
            _uiSignInState.value = LoginUiState(SignInState.Loading)

            screenModelScope.launch {
                try {

                    // API call to login endpoint
                    val response = Ktor.client.post("/portal-api/insurance/rest/auth") {
                        // Add content type header and serialize the body
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                        setBody(SignInRequest(username = email, password = password))
                    }.body<LoginResponse>()

                    if (response.errorCode == null) {
                        signUpStates = UiStates.OTP_VALIDATION
                        _uiSignInState.value = LoginUiState(SignInState.OtpSent(response))
                    } else
                        _uiSignInState.value =
                            LoginUiState(SignInState.Error(response.errorMessage))

                } catch (e: Exception) {
                    // Handle exceptions and update UI state with error
                    val errorMessage = e.message ?: "Unknown error occurred"
                    _uiSignInState.value = LoginUiState(SignInState.Error(errorMessage))
                }
            }
        }
    }

    fun verifyOtp(otp: String) {
        screenModelScope.launch {
            try {
                // Set UI state to loading
                _uiSignInState.value = LoginUiState(SignInState.Loading)

                // API call to login endpoint
                val response =
                    Ktor.client.post("/portal-api/insurance/rest/auth") {
                        // Add content type header and serialize the body
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                        setBody(
                            VerifyEmailOTPRequest(
                                username = email,
                                password = password,
                                type = "mobile",
                                otp = otp
                            )
                        )
                    }.body<LoggedUser>()

                if ((response.message != null && response.message == "otp_verification_failed") ||
                    response.errorCode != null
                ) {
                    _uiSignInState.value = LoginUiState(
                        SignInState.Error(
                            response.errorMessage ?: response.message.toString()
                        )
                    )
                } else {
                    loggedUser = response
                    signUpStates = if (response.user?.changePassword == true) {
                        UiStates.CHANGE_PASSWORD
                    } else {
                        UiStates.LOGIN_SUCCESSFULLY
                    }

                    _uiSignInState.value = LoginUiState(SignInState.OtpVerified(response))

                }

            } catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error occurred"
                _uiSignInState.value = LoginUiState(SignInState.Error(errorMessage))
            }
        }
    }

    fun changePassword() {
        screenModelScope.launch {
            try {
                // Set UI state to loading
                _uiSignInState.value = LoginUiState(SignInState.Loading)

                // API call to login endpoint
                val response =
                    Ktor.client.post("/portal-api/insurance/rest/changePwd") {
                        // Add content type header and serialize the body
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                        bearerAuth(loggedUser.token.toString())
                        setBody(
                            mapOf(
                                "id" to loggedUser.user?.id.toString(),
                                "name" to loggedUser.user?.name.toString(),
                                "email" to loggedUser.user?.email.toString(),
                                "userType" to "Client",
                                "userName" to loggedUser.user?.email.toString(),
                                "password" to newPassword,
                                "oldPassword" to password
                            )
                        )
                    }.body<ChangePasswordResponse>()

                if (response.errorCode != null) {
                    _uiSignInState.value = LoginUiState(
                        SignInState.Error(
                            response.errorMessage ?: response.message.toString()
                        )
                    )
                } else {
                    signUpStates = UiStates.LOGIN_SUCCESSFULLY
                    _uiSignInState.value =
                        LoginUiState(SignInState.PasswordChanged(response.message!!))
                }

            } catch (e: Exception) {
                val errorMessage = e.message ?: "Unknown error occurred"
                _uiSignInState.value = LoginUiState(SignInState.Error(errorMessage))
            }
        }
    }

    fun isValidEmail(): Boolean {
        var isValid = true
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"

        emailError = if (email.isBlank()) {
            isValid = false
            LanguageManager.currentStrings.emailIsRequired
        } else if (!email.matches(emailRegex.toRegex())) {
            isValid = false
            "Invalid email format"
        } else null

        return isValid
    }


    // sign up section
    fun isValidPasswords(): Boolean {
        var isValid = true

        newPasswordError = if (newPassword.isBlank()) {
            isValid = false
            LanguageManager.currentStrings.passwordIsRequired
        } else if (newPassword.length < 8) {
            isValid = false
            LanguageManager.currentStrings.passwordValidationMessage
        } else null

        return isValid
    }

    // sign up section
    fun isValidConfirmPasswords(): Boolean {
        var isValid = true

        confirmPasswordError = if (confirmPassword != newPassword) {
            isValid = false
            "Passwords do not match"
        } else null

        return isValid
    }

    private fun isPasswordNotEmpty(): Boolean {
        var isValid = true

        passwordError = if (password.isBlank()) {
            isValid = false
            LanguageManager.currentStrings.passwordIsRequired
        } else null

        return isValid
    }

    private fun validateForm(): Boolean {
        return isValidEmail() && isPasswordNotEmpty()
    }

    fun reset() {
        signUpStates = UiStates.AUTH
        _uiSignInState.value = LoginUiState(SignInState.None)

        email = ""
        password = ""

        emailError = null
        passwordError = null
    }
}