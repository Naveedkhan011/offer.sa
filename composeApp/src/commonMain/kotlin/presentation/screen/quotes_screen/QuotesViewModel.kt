package presentation.screen.quotes_screen

import SHARED_PREFERENCE
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dropDownValues
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import isLoading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import models.CreatePolicyHolderBody
import models.CreatePolicyHolderResponse
import models.DataXXX
import models.Description
import models.DriverByVehicleIdResponse
import models.DriverByVehicleIdResponseItem
import models.GeneralResponse
import models.InsuranceTypeCodeModel
import models.QuotesListResponse
import models.enums.InsuranceType
import network.Ktor
import utils.AppConstants
import utils.LogInManager

enum class Tab(val title: String) {
    ThirdParty("Third Party"),
    OwnDamage("Own Damage"),
    Comprehensive("Comprehensive")
}

var quotes by mutableStateOf(
    listOf(
        Quote("ACIG One", "SAR 2,661.66", listOf("Loss or Damage to the Insured Car", "Liability to Third Parties", "Natural Disasters"), "404.03 SAR Total discount"),
        Quote("SANAD Plus", "SAR 1,760.65", listOf("Estimated activation time: 12+ hours", "Third-party coverage"), "No discount applied")
    )
)

val currentLanguage = SHARED_PREFERENCE.getString(
    AppConstants.SharedPreferenceKeys.LANGUAGE
)

data class Driver(val name: String, val id: String)


class QuotesViewModel : ScreenModel {

    var selectedTab by mutableStateOf(Tab.ThirdParty)
    val tabs = listOf(Tab.ThirdParty, Tab.Comprehensive)

    val purposeList: DataXXX? = dropDownValues.getData(13)
    val months: DataXXX? = dropDownValues.getData(if (currentLanguage == "en") 39 else 38)
    val years: DataXXX? = dropDownValues.getData(37)
    val vehicleSpecifications: DataXXX? = dropDownValues.getData(29)
    val vehicleParking: DataXXX? = dropDownValues.getData(25)
    val vehicleMileageExpectedAnnual: DataXXX? = dropDownValues.getData(30)
    val transmissionType: DataXXX? = dropDownValues.getData(26)
    val modificationTypes: DataXXX? = dropDownValues.getData(40)
    val accidentCount: DataXXX =
        DataXXX(id = 1, name = "Accident Count", insuranceTypeCodeModels = getAccidentCountList())
    val noOfChildren: DataXXX =
        DataXXX(id = 1, name = "No of childrens", insuranceTypeCodeModels = getChildrenCount())
    val driverRelation: DataXXX? = dropDownValues.getData(14)
    val healthConditionList: DataXXX? = dropDownValues.getData(41)
    val trafficViolationList: DataXXX? = dropDownValues.getData(24)
    val driverBusinessCityList: DataXXX? = dropDownValues.getData(32)
    val drivingLicenceCountryList: DataXXX? = dropDownValues.getData(33)
    val educationList: DataXXX? = dropDownValues.getData(12)
    val productType: DataXXX? = dropDownValues.getData(5)

    var createPolicyHolderBody by mutableStateOf(CreatePolicyHolderBody())
    private var createPolicyHolderResponse by mutableStateOf(CreatePolicyHolderResponse())
    private var driverList by mutableStateOf(ArrayList<DriverByVehicleIdResponseItem>())


    var expectedKM by mutableStateOf(
        vehicleMileageExpectedAnnual?.insuranceTypeCodeModels?.get(0)?.description?.en
            ?: ""
    )
    var vehicleParkedAtNight by mutableStateOf(
        vehicleParking?.insuranceTypeCodeModels?.get(0)?.description?.en
            ?: ""
    )
    var accidentCountV by mutableStateOf("0")
    var modificationV by mutableStateOf(
        modificationTypes?.insuranceTypeCodeModels?.get(0)?.description?.en
            ?: ""
    )

    var reasonForModification by mutableStateOf("")

    var transmissionTypeV by mutableStateOf(
        transmissionType?.insuranceTypeCodeModels?.get(0)?.description?.en
            ?: ""
    )
    var specification by mutableStateOf("")
    var claim by mutableStateOf("")


    var nationalID by mutableStateOf("2537995140")
    var nationalIDError by mutableStateOf<String?>(null)

