package presentation.screen.quotes_screen

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
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import models.BillingDetailsBody
import models.ChangeReferenceNoResponse
import models.CreateInvoiceResponse
import models.CreatePolicyHolderBody
import models.CreatePolicyHolderResponse
import models.ErrorResponse
import models.InsuranceTypeCodeModel
import models.PaymentOTPResponse
import models.QuotesListResponse
import models.ResponseTPL
import models.SearchUserByNationalIdResponse
import models.UpdateVehicleBodyDTO
import models.UpdateVehicleResponse
import models.VehicleListModels.VehicleListModelItem
import models.VehiclesSequenceIdResponse
import models.VerifyPaymentOTPResponse
import models.body_models.CreateDriverBody
import models.body_models.CreateInvoiceBody
import models.body_models.DriverLicense
import models.enums.InsuranceType
import models.showDriverByVehicleIdResponseItem
import models.ui_models.AddLicenseUiData
import models.ui_models.BillingDetailsUIData
import models.ui_models.CreateDriverUiModel
import models.ui_models.CreateInvoiceUiModel
import models.ui_models.PolicyHolderUiData
import models.ui_models.VehicleUiData
import navigator
import network.Ktor
import network.createDriverEndPoint
import network.createInvoiceEndPoint
import network.createPolicyHolderEndPoint
import network.deleteDriverEndPoint
import network.foreignIqamaSearch
import network.getChangeReferenceNumEndPoint
import network.getQuotesEndPoint
import network.getShowDriverEndPoint
import network.getVehiclesSequenceIdEndPoint
import network.getshowVehiclesByPolicyholderIdAndOwnerIdEndPoint
import network.otpVerifyAndCreateInvoiceEndPoint
import network.saudiHawiyahSearch
import network.sendOTPEndPoint
import network.updateVehicleEndPoint
import presentation.screen.payment.PaymentScreen
import showError
import showLoading
import showSuccessMessage
import utils.AppConstants
import utils.AppConstants.Companion.logoutAndNavigateToLoginScreen
import utils.LogInManager
import utils.language.language_manager.LanguageManager.currentLanguage

val json = Json {
    ignoreUnknownKeys = true
    encodeDefaults = true
}

enum class Tab(val title: String) {
    ThirdParty("Third Party"),
    OwnDamage("Own Damage"),
    Comprehensive("Comprehensive")
}


class QuotesViewModel : ScreenModel {

    override fun onDispose() {
        reset()
    }

    private fun reset() {
        currentStep = 1
    }

    // ui model
    var paymentOTPResponse by mutableStateOf(PaymentOTPResponse())
    var currentStep by mutableStateOf(1)
    var policyHolderUiData by mutableStateOf(buildPolicyHolderUiData())
    var updateVehicleBodyData by mutableStateOf(UpdateVehicleBodyDTO())

    var vehicleUiData by mutableStateOf(
        buildVehicleUiData()
    )

    var billingDetailsUIData by mutableStateOf(
        BillingDetailsUIData(
            billingCountry = dropDownValues.drivingLicenceCountryList.insuranceTypeCodeModels[0]
        )
    )

    var createPolicyHolderResponse by mutableStateOf(CreatePolicyHolderResponse())
    var createInvoiceResponse by mutableStateOf(CreateInvoiceResponse())

    var newSpecificationCodeIds = emptyList<Int>().toMutableStateList()
    private var isFromInvoice = false


    //driver info variables
    var driverList: MutableList<showDriverByVehicleIdResponseItem> = mutableListOf()
    var createDriver by mutableStateOf(buildDriverUiData())

