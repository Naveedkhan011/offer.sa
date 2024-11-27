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
import kotlinx.coroutines.launch
import models.AllDropDownValues
import models.CreatePolicyHolderBody
import models.CreatePolicyHolderResponse
import models.DataXXX
import models.ServicesResponse
import models.enums.InsuranceType
import network.Ktor
import utils.LogInManager

data class Driver(val name: String, val id: String)


class QuotesViewModel : ScreenModel {
    var allDropDownValues by mutableStateOf(AllDropDownValues())

    val purposeList = getData(allDropDownValues, 13)
    lateinit var months: DataXXX
    val years = getData(allDropDownValues, 37)

    private fun getData(allDropDownValues: AllDropDownValues, id: Int): DataXXX? {
        allDropDownValues.data?.let { it ->
            it.forEach {
                if (it.id == id)//38 arabic months
                    return it
            }
        }
        return null
    }


    var nationalID by mutableStateOf("")
    var nationalIDError by mutableStateOf<String?>(null)

    var sellerNationalId by mutableStateOf("")
    var sellerNationalIdError by mutableStateOf<String?>(null)

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
    var vehicleSpecificationsSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var selectedSheet by mutableStateOf(BottomSheetCaller.MONTH)

    var isLoading by mutableStateOf(false)

    var purposeOfUse by mutableStateOf("")
    var vehicleEstimatedValue by mutableStateOf("")  // State for dropdown menu


    // Driver List
    val drivers = mutableStateListOf(
        Driver(name = "كاشف تسنيم خان", id = "2537995140"),
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

    /*private val _uiState: MutableStateFlow<GetQuotesStates> = MutableStateFlow(GetQuotesStates())
    val uiState: StateFlow<GetQuotesStates> = _uiState
*/

    fun createPolicyHolder(insuranceType: InsuranceType) {
        screenModelScope.launch {
            try {
                val servicesResponse = Ktor.client.post("/portal-api/insurance/rest/serviceList") {
                    contentType(ContentType.Application.Json)
                    accept(ContentType.Application.Json)
                    setBody(
                        CreatePolicyHolderBody(
                            nationalId = nationalID,
                            sellerNationalId = sellerNationalId,
                            dobMonth = selectedMonth,
                            dobYear = selectedYear,
                            sequenceNumber = sequenceNumber,
                            clientId = null,
                            agentId = null,
                            userId = if (LogInManager.getLoggedInUser() != null || LogInManager.getLoggedInUser()!!.user != null) {
                                LogInManager.getLoggedInUser()!!.user!!.id
                            } else 0,
                            insuranceType = when (insuranceType) {
                                InsuranceType.INSURE_YOUR_VEHICLE -> "1"
                                InsuranceType.OWNER_TRANSFER -> "2"
                                InsuranceType.CUSTOM_CARD -> "3"
                            },
                            insuranceEffectiveDate = effectiveYear,
                            customCard = customCard,
                            manufactureYear = manufacturingYear,
                            referenceNo = "",
                            selectedTab = when (insuranceType) {
                                InsuranceType.INSURE_YOUR_VEHICLE -> "INSURE_YOUR_VEHICLE"
                                InsuranceType.OWNER_TRANSFER -> "OWNER_INSURANCE"
                                InsuranceType.CUSTOM_CARD -> "CUSTOM_CARD"
                            }
                        )
                    )
                }.body<CreatePolicyHolderResponse>()

                /*_uiState.value = GetQuotesStates(
                    apiStatus = ApiStatus.SUCCESS, servicesResponse = servicesResponse
                )*/
            } catch (e: Exception) {
                val servicesResponse = ServicesResponse()
                servicesResponse.message = if (e.message == null) "empty" else e.message!!

                /*_uiState.value = HomeUiState(
                    apiStatus = ApiStatus.ERROR, servicesResponse = servicesResponse
                )*/
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


    fun getAllDataFromServer() {
        screenModelScope.launch {
            isLoading = true
            try {
                val response =
                    Ktor.client.get("/portal-api/insurance/rest/showInsuranceCodeName") {}
                        .body<AllDropDownValues>()
                allDropDownValues = response
                val data = getData(allDropDownValues, 39)
                if (data != null)
                    months = data

            } catch (_: Exception) {

            } finally {
                isLoading = false
            }
        }
    }
}