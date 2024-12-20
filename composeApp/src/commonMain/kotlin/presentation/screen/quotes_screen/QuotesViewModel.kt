package presentation.screen.quotes_screen

import SHARED_PREFERENCE
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import dropDownValues
import hideLoading
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import models.ChangeReferenceNoResponse
import models.CreateDriverBody
import models.CreateInvoiceResponse
import models.CreatePolicyHolderBody
import models.CreatePolicyHolderResponse
import models.ErrorResponse
import models.GeneralResponse
import models.QuotesListResponse
import models.ResponseTPL
import models.UpdateVehicleBodyDTO
import models.UpdateVehicleResponse
import models.VehicleListModels.VehicleListModelItem
import models.VehiclesSequenceIdResponse
import models.enums.InsuranceType
import models.showDriverByVehicleIdResponseItem
import models.showVehiclesByPolicyholderIdAndOwnerIdResponseItem
import models.ui_models.PolicyHolderUiData
import models.ui_models.VehicleUiData
import navigator
import network.Ktor
import network.createDriverEndPoint
import presentation.screen.login.LoginScreen
import showError
import showLoading
import utils.AppConstants
import utils.LogInManager
import utils.language.language_manager.LanguageManager

val json = Json { ignoreUnknownKeys = true }

enum class Tab(val title: String) {
    ThirdParty("Third Party"),
    OwnDamage("Own Damage"),
    Comprehensive("Comprehensive")
}

val currentLanguage = SHARED_PREFERENCE.getString(
    AppConstants.SharedPreferenceKeys.LANGUAGE
)

class QuotesViewModel : ScreenModel {

    override fun onDispose() {
        reset()
    }

    private fun reset() {

    }

    // ui model

    var currentStep by mutableStateOf(1)
    var policyHolderUiData by mutableStateOf(
        buildPolicyHolderUiData()
    )

    var vehicleUiData by mutableStateOf(
        buildVehicleUiData()
    )


    var createPolicyHolderResponse by mutableStateOf(CreatePolicyHolderResponse())
    var createInvoiceResponse by mutableStateOf(CreateInvoiceResponse())

    //vehicle info variables
    var vehicleData by mutableStateOf(
        UpdateVehicleBodyDTO()
    )


    var newSpecificationCodeIds = emptyList<Int>().toMutableStateList()
    var isFromInvoice = false;


    //driver info variables
    var driverList: MutableList<showDriverByVehicleIdResponseItem> = mutableListOf()
    var createDriver by mutableStateOf(CreateDriverBody())

    var dobMonth by mutableStateOf("")
    var dobYear by mutableStateOf("")
    var vehicleNightParking by mutableStateOf("")
    var driverRelationship by mutableStateOf("")
    var education by mutableStateOf("")
    var noOfChildrenBelow16 by mutableStateOf("")
    var driverBusinessCity by mutableStateOf("")
    var driverNOALastFiveYears by mutableStateOf("")
    var driverNocLastFiveYears by mutableStateOf("")
    var drivingLicenceCountry by mutableStateOf("")
    var licenceValidFor by mutableStateOf("")
    var privacyAccepted by mutableStateOf(false)
    var selectedPercentage by mutableStateOf(false)

    var isSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var isVehicleSpecificationSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var vehicleSpecificationsFieldsSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var addNewDriverSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var driverListSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var datePickerSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var policyDetailsSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var buyButtonClicked by mutableStateOf(false)  // State for dropdown menu

    var selectedSheet by mutableStateOf(BottomSheetCaller.MONTH)

    var buttonEnabled by mutableStateOf(false)  // State for dropdown menu
    var termsAndConditionAccepted by mutableStateOf(false)  // State for dropdown menu

    var selectedTab by mutableStateOf(Tab.ThirdParty)
    val tabs = listOf(Tab.ThirdParty, Tab.Comprehensive)

    private val _quotesApiStates: MutableStateFlow<QuotesUiState> =
        MutableStateFlow(QuotesUiState())
    val quotesApiStates: StateFlow<QuotesUiState> = _quotesApiStates


