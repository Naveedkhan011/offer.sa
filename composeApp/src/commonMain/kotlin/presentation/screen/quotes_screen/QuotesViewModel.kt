package presentation.screen.quotes_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import models.ServicesResponse
import models.CreatePolicyHolderBody
import models.CreatePolicyHolderResponse
import models.enums.ApiStatus
import network.Ktor
import presentation.fragments.home.HomeUiState
import presentation.screen.login.LoginUiState

data class Driver(val name: String, val id: String)


class QuotesViewModel : ScreenModel {

    val purposeList = listOf(
        "Private", "Comprehensive"
    )
    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val years = (1900..2024).toList().map { it.toString() }


    var nationalID by mutableStateOf("")
    var nationalIDError by mutableStateOf<String?>(null)

    var nationalIDSeller by mutableStateOf("")
    var nationalIDSellerError by mutableStateOf<String?>(null)

    var selectedMonth by mutableStateOf("month")
    var dobError by mutableStateOf<String?>(null)

    var selectedYear by mutableStateOf("year")
    var dobYearError by mutableStateOf<String?>(null)

    var sequenceNumber by mutableStateOf("")
    var sequenceNumberError by mutableStateOf<String?>(null)

    var customCard by mutableStateOf("")
    var customCardError by mutableStateOf<String?>(null)

    var manufacturingYear by mutableStateOf("")
    var manufacturingYearError by mutableStateOf<String?>(null)

    var effectiveYear by mutableStateOf("")
    var effectiveYearError by mutableStateOf<String?>(null)

    var isSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var selectedSheet by mutableStateOf(BottomSheetCaller.MONTH)

    var isLoading by mutableStateOf(false)

    var purposeOfUse by mutableStateOf(purposeList[0])
    var vehicleEstimatedValue by mutableStateOf("")  // State for dropdown menu


    // Driver List
    val drivers = mutableStateListOf(
        Driver(name = "كاشف تسنيم خان", id = "2537995140")
    )

    // Other Details
    val otherDetailsVisible = mutableStateOf(false)

    // Functions to modify state
    fun toggleOtherDetailsVisibility() {
        otherDetailsVisible.value = !otherDetailsVisible.value
    }

    fun editDrivers() {
        // Handle edit driver action here
    }

    private val _uiState: MutableStateFlow<GetQuotesStates> = MutableStateFlow(GetQuotesStates())
    val uiState: StateFlow<GetQuotesStates> = _uiState


    fun createPolicyHolder() {
        screenModelScope.launch {
            try {
                val servicesResponse = Ktor.client.post("/portal-api/insurance/rest/serviceList"){
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                    setBody(CreatePolicyHolderBody())
                }.body<CreatePolicyHolderResponse>()

                _uiState.value = GetQuotesStates(
                    apiStatus = ApiStatus.SUCCESS, servicesResponse = servicesResponse
                )
            } catch (e: Exception) {
                val servicesResponse = ServicesResponse()
                servicesResponse.message = if (e.message == null) "empty" else e.message!!

                _uiState.value = HomeUiState(
                    apiStatus = ApiStatus.ERROR, servicesResponse = servicesResponse
                )
            }
        }
    }

    fun verifyIqamaLocally() {
        nationalIDError = if (nationalID.isEmpty()) {
            "National ID is required"
        } else if (!nationalID.startsWith("1") && !nationalID.startsWith("2")) {
            "invalid national id"
        } else if (nationalID.length < 10) {
            "ID should be 10 character long"
        } else {
            null
        }
    }


    fun verifySequenceNumberLocally() {
        sequenceNumberError = if (sequenceNumber.isEmpty()) {
            "Sequence number is required"
        } else if (sequenceNumber.length < 8) {
            "ID should be 10 character long"
        } else {
            null
        }
    }

    fun searchUserByNationalId() {
        screenModelScope.launch {
            isLoading = true
            try {
                // Replace with your base Ktor client
                val response =
                    Ktor.client.get("https://offer.sa/portal-api/insurance/rest/foreignEqamaSearch/$nationalID") {
                        url {
                            parameters.append("dob", "$selectedYear-$selectedMonth")
                        }
                    }

            } catch (e: Exception) {

            } finally {
                isLoading = false
            }
        }
    }

    fun getVehiclesSequenceId() {
        screenModelScope.launch {
            isLoading = true
            try {
                // Replace with your base Ktor client
                val response =
                    Ktor.client.get("https://offer.sa/portal-api/insurance/rest/foreignEqamaSearch/$nationalID") {
                        url {
                            parameters.append("dob", "$selectedYear-$selectedMonth")
                        }
                    }

            } catch (e: Exception) {

            } finally {
                isLoading = false
            }
        }
    }

    fun verifyForm() {


    }

}