package presentation.screen.quotes_screen

import models.CreateInvoiceResponse
import models.QuotesListResponse
import models.VehicleListModels.VehicleListModelItem
import models.showDriverByVehicleIdResponseItem
import models.showVehiclesByPolicyholderIdAndOwnerIdResponseItem

sealed class QuotesApiStates {
    data object None : QuotesApiStates()
    data object Loading : QuotesApiStates()
    data class CreatePolicyHolder(val vehicleList: MutableList<showVehiclesByPolicyholderIdAndOwnerIdResponseItem>) : QuotesApiStates()
    data class VehicleInfo(val vehicleList: MutableList<VehicleListModelItem>) : QuotesApiStates()
    data class DriverInfo(val driverList: MutableList<showDriverByVehicleIdResponseItem>) : QuotesApiStates()
    data class QuotesList(val message: QuotesListResponse) : QuotesApiStates()
    data class Payment(
        val invoice: CreateInvoiceResponse,
        val vehicleList: MutableList<VehicleListModelItem>, ) : QuotesApiStates()
    data class Error(val message: String) : QuotesApiStates()
}