    // vehicle list
    var vehicleList: MutableList<VehicleListModelItem> = mutableListOf()
    var selectedBenefitsIds: MutableList<String> = mutableListOf()

    //quotes list
    var quotes by mutableStateOf(QuotesListResponse())
    var selectedQuote by mutableStateOf(ResponseTPL())

    fun createPolicyHolder(insuranceType: InsuranceType) {
        if (validFormValues(insuranceType)) {
            showLoading()
            screenModelScope.launch {
                try {

                    val body = CreatePolicyHolderBody(
                        nationalId = policyHolderUiData.nationalId,
                        sellerNationalId = policyHolderUiData.sellerNationalId.ifEmpty { null },
                        dobMonth = if (policyHolderUiData.dobMonth.code <= 9) "0" + policyHolderUiData.dobMonth.code.toString() else policyHolderUiData.dobMonth.code.toString(),
                        dobYear = policyHolderUiData.dobYear.description.en,
                        sequenceNumber = policyHolderUiData.sequenceNumber,
                        userId = if (LogInManager.getLoggedInUser() != null && LogInManager.getLoggedInUser()!!.user != null) {
                            LogInManager.getLoggedInUser()!!.user!!.id
                        } else 0,
                        insuranceType = policyHolderUiData.insuranceType,
                        insuranceEffectiveDate = policyHolderUiData.insuranceEffectiveDate,
                        customCard = policyHolderUiData.customCard.ifEmpty { null },
                        manufactureYear = if (policyHolderUiData.selectedTab == "INSURANCE_BY_CUSTOMS_CARD")
                            policyHolderUiData.manufactureYear.description.en else null,
                        referenceNo = if (createPolicyHolderResponse.data.referenceNo.isEmpty()) null
                        else createPolicyHolderResponse.data.referenceNo,
                        selectedTab = policyHolderUiData.selectedTab
                    )

                    val response =
                        Ktor.client.post("/portal-api/insurance/rest/createPolicyHolder") {
                            header(HttpHeaders.ContentType, ContentType.Application.Json)
                            header(HttpHeaders.Accept, ContentType.Application.Json)
                            setBody(body)
                        }.body<CreatePolicyHolderResponse>()

                    if (response.errorCode == null) {
                        createPolicyHolderResponse = response

                        showVehiclesByPolicyholderIdAndOwnerId(
                            createPolicyHolderResponse.data.id,
                            createPolicyHolderResponse.data.nationalId
                        )

                        getVehiclesSequenceId(
                            createPolicyHolderResponse.data.nationalId,
                            createPolicyHolderResponse.data.sequenceNumber
                        )

                    } else {
                        hideLoading()
                        showError(response.errorMessage.toString())
                    }
                } catch (e: Exception) {
                    hideLoading()
                    val message = if (e.message == null) "empty" else e.message!!
                    showError(message)
                }
            }
        }
    }

    private fun showVehiclesByPolicyholderIdAndOwnerId(policyHolderID: Int, nationalID: String) {

        screenModelScope.launch {
            try {
                val response =
                    Ktor.client.get("/portal-api/insurance/rest/showVehiclesByPolicyholderIdAndOwnerId/$policyHolderID/$nationalID")

                try {
                    val responseBody = response.bodyAsText()
                    vehicleList =
                        json.decodeFromString(
                            kotlinx.serialization.builtins.ListSerializer(
                                VehicleListModelItem.serializer()
                            ),
                            responseBody
                        ).toMutableList()

                    if (isFromInvoice) {
                        _quotesApiStates.value = QuotesUiState(
                            apiStatus = QuotesApiStates.Payment(createInvoiceResponse, vehicleList)
                        )
                    } else {
                        vehicleData =
                            vehicleData.copy(id = vehicleList[0].id)

                        _quotesApiStates.value = QuotesUiState(
                            apiStatus = QuotesApiStates.VehicleInfo(vehicleList)
                        )
                    }


                } catch (e: SerializationException) {
                    try {
                        val errorResponse: ErrorResponse =
                            Json.decodeFromString(ErrorResponse.serializer(), response.bodyAsText())
                        showError(errorResponse.errorMessage)
                    } catch (ex: SerializationException) {
                        showError("Unexpected response format")
                    }
                }

                hideLoading()
            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "empty" else e.message!!
                showError(message)
            }
        }
    }

