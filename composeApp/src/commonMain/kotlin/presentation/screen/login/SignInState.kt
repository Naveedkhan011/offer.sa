package presentation.screen.login

import models.ChangePasswordResponse
import models.LoggedUser
import models.LoginResponse

sealed class SignInState {
    data object None : SignInState()
    data object Loading : SignInState()
    data class OtpSent(val response: LoginResponse) : SignInState()
    data class OtpVerified(val response: LoggedUser) : SignInState()
    data class PasswordChanged(val message: String) : SignInState()
    data class Error(val message: String) : SignInState()
}
