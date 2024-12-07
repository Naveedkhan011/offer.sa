package presentation.screen.quotes_screen

import models.InvoiceResponse
import models.QuotesListResponse
import models.showDriverByVehicleIdResponseItem
import models.showVehiclesByPolicyholderIdAndOwnerIdResponseItem

sealed class QuotesApiStates {
    data object None : QuotesApiStates()
    data object Loading : QuotesApiStates()
    data class CreatePolicyHolder(val vehicleList: MutableList<showVehiclesByPolicyholderIdAndOwnerIdResponseItem>) : QuotesApiStates()
    data class VehicleInfo(val vehicleList: MutableList<showVehiclesByPolicyholderIdAndOwnerIdResponseItem>) : QuotesApiStates()
    data class DriverInfo(val driverList: MutableList<showDriverByVehicleIdResponseItem>) : QuotesApiStates()
    data class QuotesList(val message: QuotesListResponse) : QuotesApiStates()
    data class Payment(val invoice: InvoiceResponse) : QuotesApiStates()
    data class Error(val message: String) : QuotesApiStates()
}