    private fun getVehiclesSequenceId(nationalID: String, sequenceID: String) {

        screenModelScope.launch {
            try {
                val response =
                    Ktor.client.get("/portal-api/insurance/rest/getVehiclesSequenceId/$sequenceID/$nationalID")

                try {
                    val responseBody = response.bodyAsText()

                    val getVehiclesSequenceResponse = Json.decodeFromString(
                        VehiclesSequenceIdResponse.serializer(),
                        responseBody
                    )
                    vehicleData =
                        vehicleData.copy(vehicleRegExpiryDate = getVehiclesSequenceResponse.vehicleInfo.registrationExpiryDate)

                } catch (e: SerializationException) {
                    showError("Unexpected response format")
                }

                hideLoading()

            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "empty" else e.message!!
                showError(message)
            }
        }
    }

    fun updateVehicle() {

        screenModelScope.launch {
            try {
                showLoading()
                val firstItem = vehicleList[0]
                vehicleData = vehicleData.copy(
                    capacity = firstItem.vehicleSeating,
                    manufactureYear = firstItem.vehicleModelYear,
                    policy_holder_id = createPolicyHolderResponse.data.id,
                    specificationCodeIds = ArrayList(newSpecificationCodeIds),
                    vehicleMajorColorCode = firstItem.vehicleMajorColorCode!!,
                    identificationType = if (selectedInsuranceType == InsuranceType.CUSTOM_CARD) 2 else 1,// for sequence number 2 for custom card
                    vehicleOvernightParkingLocationCode = vehicleUiData.vehicleOvernightParkingLocation.code,
                    transferOwnership = 0,
                    accidentCount = vehicleUiData.accidentCount.description.en,
                    modification = vehicleUiData.vehicleModification.description.en,
                    vehicleModificationDetails = vehicleUiData.vehicleModificationDetails,
                    km = vehicleUiData.km.code,
                    transmission = vehicleUiData.transmission.code,
                    approved = vehicleUiData.approved,
                    insuranceType = vehicleUiData.insuranceType.code,
                    deductibleValue = 500,
                    vehicleAgencyRepair = if (vehicleUiData.insuranceType.code == 1) 0 else 1,
                    vehicleUseCode = vehicleUiData.vehicleUse.code,
                    vehicleValue = vehicleUiData.vehicleValue,
                    vehicleModification = vehicleUiData.vehicleModification.code == 2
                )

                val response = Ktor.client.post("/portal-api/insurance/rest/updateVehicle") {
                    header(HttpHeaders.ContentType, ContentType.Application.Json)
                    header(HttpHeaders.Accept, ContentType.Application.Json)
                    LogInManager.getLoggedInUser()?.token?.let { token ->
                        header(HttpHeaders.Authorization, "Bearer $token")
                    }
                    setBody(vehicleData)
                }

                val errorCode = response.status.value
                when (errorCode) {
                    200 -> {
                        try {
                            val data = json.decodeFromString(
                                UpdateVehicleResponse.serializer(),
                                response.bodyAsText()
                            )

                            if (data.success) {
                                showDriverByVehicleId(
                                    firstItem.id,
                                    createPolicyHolderResponse.data.id.toString()
                                )
                            } else {
                                showError(data.message)
                            }

                        } catch (e: Exception) {
                            showError("Unexpected Response")
                        }

                    }

                    401 -> {
                        LogInManager.setLoggedInValue(false)
                        navigator.push(LoginScreen())
                        showError(LanguageManager.currentStrings.unauthenticated)
                    }

                    else -> {
                        val error = Json.decodeFromString(
                            ErrorResponse.serializer(),
                            response.bodyAsText()
                        )
                        showError(error.errorMessage)
                    }
                }

                hideLoading()
            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "empty" else e.message!!
                showError(message)
            }
        }
    }

