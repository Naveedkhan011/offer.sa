package presentation.screen.quotes_screen

import models.CreatePolicyHolderResponse
import models.LoggedUser
import models.showDriverByVehicleIdResponseItem
import models.showVehiclesByPolicyholderIdAndOwnerIdResponseItem

sealed class QuotesApiStates {
    data object None : QuotesApiStates()
    data object Loading : QuotesApiStates()
    data class CreatePolicyHolder(val vehicleList: MutableList<showVehiclesByPolicyholderIdAndOwnerIdResponseItem>) : QuotesApiStates()
    data class VehicleInfo(val driverList: MutableList<showDriverByVehicleIdResponseItem>) : QuotesApiStates()
    data class DriverInfo(val driverList: MutableList<showDriverByVehicleIdResponseItem>) : QuotesApiStates()
    data class QuotesList(val message: String) : QuotesApiStates()
    data class Payment(val message: String) : QuotesApiStates()
    data class Error(val message: String) : QuotesApiStates()
}