    private fun buildDriverUiData(): CreateDriverUiModel {
        return CreateDriverUiModel(
            driverId = "1011004031",
            childrenBelow16 = dropDownValues.noOfChildren.insuranceTypeCodeModels[0],
            dobMonth = dropDownValues.monthsEnglish.insuranceTypeCodeModels[0],
            dobYear = dropDownValues.arabicYears.insuranceTypeCodeModels[0],
            driverAddressCity = dropDownValues.driverBusinessCityList.insuranceTypeCodeModels[0],
            driverBusinessCity = dropDownValues.driverBusinessCityList.insuranceTypeCodeModels[0],
            driverNoaLastFiveYears = dropDownValues.accidentCount.insuranceTypeCodeModels[0],
            driverNocLastFiveYears = dropDownValues.accidentCount.insuranceTypeCodeModels[0],
            driverRelationship = dropDownValues.driverRelation.insuranceTypeCodeModels[0],
            education = dropDownValues.educationList.insuranceTypeCodeModels[0],
            licenseCountryCode = dropDownValues.drivingLicenceCountryList.insuranceTypeCodeModels[0],
            vehicleNightParking = dropDownValues.vehicleParking.insuranceTypeCodeModels[0],
            driverTypeCode = dropDownValues.driverType.insuranceTypeCodeModels[1]
        )
    }

    var addNewDriverBody by mutableStateOf(CreateDriverBody())

    var privacyAccepted by mutableStateOf(false)
    var termsAndConditionAccepted by mutableStateOf(false)  // State for dropdown menu

    var isSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var paymentOTPVisible by mutableStateOf(false)  // State for dropdown menu
    var isVehicleSpecificationSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var vehicleSpecificationsFieldsSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var addNewDriverSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var driverListSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var datePickerSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var policyDetailsSheetVisible by mutableStateOf(false)  // State for dropdown menu
    var buyButtonClicked by mutableStateOf(false)  // State for dropdown menu

    var selectedSheet by mutableStateOf(BottomSheetCaller.MONTH)

    var buttonEnabled by mutableStateOf(false)  // State for dropdown menu

    var selectedTab by mutableStateOf(Tab.ThirdParty)
    val tabs = listOf(Tab.ThirdParty, Tab.Comprehensive)

    private val _quotesApiStates: MutableStateFlow<QuotesUiState> =
        MutableStateFlow(QuotesUiState())
    val quotesApiStates: StateFlow<QuotesUiState> = _quotesApiStates


    // vehicle list
    var vehicleList: MutableList<VehicleListModelItem> = mutableListOf()
    var createInvoiceUiModel by mutableStateOf(CreateInvoiceUiModel())


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
                        dobMonth = policyHolderUiData.dobMonth.code.toString().padStart(2, '0'),
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

                    val response = Ktor.client.post(createPolicyHolderEndPoint) {
                        setBody(body)
                    }