    /*private fun mapToNewUpdateVehicleBody(source: UpdateVehicleBody): UpdateVehicleBodyDTO {
        return UpdateVehicleBodyDTO(
            accidentCount = source.accidentCount,
            approved = source.approved,
            capacity = source.capacity,
            deductibleValue = source.deductibleValue,
            id = source.id,
            identificationType = vehicleData.identificationType!!,
            insuranceType = source.insuranceType,
            km = source.km,
            manufactureYear = source.manufactureYear,
            modification = source.modification,
            policy_holder_id = source.policyHolderId,
            specificationCodeIds = ArrayList(source.specificationCodeIds), // Deep copy the list
            transferOwnership = source.transferOwnership,
            transmission = source.transmission,
            vehicleAgencyRepair = source.vehicleAgencyRepair,
            vehicleMajorColorCode = source.vehicleMajorColorCode,
            vehicleModification = source.vehicleModification,
            vehicleModificationDetails = source.vehicleModificationDetails,
            vehicleOvernightParkingLocationCode = source.vehicleOvernightParkingLocationCode,
            vehicleRegExpiryDate = source.vehicleRegExpiryDate,
            vehicleUseCode = source.vehicleUseCode,
            vehicleValue = source.vehicleValue
        )
    }*/

    @Serializable
    data class CreateInvoiceModel(
        val companyCode: String,
        val deductibleValue: String?,
        val insuranceTypeId: Int?,
        val referenceNo: String,
        val selectedBenifitIds: List<String>,
        val userid: Int,
    )

    fun createInvoice(
        companyCode: Int,
        deductibleValue: String?,
        selectedBenefitIds: List<String>
    ) {

        val createInvoiceModel = CreateInvoiceModel(
            companyCode = companyCode.toString(),
            deductibleValue = deductibleValue,
            insuranceTypeId = 1,
            referenceNo = createPolicyHolderResponse.data.referenceNo,
            selectedBenifitIds = selectedBenefitIds,
            userid = if (LogInManager.getLoggedInUser() != null && LogInManager.getLoggedInUser()!!.user != null) {
                LogInManager.getLoggedInUser()!!.user!!.id
            } else 0
        )

        screenModelScope.launch {
            try {
                showLoading()
                val response =
                    Ktor.client.post("/portal-api/insurance/rest/createInvoice") {
                        header(HttpHeaders.ContentType, ContentType.Application.Json)
                        header(HttpHeaders.Accept, ContentType.Application.Json)
                        setBody(createInvoiceModel)
                    }

                if (response.status.isSuccess()) {
                    createInvoiceResponse = Json.decodeFromString(
                        CreateInvoiceResponse.serializer(),
                        response.bodyAsText()
                    )

                    isFromInvoice = true
                    showVehiclesByPolicyholderIdAndOwnerId(
                        createPolicyHolderResponse.data.id,
                        createPolicyHolderResponse.data.nationalId
                    )
                } else{
                    hideLoading()
                    showError("error while creating invoice")
                }
            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "empty" else e.message!!
                showError(message)
            }
        }
    }


    fun createDriver() {

        showLoading()
        screenModelScope.launch {
            try {

                val response =
                    Ktor.client.post(createDriverEndPoint) {
                        setBody(createDriver)
                    }

                val responseBody = response.bodyAsText()

                try {
                    val createDriverResponse: GeneralResponse =
                        Json.decodeFromString(GeneralResponse.serializer(), responseBody)
                    showError(createDriverResponse.message.toString())
                } catch (ex: SerializationException) {
                    showError("Unexpected response format")
                }

                hideLoading()
            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "empty" else e.message!!
                showError(message)
            }
        }
    }

    private fun showDriverByVehicleId(vehicleId: Int, policyHolderId: String) {

        screenModelScope.launch {
            try {
                val response =
                    Ktor.client.get("/portal-api/insurance/rest/showDriverByVehicleId/$vehicleId/$policyHolderId")
                //.body<ArrayList<showVehiclesByPolicyholderIdAndOwnerIdResponseItem>>()

                try {
                    val responseBody = response.bodyAsText()

                    driverList =
                        Json.decodeFromString(
                            kotlinx.serialization.builtins.ListSerializer(
                                showDriverByVehicleIdResponseItem.serializer()
                            ),
                            responseBody
                        ).toMutableList()

                    _quotesApiStates.value = QuotesUiState(
                        apiStatus = QuotesApiStates.DriverInfo(driverList)
                    )

                    updateDriverPercentage(100)

                } catch (e: SerializationException) {
                    hideLoading()
                    try {
                        val errorResponse: ErrorResponse =
                            Json.decodeFromString(ErrorResponse.serializer(), response.bodyAsText())
                        showError(errorResponse.errorMessage)
                    } catch (ex: SerializationException) {
                        showError("un Expected Error")
                    }
                }

            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "empty" else e.message!!
                showError(message)
            }
        }
    }

