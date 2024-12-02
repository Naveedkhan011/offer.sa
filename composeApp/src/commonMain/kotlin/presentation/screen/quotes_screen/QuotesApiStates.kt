package presentation.screen.quotes_screen

import models.CreatePolicyHolderResponse
import models.LoggedUser

sealed class QuotesApiStates {
    data object None : QuotesApiStates()
    data object Loading : QuotesApiStates()
    data class CreatePolicyHolder(val response: CreatePolicyHolderResponse) : QuotesApiStates()
    data class VehicleInfo(val response: LoggedUser) : QuotesApiStates()
    data class DriverInfo(val message: String) : QuotesApiStates()
    data class QuotesList(val message: String) : QuotesApiStates()
    data class Payment(val message: String) : QuotesApiStates()
    data class Error(val message: String) : QuotesApiStates()
}