    var sellerNationalId by mutableStateOf("2537995140")
    var sellerNationalIdError by mutableStateOf<String?>(null)

    var selectedMonth by mutableStateOf(
        months?.insuranceTypeCodeModels?.get(0)?.description?.en ?: ""
    )
    var dobError by mutableStateOf<String?>(null)

    var selectedYear by mutableStateOf(
        years?.insuranceTypeCodeModels?.get(0)?.description?.en ?: ""
    )
    var dobYearError by mutableStateOf<String?>(null)

    var sequenceNumber by mutableStateOf("983236710")
    var sequenceNumberError by mutableStateOf<String?>(null)

    var customCard by mutableStateOf("")
    var customCardError by mutableStateOf<String?>(null)

    var manufacturingYear by mutableStateOf("")
    var manufacturingYearError by mutableStateOf<String?>(null)

    var effectiveYear by mutableStateOf("2024-12-10")
    var effectiveYearError by mutableStateOf<String?>(null)

    var isSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var isVehicleSpecificationSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var vehicleSpecificationsFieldsSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var addNewDriverSheetVisible by mutableStateOf(false)  // State for dropdown menu

    var selectedSheet by mutableStateOf(BottomSheetCaller.MONTH)
    var purposeOfUse by mutableStateOf("")
    var vehicleEstimatedValue by mutableStateOf("")  // State for dropdown menu

    var buttonEnabled by mutableStateOf(false)  // State for dropdown menu


    private val _quotesApiStates: MutableStateFlow<QuotesUiState> =
        MutableStateFlow(QuotesUiState())
    val quotesApiStates: StateFlow<QuotesUiState> = _quotesApiStates


    // Driver List
    val drivers = mutableStateListOf(
        Driver(name = "كاشف تسنيم خان", id = "2537995140"),
    )


    // edit driver list variables

    var driverId by mutableStateOf("")
    var dobMonth by mutableStateOf("")
    var dobYear by mutableStateOf("")
    var vehicleNightParking by mutableStateOf("")
    var driverRelationship by mutableStateOf("")
    var education by mutableStateOf("")
    var noOfChildrenBelow16 by mutableStateOf("")
    var healthCondition by mutableStateOf("")
    var trafficViolations by mutableStateOf("")
    var driverBusinessCity by mutableStateOf("")
    var driverNOALastFiveYears by mutableStateOf("")
    var driverNocLastFiveYears by mutableStateOf("")
    var drivingLicenceCountry by mutableStateOf("")
    var licenceValidFor by mutableStateOf("")


    fun editDrivers() {
        // Handle edit driver action here
    }

    /*private val _uiState: MutableStateFlow<GetQuotesStates> = MutableStateFlow(GetQuotesStates())
    val uiState: StateFlow<GetQuotesStates> = _uiState
*/

    fun checkButtonVisibility() {
        buttonEnabled =
            verifyIqamaLocally() && verifySequenceNumberLocally() && effectiveYear.isNotEmpty()

    }

    fun testing(insuranceType: InsuranceType) {
        if (validFormValues(insuranceType)) {
            screenModelScope.launch {
                try {

                    val body = CreatePolicyHolderBody(
                        nationalId = nationalID,
                        sellerNationalId = sellerNationalId.ifEmpty { "null" },
                        dobMonth = selectedMonth.substring(1, 3),
                        dobYear = selectedYear,
                        sequenceNumber = sequenceNumber,
                        userId = if (LogInManager.getLoggedInUser() != null && LogInManager.getLoggedInUser()!!.user != null) {
                            LogInManager.getLoggedInUser()!!.user!!.id.toString()
                        } else "15",
                        insuranceType = when (insuranceType) {
                            InsuranceType.INSURE_YOUR_VEHICLE -> "2"
                            InsuranceType.OWNER_TRANSFER -> "2"
                            InsuranceType.CUSTOM_CARD -> "2"
                        },
                        insuranceEffectiveDate = effectiveYear,
                        customCard = customCard.ifEmpty { "null" },
                        manufactureYear = manufacturingYear.ifEmpty { "null" },
                        referenceNo = "null",
                        selectedTab = when (insuranceType) {
                            InsuranceType.INSURE_YOUR_VEHICLE -> "OWNER_INSURANCE"
                            InsuranceType.OWNER_TRANSFER -> "TRANSFER_OWNERSHIP"
                            InsuranceType.CUSTOM_CARD -> "INSURANCE_BY_CUSTMOS_CARD"
                        }
                    )

                    val jsonString = Json.encodeToString(CreatePolicyHolderBody.serializer(), body)

                    val response = Ktor.client.post("/portal-api/insurance/rest/serviceList") {
                        contentType(ContentType.Application.Json) // Set Content-Type to JSON
                        setBody(jsonString) // Send the JSON string as the body
                    }.body<CreatePolicyHolderResponse>()

                    if (response.errorCode == null) {
                        createPolicyHolderResponse = response
                        _quotesApiStates.value = QuotesUiState(
                            apiStatus = QuotesApiStates.CreatePolicyHolder(response)
                        )
                    } else
                        _quotesApiStates.value = QuotesUiState(
                            apiStatus = QuotesApiStates.Error(response.errorMessage.toString())
                        )
                } catch (e: Exception) {
                    val message = if (e.message == null) "empty" else e.message!!
                    _quotesApiStates.value = QuotesUiState(
                        apiStatus = QuotesApiStates.Error(message)
                    )
                }
            }
        }
    }

