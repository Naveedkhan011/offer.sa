package presentation.fragments.home

import models.ServicesResponse
import models.enums.ApiStatus

data class HomeUiState(
    val apiStatus: ApiStatus = ApiStatus.LOADING,
    val servicesResponse: ServicesResponse = ServicesResponse()
)