                    val errorCode = response.status.value
                    when (errorCode) {
                        200 -> {
                            try {

                                val data = json.decodeFromString(
                                    CreatePolicyHolderResponse.serializer(),
                                    response.bodyAsText()
                                )

                                createPolicyHolderResponse = data

                                isFromInvoice = false
                                showVehiclesByPolicyholderIdAndOwnerId(
                                    createPolicyHolderResponse.data.id,
                                    createPolicyHolderResponse.data.nationalId
                                )

                                getVehiclesSequenceId(
                                    policyHolderUiData.nationalId,
                                    //createPolicyHolderResponse.data.nationalId,
                                    //createPolicyHolderResponse.data.sequenceNumber
                                    policyHolderUiData.sequenceNumber
                                )

                            } catch (e: Exception) {
                                showError("Unexpected Response")
                            }
                        }

                        401 -> {
                            logoutAndNavigateToLoginScreen()
                        }

                        else -> {
                            val error = json.decodeFromString(
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
    }

    private fun showVehiclesByPolicyholderIdAndOwnerId(policyHolderID: Int, nationalID: String) {

        screenModelScope.launch {
            try {
                val response =
                    Ktor.client.get(
                        getshowVehiclesByPolicyholderIdAndOwnerIdEndPoint(
                            policyHolderID,
                            nationalID
                        )
                    )

                try {
                    val responseBody = response.bodyAsText()
                    vehicleList = json.decodeFromString(
                        kotlinx.serialization.builtins.ListSerializer(
                            VehicleListModelItem.serializer()
                        ),
                        responseBody
                    ).toMutableList()

                    if (isFromInvoice) {
                        isFromInvoice = false
                        buildBillingDetailsData(vehicleList[0])


                        currentStep = 5
                        /* _quotesApiStates.value = QuotesUiState(
                             apiStatus = QuotesApiStates.Payment(createInvoiceResponse, vehicleList)
                         )*/
                    } else {
                        updateVehicleBodyData =
                            updateVehicleBodyData.copy(id = vehicleList[0].id)


                        currentStep = 2
                        /*_quotesApiStates.value = QuotesUiState(
                            apiStatus = QuotesApiStates.VehicleInfo(vehicleList)
                        )*/
                    }


                } catch (e: SerializationException) {
                    try {
                        val errorResponse: ErrorResponse =
                            json.decodeFromString(ErrorResponse.serializer(), response.bodyAsText())
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

    private fun buildBillingDetailsData(vehicleListModelItem: VehicleListModelItem) {
        val policyHolder = vehicleListModelItem.policyHolder
        val user = vehicleListModelItem.user

        billingDetailsUIData = billingDetailsUIData.copy(
            billingCity = if (policyHolder?.insuredAddressCity != null) policyHolder.insuredAddressCity else "",
            billingCountry = InsuranceTypeCodeModel(),
            billingIban = "0380000000608010167519",
            billingMobileNo = user?.userProfile?.mobileNumber ?: "",
            billingPostcode = policyHolder?.postCode ?: "",
            billingState = policyHolder?.district ?: "",
            billingStreet1 = if (policyHolder?.street != null) policyHolder.street else "",
            billingVat = "",
            customerEmail = if (user?.email != null) user.email else "",
            customerGivenName = if (policyHolder?.firstNameEn != null) policyHolder.firstNameEn else "",
            customerSurname = if (policyHolder?.middleNameEn != null) policyHolder.middleNameEn else ""
        )
    }

    private fun getVehiclesSequenceId(nationalID: String, sequenceID: String) {

        screenModelScope.launch {
            try {
                val response =
                    Ktor.client.get(getVehiclesSequenceIdEndPoint(sequenceID, nationalID))

                try {
                    val responseBody = response.bodyAsText()

                    val getVehiclesSequenceResponse = json.decodeFromString(
                        VehiclesSequenceIdResponse.serializer(),
                        responseBody
                    )
                    updateVehicleBodyData =
                        updateVehicleBodyData.copy(vehicleRegExpiryDate = getVehiclesSequenceResponse.vehicleInfo.registrationExpiryDate)

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
                updateVehicleBodyData = updateVehicleBodyData.copy(
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

                val response = Ktor.client.post(updateVehicleEndPoint) {
                    setBody(updateVehicleBodyData)
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
                        logoutAndNavigateToLoginScreen()
                    }

                    else -> {
                        val error = json.decodeFromString(
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


    fun createInvoice(
        companyCode: Int,
        deductibleValue: String?
    ) {

        val createInvoiceModel = CreateInvoiceBody(
            companyCode = companyCode.toString(),
            deductibleValue = deductibleValue,
            insuranceTypeId = vehicleUiData.insuranceType.code,
            referenceNo = createPolicyHolderResponse.data.referenceNo,
            selectedBenifitIds = createInvoiceUiModel.selectedBenifitIds,
            userid = if (LogInManager.getLoggedInUser() != null && LogInManager.getLoggedInUser()!!.user != null) {
                LogInManager.getLoggedInUser()!!.user!!.id
            } else 0
        )

        screenModelScope.launch {
            try {
                showLoading()
                val response =
                    Ktor.client.post(createInvoiceEndPoint) {
                        setBody(createInvoiceModel)
                    }

                if (response.status.isSuccess()) {
                    createInvoiceResponse = json.decodeFromString(
                        CreateInvoiceResponse.serializer(),
                        response.bodyAsText()
                    )

                    isFromInvoice = true
                    showVehiclesByPolicyholderIdAndOwnerId(
                        createPolicyHolderResponse.data.id,
                        createPolicyHolderResponse.data.nationalId
                    )
                } else {
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


    @Serializable
    data class CreateDriverResponse(
        val success: Boolean,
        val code: String?,
        val flag: Boolean?,
        val message: String?
    )

    fun createDriver() {

        showLoading()
        screenModelScope.launch {
            try {

                val body = addNewDriverBody.copy(
                    childrenBelow16 = createDriver.childrenBelow16.code,
                    dobMonth = createDriver.dobMonth.code.toString().padStart(2, '0'),
                    dobYear = createDriver.dobYear.description.en,
                    driverBusinessCity = createDriver.driverBusinessCity.code,
                    driverId = createDriver.driverId,
                    driverLicenses = getDriverLicenses(createDriver.driverLicenses),
                    driverNoaLastFiveYears = createDriver.driverNoaLastFiveYears.code,
                    driverNocLastFiveYears = createDriver.driverNocLastFiveYears.code,
                    driverRelationship = createDriver.driverRelationship.code,
                    driverTypeCode = createDriver.driverTypeCode.code,
                    education = createDriver.education.code,
                    healthConditions = createDriver.healthConditions.ifEmpty { null },
                    id = "",
                    isAdditionalDriver = createDriver.driverTypeCode.code == 2,
                    policyHolderId = createPolicyHolderResponse.data.id,
                    referenceNo = createPolicyHolderResponse.data.referenceNo,
                    type = "driver",
                    licenseCountryCode = "",
                    licenseNumberYears = "",
                    driverAddressCity = "",
                    vehicleId = vehicleList[0].id,
                    vehicleNightParking = createDriver.vehicleNightParking.code,
                    violationCodeIds = createDriver.violationCodeIds.ifEmpty { null }
                )

                val bodyToSend = json.encodeToString(
                    CreateDriverBody.serializer(), body
                )

                println("Serialized JSON: $bodyToSend")
                val response =
                    Ktor.client.post(createDriverEndPoint) {
                        setBody(body)
                    }

                val responseBody = response.bodyAsText()

                if (response.status.isSuccess()) {
                    try {
                        val createDriverResponse: CreateDriverResponse =
                            json.decodeFromString(CreateDriverResponse.serializer(), responseBody)

                        if (createDriverResponse.success) {
                            addNewDriverSheetVisible = false
                            showDriverByVehicleId(
                                vehicleList[0].id,
                                createPolicyHolderResponse.data.id.toString()
                            )
                            showSuccessMessage(createDriverResponse.message.toString())
                        } else {
                            showError(createDriverResponse.message.toString())
                        }

                    } catch (ex: SerializationException) {
                        showError("Unexpected response format")
                    }

                } else {
                    AppConstants.onError(response)
                }


                hideLoading()
            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "empty" else e.message!!
                showError(message)
            }
        }
    }

    private fun getDriverLicenses(driverLicenses: List<AddLicenseUiData>): List<DriverLicense> {
        return driverLicenses.map {
            DriverLicense(
                licenseCountryCode = it.licenseCountryCode.code,
                licenseNumberYears = it.licenseNumberYears
            )
        }
    }

    private fun showDriverByVehicleId(vehicleId: Int, policyHolderId: String) {

        screenModelScope.launch {
            try {
                val response =
                    Ktor.client.get(getShowDriverEndPoint(vehicleId, policyHolderId))

                val responseBody = response.bodyAsText()
                if (response.status.isSuccess()) {

                    try {
                        driverList =
                            json.decodeFromString(
                                kotlinx.serialization.builtins.ListSerializer(
                                    showDriverByVehicleIdResponseItem.serializer()
                                ),
                                responseBody
                            ).toMutableList()

                        currentStep = 3
                        updateDriverPercentage()

                    } catch (e: SerializationException) {
                        hideLoading()
                        try {
                            val errorResponse: ErrorResponse =
                                json.decodeFromString(
                                    ErrorResponse.serializer(),
                                    response.bodyAsText()
                                )
                            showError(errorResponse.errorMessage)
                        } catch (ex: SerializationException) {
                            showError("un Expected Error")
                        }
                    }

                } else {
                    AppConstants.onError(response)
                }
            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "empty" else e.message!!
                showError(message)
            }
        }
    }

    fun updateDriverPercentage() {

        screenModelScope.launch {
            try {
                val response =
                    Ktor.client.post(getShowDriverEndPoint(createPolicyHolderResponse.data.referenceNo)) {
                        setBody(
                            driverList.associate {
                                it.driverId.toString() to it.driverDrivingPercentage
                            }
                        )
                    }

                if (response.status.isSuccess()) {
                    val updatedDriverList = json.decodeFromString(
                        kotlinx.serialization.builtins.ListSerializer(
                            showDriverByVehicleIdResponseItem.serializer()
                        ),
                        response.bodyAsText()
                    ).toMutableList()

                    // getQuotesList(createPolicyHolderResponse.data.referenceNo, false)
                } else {
                    val error =
                        json.decodeFromString(ErrorResponse.serializer(), response.bodyAsText())
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
                    Ktor.client.get(getQuotesEndPoint(createPolicyHolderResponse.data.referenceNo))
                        .body<QuotesListResponse>()

                if (response.errorCode == null) {
                    quotes = response

                    if (!isRecursiveCall)
                        currentStep = 4

                    if (!response.completed) {
                        delay(1000)
                        getQuotesList(referenceNo, true)
                    }
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

    fun changeReferenceNoOnQuotation(referenceNo: String, policyHolderId: Int) {
        screenModelScope.launch {
            try {
                showLoading()

                val response =
                    Ktor.client.get(getChangeReferenceNumEndPoint(referenceNo, policyHolderId))

                if (response.status.isSuccess()) {

                    try {
                        val data = json.decodeFromString(
                            ChangeReferenceNoResponse.serializer(),
                            response.bodyAsText()
                        )
                        if (data.success) {
                            if (!data.code.equals("FIRST_ATTEMPT_UPDATED")) {
                                createPolicyHolderResponse.data.referenceNo =
                                    data.data?.policyHolder?.referenceNo!!
                            }

                            getQuotesList(createPolicyHolderResponse.data.referenceNo, false)
                        } else {
                            hideLoading()
                            showError(data.message)
                        }

                    } catch (e: Exception) {
                        hideLoading()
                        showError("failed to update some information")
                    }
                } else
                    AppConstants.onError(response)

            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "unknown error" else e.message!!
                showError(message)
            }
        }
    }

    fun sendPaymentOTP() {
        screenModelScope.launch {
            try {
                showLoading()

                val response =
                    Ktor.client.post(sendOTPEndPoint)
                try {
                    paymentOTPResponse = json.decodeFromString(
                        PaymentOTPResponse.serializer(),
                        response.bodyAsText()
                    )

                    if (paymentOTPResponse.errorCode == null) {
                        paymentOTPVisible = true
                    } else if (paymentOTPResponse.errorCode == 401) {
                        logoutAndNavigateToLoginScreen()
                    } else {
                        hideLoading()
                        showError(paymentOTPResponse.errorMessage.toString())
                    }

                    hideLoading()
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

    fun deleteDriver(driverId: Int) {
        screenModelScope.launch {
            try {
                showLoading()

                val response =
                    Ktor.client.post(deleteDriverEndPoint(driverId))

                if (response.status.isSuccess()) {
                    try {
                        showDriverByVehicleId(
                            vehicleList[0].id,
                            createPolicyHolderResponse.data.id.toString()
                        )

                        hideLoading()
                    } catch (e: Exception) {
                        hideLoading()
                        showError("failed to update some information")
                    }
                } else {
                    AppConstants.onError(response)
                }

            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "unknown error" else e.message!!
                showError(message)
            }
        }
    }

    fun verifyPaymentOTPAndCreateInvoice() {
        screenModelScope.launch {
            try {
                showLoading()

                val billingDetailsBody = BillingDetailsBody(
                    billingCity = billingDetailsUIData.billingCity,
                    billingCountry = "SA" /*billingDetailsUIData.billingCountry.description.en*/,
                    billingIban = "SA${billingDetailsUIData.billingIban}",
                    billingMobileNo = billingDetailsUIData.billingMobileNo,
                    billingPostcode = billingDetailsUIData.billingPostcode,
                    billingState = billingDetailsUIData.billingState,
                    billingStreet1 = billingDetailsUIData.billingStreet1,
                    billingVat = billingDetailsUIData.billingVat,
                    cardType = billingDetailsUIData.cardType,
                    customerEmail = billingDetailsUIData.customerEmail,
                    customerGivenName = billingDetailsUIData.customerGivenName,
                    customerSurname = billingDetailsUIData.customerSurname,
                    lang = currentLanguage,
                    otp = billingDetailsUIData.otp,
                    referenceNo = createPolicyHolderResponse.data.referenceNo
                )

                val response =
                    Ktor.client.post(otpVerifyAndCreateInvoiceEndPoint) {
                        setBody(billingDetailsBody)
                    }

                if (response.status.isSuccess()) {

                    try {
                        val data = json.decodeFromString(
                            VerifyPaymentOTPResponse.serializer(),
                            response.bodyAsText()
                        )

                        when (data.success) {
                            true -> {
                                currentStep = 1
                                paymentOTPVisible = false
                                navigator.replace(PaymentScreen())
                            }

                            else -> {
                                hideLoading()
                                showError(data.message.toString())
                            }
                        }

                    } catch (e: Exception) {
                        showError("failed to update some information")
                    }
                } else {
                    AppConstants.onError(response)
                }

                hideLoading()
            } catch (e: Exception) {
                hideLoading()
                val message = if (e.message == null) "unknown error" else e.message!!
                showError(message)
            }
        }
    }


    private fun validateForm(insuranceType: InsuranceType): Boolean {
        var isValid = true

        if (currentStep == 1) {
            isValid = validFormValues(insuranceType)
        } else if (currentStep == 2) {
            isValid = vaidateVehicleForm()
        } else if (currentStep == 3) {
            isValid = validateDriverForm()
        } else if (currentStep == 4) {
            isValid = true
        } else {
            isValid = validateBillingInfo()
        }

        return isValid
    }

    private fun validateDriverForm(): Boolean {
        return true
    }

    private fun validateBillingInfo(): Boolean {
        return true
    }

    private fun vaidateVehicleForm(): Boolean {
        return true
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

    fun searchUserByNationalId(driverId: String, dobMonth: String, dobYear: String) {


        val endPoint = if (driverId.startsWith("2"))
            foreignIqamaSearch
        else
            saudiHawiyahSearch

        screenModelScope.launch {
            //showLoading()
            try {
                val response =
                    Ktor.client.get("$endPoint${driverId}") {
                        url {
                            parameters.append(
                                "dob",
                                "${dobYear}-${dobMonth.padStart(2, '0')}"
                            )
                        }
                    }

                val statusCode = response.status.value

                when (statusCode) {
                    200 -> {
                        val data = json.decodeFromString(
                            SearchUserByNationalIdResponse.serializer(),
                            response.bodyAsText()
                        )

                        addNewDriverBody = addNewDriverBody.copy(
                            driverAddressCity = "",
                            driverFirstNameAr = data.personBasicInfo.firstName.toString(),
                            driverFirstNameEn = data.personBasicInfo.firstNameT.toString(),
                            driverLastNameAr = data.personBasicInfo.familyName.toString(),
                            driverLastNameEn = data.personBasicInfo.familyNameT.toString(),
                            driverMiddleNameAr = data.personBasicInfo.fatherName.toString()
                                .ifEmpty { null },
                            driverMiddleNameEn = data.personBasicInfo.fatherNameT.toString()
                                .ifEmpty { null },
                            fullArabicName = data.personBasicInfo.fullNameAr.toString(),
                            fullEnglishName = data.personBasicInfo.fullNameEn.toString(),
                            isAdditionalDriver = true,
                            licenseCountryCode = "",
                            licenseNumberYears = ""
                        )

                    }

                    401 -> {
                        logoutAndNavigateToLoginScreen()
                    }

                    else -> {
                        val error = json.decodeFromString(
                            ErrorResponse.serializer(),
                            response.bodyAsText()
                        )
                        showError(error.errorMessage)
                    }
                }
                // hideLoading()
            } catch (e: Exception) {
                println()
            }
        }
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