    fun getQuotesList(){
        screenModelScope.launch {
            try {
                val response = Ktor.client.get("portal-api/insurance/rest/ica-get-quotesErrorFree/L1B12W0FCK01")
                    .body<QuotesListResponse>()

                if (response.errorCode == null) {
                    /*_quotesApiStates.value = QuotesUiState(
                        apiStatus = QuotesApiStates.CreatePolicyHolder(response)
                    )*/
                } else
                    _quotesApiStates.value = QuotesUiState(
                        apiStatus = QuotesApiStates.Error(response.errorMessage.toString())
                    )

            } catch (e: Exception) {
                val message = if (e.message == null) "empty" else e.message!!
                _quotesApiStates.value = QuotesUiState(
                    apiStatus = QuotesApiStates.Error(message)
                )
            }
        }
    }

    fun showDriverByVehicleId(){

        screenModelScope.launch {
            try {
                val response = Ktor.client.get("portal-api/insurance/rest/showDriverByVehicleId/384/384")
                    .body<ArrayList<DriverByVehicleIdResponseItem>>()

                // Check the type of the response
                when (response) {
                    is List<*> -> {
                        println("success")
                        driverList = response as ArrayList<DriverByVehicleIdResponseItem>
                    }
                    /*is DriverByVehicleIdResponse -> {
                        println("error")
                    }*/
                    else -> {
                        println("error")

                        val driverByVehicleIdResponse = response as DriverByVehicleIdResponse
                        _quotesApiStates.value = QuotesUiState(
                            apiStatus = QuotesApiStates.Error(driverByVehicleIdResponse.errorMessage.toString())
                        )
                    }
                }
            } catch (e: Exception) {
                val message = if (e.message == null) "empty" else e.message!!
                _quotesApiStates.value = QuotesUiState(
                    apiStatus = QuotesApiStates.Error(message)
                )
            }
        }


    }
fun createPolicyHolder(insuranceType: InsuranceType) {
        if (validFormValues(insuranceType)) {
            screenModelScope.launch {
                try {

                    val body = CreatePolicyHolderBody(
                        nationalId = nationalID,
                        sellerNationalId = sellerNationalId.ifEmpty { null },
                        dobMonth = selectedMonth.substring(1, 3),
                        dobYear = selectedYear,
                        sequenceNumber = sequenceNumber,
                        userId = if (LogInManager.getLoggedInUser() != null && LogInManager.getLoggedInUser()!!.user != null) {
                            LogInManager.getLoggedInUser()!!.user!!.id.toString()
                        } else "15",
                        insuranceType = when (insuranceType) {
                            InsuranceType.INSURE_YOUR_VEHICLE -> "2"
                            InsuranceType.OWNER_TRANSFER -> "2"
                            InsuranceType.CUSTOM_CARD -> "2"
                        },
                        insuranceEffectiveDate = effectiveYear,
                        customCard = customCard.ifEmpty { null },
                        manufactureYear = manufacturingYear.ifEmpty { null },
                        referenceNo = null,
                        selectedTab = when (insuranceType) {
                            InsuranceType.INSURE_YOUR_VEHICLE -> "OWNER_INSURANCE"
                            InsuranceType.OWNER_TRANSFER -> "TRANSFER_OWNERSHIP"
                            InsuranceType.CUSTOM_CARD -> "INSURANCE_BY_CUSTMOS_CARD"
                        }
                    )

                    val response = Ktor.client.post("/portal-api/insurance/rest/serviceList") {
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                        setBody(body)
                    }.body<CreatePolicyHolderResponse>()

                    if (response.errorCode == null) {
                        createPolicyHolderResponse = response
                        _quotesApiStates.value = QuotesUiState(
                            apiStatus = QuotesApiStates.CreatePolicyHolder(response)
                        )
                    } else
                        _quotesApiStates.value = QuotesUiState(
                            apiStatus = QuotesApiStates.Error(response.errorMessage.toString())
                        )
                } catch (e: Exception) {
                    val message = if (e.message == null) "empty" else e.message!!
                    _quotesApiStates.value = QuotesUiState(
                        apiStatus = QuotesApiStates.Error(message)
                    )
                }
            }
        }
    }

