package presentation.screen.quotes_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import dropDownValues
import models.enums.InsuranceType
import models.showDriverByVehicleIdResponseItem
import navigator
import presentation.bottom_sheets.BottomSheet
import presentation.bottom_sheets.DateBottomSheet
import presentation.bottom_sheets.DriverListSheet
import presentation.bottom_sheets.InsuranceDetailSheet
import presentation.bottom_sheets.VehicleSpecificationsBottomSheet
import presentation.screen.login.LoginScreen
import utils.AppColors
import utils.AppConstants.Companion.getButtonColors
import utils.AppConstants.Companion.getCheckBoxColors
import utils.AppConstants.Companion.getOutlineTextFieldColors
import utils.LogInManager
import utils.language.language_manager.LanguageManager.currentLanguage

private const val TOTAL_STEPS = 5
public var selectedInsuranceType: InsuranceType = InsuranceType.INSURE_YOUR_VEHICLE
private lateinit var quoteViewModel: QuotesViewModel

enum class BottomSheetCaller {
    MONTH, YEAR, PURPOSE, MANUFACTURING_YEAR, COUNTRY
}

val spaceBwFields = 10.dp

data class QuotesUiState(val apiStatus: QuotesApiStates = QuotesApiStates.None)

lateinit var uiQuotesState: QuotesUiState

class GetQuotes(private val insuranceType: InsuranceType = InsuranceType.INSURE_YOUR_VEHICLE) :
    Screen {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        selectedInsuranceType = insuranceType
        quoteViewModel = getScreenModel<QuotesViewModel>()
        uiQuotesState = quoteViewModel.quotesApiStates.collectAsState().value
        quoteViewModel.updateVehicleBodyData =
            quoteViewModel.updateVehicleBodyData.copy(identificationType = if (insuranceType == InsuranceType.CUSTOM_CARD) 2 else 1)
        //quoteViewModel.currentStep = 5

        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Get Quotes") }, navigationIcon = {
                IconButton(onClick = {
                    if (quoteViewModel.currentStep == 1) {
                        uiQuotesState = QuotesUiState(apiStatus = QuotesApiStates.None)
                        navigator.pop()
                    } else {
                        quoteViewModel.currentStep--
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Menu"
                    )
                }
            })
        }) { padding ->


            Column(
                modifier = Modifier.fillMaxSize().padding(vertical = 10.dp, horizontal = 20.dp)
            ) {


                Column(
                    modifier = Modifier.fillMaxSize().weight(1f) // Takes the remaining space
                        .padding(padding).verticalScroll(rememberScrollState())
                ) {

                    StepIndicator(quoteViewModel.currentStep)

                    Spacer(modifier = Modifier.height(30.dp))

                    GetQuoteForm(quoteViewModel.currentStep)
                }

                if (quoteViewModel.currentStep <= 3) {

                    Column(Modifier.fillMaxWidth()) {

                        Spacer(modifier = Modifier.height(16.dp))

                        if (quoteViewModel.currentStep <= 2) {
                            // Authorization Text and Button
                            Text(
                                text = "By clicking Next, I authorize Offer to access my personal and vehicle data to generate insurance quotes.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray
                            )

                        } else {
                            val message =
                                "I grant Offer Insurance Brokerage Ltd permission to use my information to obtain quotes from relevant agencies as necessary. See our, Privacy Policy"


                            Row(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxWidth().clickable {
                                    quoteViewModel.privacyAccepted = !quoteViewModel.privacyAccepted
                                }) {
                                Checkbox(
                                    checked = quoteViewModel.privacyAccepted, onCheckedChange = {
                                        quoteViewModel.privacyAccepted = it
                                    }, colors = getCheckBoxColors()
                                )

                                Text(
                                    text = message,
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                        .fillMaxWidth().padding(vertical = 20.dp)
                                )
                            }

                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            shape = androidx.compose.foundation.shape.CircleShape,
                            colors = getButtonColors(),
                            onClick = {

                                if (LogInManager.loggedIn) {
                                    //quoteViewModel.currentStep++
                                    when (quoteViewModel.currentStep) {
                                        1 -> {
                                            quoteViewModel.createPolicyHolder(insuranceType)
                                        }

                                        2 -> {
                                            quoteViewModel.updateVehicle()
                                        }

                                        3 -> {
                                            quoteViewModel.changeReferenceNoOnQuotation(
                                                quoteViewModel.createPolicyHolderResponse.data.referenceNo,
                                                quoteViewModel.createPolicyHolderResponse.data.id
                                            )
                                        }

                                        4 -> {

                                        }

                                        5 -> {

                                        }
                                    }
                                } else {
                                    navigator.push(LoginScreen())
                                }
                            },
                            enabled = true, // Initially disabled
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        ) {
                            Text("Next")
                        }
                    }

                }
            }
        }

        if (quoteViewModel.isSheetVisible) {
            BottomSheet(title = "Select a option", onDismiss = {
                quoteViewModel.isSheetVisible = false
            }, onSelected = {
                when (quoteViewModel.selectedSheet) {
                    BottomSheetCaller.COUNTRY -> {
                        quoteViewModel.billingDetailsUIData =
                            quoteViewModel.billingDetailsUIData.copy(
                                billingCountry = it
                            )
                    }

                    BottomSheetCaller.MONTH -> {
                        quoteViewModel.policyHolderUiData = quoteViewModel.policyHolderUiData.copy(
                            dobMonth = it
                        )
                    }

                    BottomSheetCaller.YEAR -> {
                        quoteViewModel.policyHolderUiData = quoteViewModel.policyHolderUiData.copy(
                            dobYear = it
                        )
                    }

                    BottomSheetCaller.PURPOSE -> {
                        quoteViewModel.vehicleUiData =
                            quoteViewModel.vehicleUiData.copy(vehicleUse = it)
                    }

                    BottomSheetCaller.MANUFACTURING_YEAR -> {
                        quoteViewModel.policyHolderUiData = quoteViewModel.policyHolderUiData.copy(
                            manufactureYear = it
                        )
                    }
                }
                quoteViewModel.isSheetVisible = false
            }, data = when (quoteViewModel.selectedSheet) {
                BottomSheetCaller.MONTH -> if (quoteViewModel.policyHolderUiData.nationalId.startsWith(
                        "1"
                    )
                ) dropDownValues.monthsArabic else dropDownValues.monthsEnglish

                BottomSheetCaller.YEAR -> if (quoteViewModel.policyHolderUiData.nationalId.startsWith(
                        "1"
                    )
                ) dropDownValues.arabicYears else dropDownValues.englishYears

                BottomSheetCaller.PURPOSE -> dropDownValues.vehiclePurposeList
                BottomSheetCaller.MANUFACTURING_YEAR -> dropDownValues.manufactureYear
                BottomSheetCaller.COUNTRY -> dropDownValues.drivingLicenceCountryList
            }
            )
        }

        if (quoteViewModel.isVehicleSpecificationSheetVisible) {
            VehicleSpecificationsBottomSheet(quoteViewModel, onDismiss = {
                quoteViewModel.isVehicleSpecificationSheetVisible = false
            }, onSelected = {

            })
        }

        if (quoteViewModel.driverListSheetVisible) {
            DriverListSheet(quoteViewModel, onDismiss = {
                quoteViewModel.driverListSheetVisible = false
            }, onSelected = {})
        }

        if (quoteViewModel.datePickerSheetVisible) {
            DateBottomSheet("Select Effective Date", onDismissSheet = {
                quoteViewModel.datePickerSheetVisible = false
            }, onDateSelected = {
                quoteViewModel.policyHolderUiData = quoteViewModel.policyHolderUiData.copy(
                    insuranceEffectiveDate = it
                )
                quoteViewModel.datePickerSheetVisible = false
            })
        }
        if (quoteViewModel.policyDetailsSheetVisible) {
            InsuranceDetailSheet(
                quotesViewModel = quoteViewModel,
                onDismiss = {
                    quoteViewModel.policyDetailsSheetVisible = false
                }, onBuyPolicy = {
                    quoteViewModel.policyDetailsSheetVisible = false
                }, quote = quoteViewModel.selectedQuote
            )
        }

        if (quoteViewModel.buyButtonClicked) {
            quoteViewModel.currentStep = 5
        }
    }
}


