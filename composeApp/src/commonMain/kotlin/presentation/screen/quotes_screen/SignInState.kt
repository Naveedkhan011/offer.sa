package presentation.screen.quotes_screen

import models.CreatePolicyHolderResponse
import models.LoggedUser
import models.LoginResponse
import presentation.screen.login.SignInState

sealed class GetQuotesStates {
    data object None : GetQuotesStates()
    data object Loading : GetQuotesStates()
    data class CreatePolicyHolder(val response: CreatePolicyHolderResponse) : GetQuotesStates()
    data class OtpVerified(val response: LoggedUser) : GetQuotesStates()
    data class PasswordChanged(val message: String) : GetQuotesStates()
    data class Error(val message: String) : GetQuotesStates()
}