package presentation.screen.quotes_screen

import models.ServicesResponse
import models.enums.ApiStatus

data class QuotesUiState(
    val apiStatus: ApiStatus = ApiStatus.LOADING,
    val servicesResponse: ServicesResponse = ServicesResponse()
)