    private fun validFormValues(insuranceType: InsuranceType): Boolean {
        var isValid = true
        if (!verifyIqamaLocally() || !verifySequenceNumberLocally()) {
            isValid = false
        }
        if (insuranceType == InsuranceType.OWNER_TRANSFER) {
            if (!verifyIqamaSellerLocally()) {
                isValid = false
            }
        }

        if (insuranceType == InsuranceType.CUSTOM_CARD) {
            if (customCard.isEmpty()) {
                isValid = false
                customCardError = "Field is required"
            }

            if (manufacturingYear.isEmpty()) {
                isValid = false
                manufacturingYearError = "Field is required"
            }
        }

        return isValid
    }

    fun verifyIqamaSellerLocally(): Boolean {
        sellerNationalIdError = if (sellerNationalId.isEmpty()) {
            buttonEnabled = false
            "National ID is required"
        } else if (!sellerNationalId.startsWith("1") && !sellerNationalId.startsWith("2")) {
            "invalid national id"
        } else if (sellerNationalId.length < 10) {
            "ID should be 10 character long"
        } else {
            null
        }

        return nationalIDError == null
    }

    fun verifyIqamaLocally(): Boolean {
        nationalIDError = if (nationalID.isEmpty()) {
            buttonEnabled = false
            "National ID is required"
        } else if (!nationalID.startsWith("1") && !nationalID.startsWith("2")) {
            "invalid national id"
        } else if (nationalID.length < 10) {
            "ID should be 10 character long"
        } else {
            null
        }

        return nationalIDError == null
    }


    fun verifySequenceNumberLocally(): Boolean {
        sequenceNumberError = if (sequenceNumber.isEmpty()) {
            "Sequence number is required"
        } else if (sequenceNumber.length < 8) {
            "ID should be 10 character long"
        } else {
            null
        }

        return sequenceNumberError == null
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

    private fun getChildrenCount(): List<InsuranceTypeCodeModel?> {
        return listOf(
            InsuranceTypeCodeModel(description = Description(en = "1", ar = "1")),
            InsuranceTypeCodeModel(description = Description(en = "2", ar = "2")),
            InsuranceTypeCodeModel(description = Description(en = "3", ar = "3")),
            InsuranceTypeCodeModel(description = Description(en = "4", ar = "4")),
            InsuranceTypeCodeModel(description = Description(en = "5", ar = "5"))
        )
    }

    private fun getAccidentCountList(): List<InsuranceTypeCodeModel?> {
        return listOf(
            InsuranceTypeCodeModel(description = Description(en = "1", ar = "1")),
            InsuranceTypeCodeModel(description = Description(en = "2", ar = "2")),
            InsuranceTypeCodeModel(description = Description(en = "3", ar = "3")),
            InsuranceTypeCodeModel(description = Description(en = "4", ar = "4")),
            InsuranceTypeCodeModel(description = Description(en = "5", ar = "5")),
            InsuranceTypeCodeModel(description = Description(en = "6", ar = "6")),
            InsuranceTypeCodeModel(description = Description(en = "7", ar = "7")),
            InsuranceTypeCodeModel(description = Description(en = "8", ar = "8")),
            InsuranceTypeCodeModel(description = Description(en = "9", ar = "9")),
            InsuranceTypeCodeModel(description = Description(en = "10", ar = "10")),
        )
    }
}