    fun updateDriverPercentage(percentage: Int) {

        screenModelScope.launch {
            try {
                val response =
                    Ktor.client.post("/portal-api/insurance/rest/updateDriverPercentage?referenceNo=${createPolicyHolderResponse.data.referenceNo}") {
                        header(HttpHeaders.ContentType, ContentType.Application.Json)
                        header(HttpHeaders.Accept, ContentType.Application.Json)
                        setBody(
                            mapOf(
                                createPolicyHolderResponse.data.nationalId to percentage
                            )
                        )
                    }/*.body<UpdateVehicleResponse>()*/

                if (response.status.isSuccess()) {
                    val updatedDriverList = Json.decodeFromString(
                        kotlinx.serialization.builtins.ListSerializer(
                            showDriverByVehicleIdResponseItem.serializer()
                        ),
                        response.bodyAsText()
                    ).toMutableList()

                    // getQuotesList(createPolicyHolderResponse.data.referenceNo, false)
                } else {
                    val error =
                        Json.decodeFromString(ErrorResponse.serializer(), response.bodyAsText())
                    showError(error.errorMessage)
                }
            } catch (e: Exception) {
                val message = if (e.message == null) "unknown error" else e.message!!
                showError(message)
            }
        }
    }

    private fun getQuotesList(referenceNo: String, isRecursiveCall: Boolean) {
        if (!isRecursiveCall)
            showLoading()

        screenModelScope.launch {
            try {
                val response =
                    Ktor.client.get("/portal-api/insurance/rest/ica-get-quotesErrorFree/$referenceNo")
                        .body<QuotesListResponse>()

                if (response.errorCode == null) {
                    quotes = response
                    _quotesApiStates.value = QuotesUiState(
                        apiStatus = QuotesApiStates.QuotesList(quotes)
                    )

                    delay(1000)
                    if (!response.completed)
                        getQuotesList(referenceNo, true)
                } else
                    showError(response.errorMessage.toString())

                hideLoading()
            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "unknown error" else e.message!!
                showError(message)
            }
        }
    }

