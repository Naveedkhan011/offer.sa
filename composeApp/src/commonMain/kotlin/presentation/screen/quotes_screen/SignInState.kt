package presentation.screen.quotes_screen

import models.CreatePolicyHolderResponse
import models.LoggedUser

sealed class GetQuotesStates {
    data object None : GetQuotesStates()
    data object Loading : GetQuotesStates()
    data class CreatePolicyHolder(val response: CreatePolicyHolderResponse) : GetQuotesStates()
    data class VehicleInfo(val response: LoggedUser) : GetQuotesStates()
    data class DriverInfo(val message: String) : GetQuotesStates()
    data class QuotesList(val message: String) : GetQuotesStates()
    data class Payment(val message: String) : GetQuotesStates()
    data class Error(val message: String) : GetQuotesStates()
}