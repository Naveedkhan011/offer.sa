package presentation.fragments.home

import dev.icerock.moko.mvvm.viewmodel.ViewModel
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import models.ServicesResponse
import network.Ktor
import models.enums.ApiStatus

class HomeFragmentViewModel() : ViewModel() {
    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    override fun onCleared() {
        //Ktor.client.close()
    }

    init {
        getServices()
    }

    fun getServices() {
        viewModelScope.launch {
            try {
                val servicesResponse = Ktor.client.get("/insurance/rest/serviceList")
                    .body<ServicesResponse>()
                _uiState.value = HomeUiState(
                    apiStatus = ApiStatus.SUCCESS,
                    servicesResponse = servicesResponse
                )
            } catch (e: Exception) {
                val servicesResponse = ServicesResponse()
                servicesResponse.message = if (e.message == null) "empty" else e.message!!

                _uiState.value = HomeUiState(
                    apiStatus = ApiStatus.ERROR,
                    servicesResponse = servicesResponse
                )
            }
        }
    }
}