@Composable
fun StepIndicator(progress: Int) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())
    ) {
        repeat(TOTAL_STEPS) { index ->
            Box(
                modifier = Modifier.height(8.dp).padding(2.dp).weight(1f).background(
                    color = if (index < progress) AppColors.AppColor else Color.LightGray,
                    shape = RoundedCornerShape(4.dp)
                )
            )
        }
    }
}

@Composable
fun GetQuoteForm(progress: Int) {

    when (progress) {
        1 -> {
            policyHolderCompose()
        }

        2 -> {
            VehicleDetailsForm(quoteViewModel)
        }

        3 -> {
            DriversScreen(quoteViewModel)
        }

        4 -> {
            QuoteScreen(quoteViewModel)
        }

        else -> {
            ReviewInsurancePolicy(quoteViewModel)
        }
    }
}

@Composable
fun policyHolderCompose() {
    when (selectedInsuranceType) {
        InsuranceType.INSURE_YOUR_VEHICLE -> {
            IqamaFormScreen(quoteViewModel)
        }

        InsuranceType.OWNER_TRANSFER -> {
            OwnerTransferFormScreen(quoteViewModel)
        }

        InsuranceType.CUSTOM_CARD -> {
            CustomerTransferFormScreen(quoteViewModel)
        }
    }
}


@Composable
fun DriverCard(driver: showDriverByVehicleIdResponseItem) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp)).padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(Color.White) // Set background color here
                .padding(12.dp)
        ) {
            Text(
                text = if (currentLanguage.equals("en")) driver.fullEnglishName else driver.fullArabicName,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Driver ID: ${driver.driverId}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}


@Composable
fun DropdownField(
    label: String,
    onclick: () -> Unit,
    modifier: Modifier = Modifier,
    errorValue: String?,
    selectedOption: String
) {

    Column(modifier) {

        OutlinedTextField(
            value = selectedOption,
            onValueChange = {},
            isError = errorValue != null,
            label = { Text(text = label) },
            readOnly = true, // Prevent manual editing
            modifier = Modifier.fillMaxWidth().clickable {
                quoteViewModel.isSheetVisible = true
                onclick()
            },
            trailingIcon = {
                Icon(imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        quoteViewModel.isSheetVisible = true
                        onclick()
                    })
            },
            colors = getOutlineTextFieldColors()
        )

        addErrorText(errorValue)
    }
}


@Composable
fun addErrorText(errorValue: String?) {
    errorValue?.let {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = it,
            textAlign = TextAlign.Start,
            color = AppColors.Red,
            fontSize = 12.sp
        )
    }
}