    public fun changeReferenceNoOnQuotation(referenceNo: String, policyHolderId: Int) {
        screenModelScope.launch {
            try {
                showLoading()

                val response =
                    Ktor.client.get("/portal-api/insurance/rest/changeReferenceNoOnQuotation/$referenceNo/$policyHolderId")

                try {
                    val data = json.decodeFromString(
                        ChangeReferenceNoResponse.serializer(),
                        response.bodyAsText()
                    )
                    if (data.success) {
                        createPolicyHolderResponse.data.referenceNo =
                            data.data?.policyHolder?.referenceNo!!
                        getQuotesList(createPolicyHolderResponse.data.referenceNo, false)
                    } else {
                        hideLoading()
                        showError(data.message)
                    }

                } catch (e: Exception) {
                    hideLoading()
                    showError("failed to update some information")
                }

            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "unknown error" else e.message!!
                showError(message)
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
            if (policyHolderUiData.customCard.isEmpty()) {
                isValid = false
                policyHolderUiData.customCardError = "Field is required"
            }

            if (policyHolderUiData.manufactureYear.description.en.isEmpty()) {
                isValid = false
                policyHolderUiData.manufactureYearError = "Field is required"
            }
        }

        return isValid
    }

    fun verifyIqamaSellerLocally(): Boolean {
        policyHolderUiData.sellerNationalIdError =
            if (policyHolderUiData.sellerNationalId.isEmpty()) {
                buttonEnabled = false
                "National ID is required"
            } else if (!policyHolderUiData.sellerNationalId.startsWith("1") && !policyHolderUiData.sellerNationalId.startsWith(
                    "2"
                )
            ) {
                "invalid national id"
            } else if (policyHolderUiData.sellerNationalId.length < 10) {
                "ID should be 10 character long"
            } else {
                null
            }

        return policyHolderUiData.nationalIDError == null
    }

    fun verifyIqamaLocally(): Boolean {
        policyHolderUiData = policyHolderUiData.copy(
            nationalIDError = if (policyHolderUiData.nationalId.isEmpty()) {
                buttonEnabled = false
                "National ID is required"
            } else if (!policyHolderUiData.nationalId.startsWith("1") && !policyHolderUiData.nationalId.startsWith(
                    "2"
                )
            ) {
                "invalid national id"
            } else if (policyHolderUiData.nationalId.length < 10) {
                "ID should be 10 character long"
            } else {
                null
            }
        )
        return policyHolderUiData.nationalIDError == null
    }

    fun verifySequenceNumberLocally(): Boolean {
        policyHolderUiData = policyHolderUiData.copy(
            sequenceNumberError = if (policyHolderUiData.sequenceNumber.isEmpty()) {
                "Sequence number is required"
            } else if (policyHolderUiData.sequenceNumber.length < 8) {
                "ID should be 10 character long"
            } else {
                null
            }
        )

        return policyHolderUiData.sequenceNumberError == null
    }

    fun searchUserByNationalId() {
        screenModelScope.launch {
            showLoading()
            try {
                // Replace with your base Ktor client
                val response =
                    Ktor.client.get("/portal-api/insurance/rest/foreignEqamaSearch/${policyHolderUiData.nationalId}") {
                        url {
                            parameters.append(
                                "dob",
                                "${policyHolderUiData.dobYear.description.en}-${policyHolderUiData.dobMonth.description.en}"
                            )
                        }
                    }

            } catch (e: Exception) {
                println()
            } finally {
                hideLoading()
            }
        }
    }


    private fun buildVehicleData(): VehicleUiData {
        return VehicleUiData(

        )
    }

    private fun buildPolicyHolderUiData(): PolicyHolderUiData {

        var policyHolderUiData = PolicyHolderUiData(
            insuranceType = "2",
            manufactureYear = dropDownValues.manufactureYear.insuranceTypeCodeModels[0],
            selectedTab = when (selectedInsuranceType) {
                InsuranceType.INSURE_YOUR_VEHICLE -> "OWNER_INSURANCE"
                InsuranceType.OWNER_TRANSFER -> "TRANSFER_OWNERSHIP"
                InsuranceType.CUSTOM_CARD -> "INSURANCE_BY_CUSTOMS_CARD"
            }
        )

        policyHolderUiData = policyHolderUiData.copy(
            dobMonth = if (policyHolderUiData.nationalId.startsWith("1"))
                dropDownValues.monthsArabic.insuranceTypeCodeModels[0]
            else dropDownValues.monthsEnglish.insuranceTypeCodeModels[0],
            dobYear = if (policyHolderUiData.nationalId.startsWith("1"))
                dropDownValues.arabicYears.insuranceTypeCodeModels[0]
            else dropDownValues.englishYears.insuranceTypeCodeModels[0]
        )

        return policyHolderUiData
    }


    private fun buildVehicleUiData(): VehicleUiData {
        return VehicleUiData(
            vehicleUse = dropDownValues.vehiclePurposeList.insuranceTypeCodeModels[0],
            insuranceType = dropDownValues.productType.insuranceTypeCodeModels[0],
            km = dropDownValues.vehicleMileageExpectedAnnual.insuranceTypeCodeModels[0],
            vehicleOvernightParkingLocation = dropDownValues.vehicleParking.insuranceTypeCodeModels[0],
            accidentCount = dropDownValues.accidentCount.insuranceTypeCodeModels[0],
            transmission = dropDownValues.transmissionType.insuranceTypeCodeModels[0],
            vehicleModification = dropDownValues.modificationTypes.insuranceTypeCodeModels[0]
        )
    }

}