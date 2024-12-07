package presentation.screen.quotes_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import models.enums.InsuranceType
import models.showDriverByVehicleIdResponseItem
import models.showVehiclesByPolicyholderIdAndOwnerIdResponseItem
import navigator
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.ic_baseline_alternate_email_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.bottom_sheets.BottomSheet
import presentation.bottom_sheets.DriverListSheet
import presentation.bottom_sheets.VehicleSpecificationsBottomSheet
import presentation.screen.login.LoginScreen
import showError
import showLoading
import utils.AppColors
import utils.AppConstants.Companion.getButtonColors
import utils.AppConstants.Companion.getOutlineTextFieldColors
import utils.LogInManager

private const val TOTAL_STEPS = 5
private var selectedInsuranceType: InsuranceType = InsuranceType.INSURE_YOUR_VEHICLE
private lateinit var quoteViewModel: QuotesViewModel

data class Quote(
    val title: String,
    val price: String,
    val details: List<String>,
    val discount: String
)

enum class BottomSheetCaller {
    MONTH, YEAR, PURPOSE
}

val spaceBwFields = 10.dp

data class QuotesUiState(val apiStatus: QuotesApiStates = QuotesApiStates.None)

lateinit var uiQuotesState: QuotesUiState

class GetQuotes(private val insuranceType: InsuranceType = InsuranceType.INSURE_YOUR_VEHICLE) :
    Screen {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        selectedInsuranceType = insuranceType;
        quoteViewModel = getScreenModel<QuotesViewModel>()
        uiQuotesState = quoteViewModel.quotesApiStates.collectAsState().value

        var currentStep by remember { mutableStateOf(1) }

        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Get Quotes") }, navigationIcon = {
                IconButton(onClick = {
                    if (currentStep == 1) {
                        uiQuotesState = QuotesUiState(apiStatus = QuotesApiStates.None)
                        navigator.pop()
                    } else {
                        currentStep--
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                        contentDescription = "Menu"
                    )
                }
            })
        }) { padding ->


            Box(
                modifier = Modifier.fillMaxSize().padding(
                    vertical = 10.dp, horizontal = 20.dp
                )
            ) {


                Column(
                    modifier = Modifier.fillMaxSize().padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {
                    StepIndicator(currentStep)

                    Spacer(modifier = Modifier.height(30.dp))

                    GetQuoteForm(currentStep)
                }

                if (currentStep <= 3) {

                    Column(Modifier.fillMaxWidth().align(Alignment.BottomCenter)) {

                        Spacer(modifier = Modifier.height(16.dp))

                        // Authorization Text and Button
                        Text(
                            text = "By clicking Next, I authorize Offer to access my personal and vehicle data to generate insurance quotes.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Button(
                            shape = androidx.compose.foundation.shape.CircleShape,
                            colors = getButtonColors(),
                            onClick = {
                                if (LogInManager.loggedIn) {
                                    //currentStep++
                                    when (currentStep) {
                                        1 -> {
                                            quoteViewModel.createPolicyHolder(insuranceType)
                                        }

                                        2 -> {
                                            quoteViewModel.updateVehicle(quoteViewModel.vehicleList[0].id!!)
                                        }

                                        3 -> {
                                            quoteViewModel.createPolicyHolderResponse.data.referenceNo?.let {
                                                quoteViewModel.getQuotesList("RM5WLWDMAU00")
                                            }
                                        }

                                        4 -> {
                                            /*quoteViewModel.createInvoice(
                                                "1007",
                                                null,
                                                1,
                                                "RM5WLWDMAU00",
                                                listOf("25","26"),
                                                15)*/
                                        }

                                        5 -> {

                                        }
                                    }
                                } else {
                                    navigator.push(LoginScreen())
                                }
                            }, enabled = true, // Initially disabled
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        ) {
                            Text("Next")
                        }
                    }

                }
            }
        }

        when (val state = uiQuotesState.apiStatus) {

            is QuotesApiStates.None -> {}

            is QuotesApiStates.Loading -> {
                showLoading()
            }

            is QuotesApiStates.CreatePolicyHolder -> {
                currentStep = 1
            }

            is QuotesApiStates.VehicleInfo -> {
                currentStep = 2
            }

            is QuotesApiStates.DriverInfo -> {
                currentStep = 3
            }


            is QuotesApiStates.QuotesList -> {
                currentStep = 4
            }

            is QuotesApiStates.Payment -> {
                currentStep = 5
            }

            is QuotesApiStates.Error -> {
                val message = state.message
                LaunchedEffect(message) {
                    showError(message)
                }
            }
        }

        if (quoteViewModel.isSheetVisible) {
            BottomSheet(
                title = "User Data",
                onDismiss = {
                    quoteViewModel.isSheetVisible = false
                },
                onSelected = {
                    when (quoteViewModel.selectedSheet) {
                        BottomSheetCaller.MONTH -> quoteViewModel.selectedMonth = it
                        BottomSheetCaller.YEAR -> quoteViewModel.selectedYear = it
                        BottomSheetCaller.PURPOSE -> {
                            quoteViewModel.purposeOfVehicle = getTitle(it)
                            quoteViewModel.vehicleData.vehicleUseCode = it.code
                        }
                    }
                    quoteViewModel.isSheetVisible = false
                },
                data = when (quoteViewModel.selectedSheet) {
                    BottomSheetCaller.MONTH -> quoteViewModel.months
                    BottomSheetCaller.YEAR -> quoteViewModel.years
                    BottomSheetCaller.PURPOSE -> quoteViewModel.purposeList
                }
            )
        }

        if (quoteViewModel.isVehicleSpecificationSheetVisible) {
            VehicleSpecificationsBottomSheet(
                quoteViewModel,
                onDismiss = {
                    quoteViewModel.isVehicleSpecificationSheetVisible = false
                },
                onSelected = {

                })
        }

        if (quoteViewModel.driverListSheetVisible) {
            DriverListSheet(
                quoteViewModel,
                onDismiss = {
                    quoteViewModel.driverListSheetVisible = false
                },
                onSelected = {}
            )
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
            //CheckboxListScreen()
            policyHolderCompose()
        }

        2 -> {
            VehicleDetailsForm(quoteViewModel.vehicleList)
        }

        3 -> {
            DriversScreen()
        }

        4 -> {
            QuoteScreen(quoteViewModel)
        }

        else -> {
            PaymentCompose()
        }
    }


    /*when (selectedInsuranceType) {
               InsuranceType.INSURE_YOUR_VEHICLE,
               InsuranceType.OWNER_TRANSFER -> {
                   sequenceVerificationForm()
               }

               InsuranceType.CUSTOM_CARD -> {
                   CustomCardsForm()
               }
           }*/
}

@Composable
fun PaymentCompose() {

    Text(text = "Payment", modifier = Modifier.fillMaxSize())

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
fun DriversScreen() {
    val drivers = remember { quoteViewModel.driverList }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Drivers List Header with "Edit List"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Drivers List",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Edit List",
                style = MaterialTheme.typography.bodyMedium.copy(color = AppColors.AppColor),
                modifier = Modifier.clickable { quoteViewModel.driverListSheetVisible = true },
                fontWeight = FontWeight.Bold
            )
        }

        // Subtitle for the Drivers List
        Text(
            text = "you can add, edit or remove",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Drivers List Content
        drivers.forEach { driver ->
            DriverCard(driver = driver)
        }
    }
}


@Composable
fun DriverCard(driver: showDriverByVehicleIdResponseItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White) // Set background color here
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
fun OtherDetailsCard(isVisible: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFFF7F7F7),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "+",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Blue,
                modifier = Modifier.padding(end = 8.dp)
            )
            Column {
                Text(
                    text = "Other Details",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                if (isVisible) {
                    Text(
                        text = "Some insurance companies are asking for more details like Vehicle Night Parking, Expected KM per year",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun VehicleDetailsForm(vehicleList: MutableList<showVehiclesByPolicyholderIdAndOwnerIdResponseItem>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val firstItem = vehicleList[0]
        val sequenceNumber = firstItem.sequenceNumber
        val vehiclePlateNumber = firstItem.vehiclePlateNumber
        val vehiclePlateChar =
            firstItem.vehiclePlateText1 + " " + firstItem.vehiclePlateText2 + " " + firstItem.vehiclePlateText3

        // Car Header Section
        Card(
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth().background(Color(0xFFF8F8F8))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Car Logo
                Image(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Car Logo",
                    modifier = Modifier
                        .size(48.dp)
                        .weight(0.5f)
                        .padding(end = 16.dp)
                )

                // Car Info
                Column(modifier = Modifier.weight(2f)) {
                    Text(
                        text = firstItem.vehicleMaker + firstItem.vehicleModel,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = firstItem.vehicleModelYear.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Text(
                        text = "Sequence Number: \n$sequenceNumber",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(8.dp)
                        )
                ) {
                    Row {
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = "$vehiclePlateNumber",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        VerticalDivider(thickness = 2.dp)

                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = vehiclePlateChar,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                    }

                    HorizontalDivider(thickness = 1.dp)

                    Row {
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = "${firstItem.vehicleMajorColor}",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        VerticalDivider(thickness = 2.dp)

                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = "${firstItem.vehiclePlateTypeCode}",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }



        Spacer(modifier = Modifier.height(16.dp))

        // Car Details Section
        Text(
            text = "Car details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(spaceBwFields))
        OutlinedTextField(
            value = quoteViewModel.purposeOfVehicle,
            onValueChange = { },
            label = { Text("Purpose of use") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Info",
                    modifier = Modifier.clickable {
                        quoteViewModel.selectedSheet = BottomSheetCaller.PURPOSE
                        quoteViewModel.isSheetVisible = true
                    })
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_baseline_alternate_email_24),
                    contentDescription = null,
                    tint = AppColors.AppColor
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )

        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = quoteViewModel.valueOfVehicle,
            onValueChange = {
                quoteViewModel.valueOfVehicle = it
                quoteViewModel.vehicleData.vehicleValue = it
            },
            label = { Text("Vehicle Estimated Value") },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_baseline_alternate_email_24),
                    contentDescription = null,
                    tint = AppColors.AppColor
                )
            },
            trailingIcon = {
                Text(text = "SAR", modifier = Modifier.padding(5.dp))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )

        Spacer(modifier = Modifier.height(16.dp))
        // Other Details Section
        Text(
            text = "Other Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(vertical = 8.dp),
            onClick = {
                quoteViewModel.isVehicleSpecificationSheetVisible = true
            },
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White) // Set background color here
                    .padding(12.dp)
            ) {
                Text(
                    text = "+",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Blue,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Column {
                    Text(
                        text = "Other Details",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Some insurance companies are asking for more details like Vehicle Night Parking, Expected KM per year",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun DetailItem(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = Color(0xFFE0E0E0),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CustomCardsForm() {

    Spacer(modifier = Modifier.height(spaceBwFields))
    OutlinedTextField(
        value = quoteViewModel.customCard,
        onValueChange = { quoteViewModel.customCard = it },
        label = { Text("Custom Card") },
        leadingIcon = {
            Icon(
                painter = painterResource(Res.drawable.ic_baseline_alternate_email_24),
                contentDescription = null,
                tint = AppColors.AppColor
            )
        },
        isError = quoteViewModel.customCardError != null,
        modifier = Modifier.fillMaxWidth(),
        colors = getOutlineTextFieldColors()
    )
    addErrorText(quoteViewModel.customCardError)

    // National ID/Iqama ID Field
    Spacer(modifier = Modifier.height(spaceBwFields))
    OutlinedTextField(
        value = quoteViewModel.manufacturingYear,
        onValueChange = { quoteViewModel.manufacturingYear = it },
        label = { Text(text = "Manufacturing Year") },
        trailingIcon = { Icon(Icons.Default.Info, contentDescription = "Info") },
        modifier = Modifier.fillMaxWidth(),
        isError = quoteViewModel.manufacturingYearError != null,
        colors = getOutlineTextFieldColors()
    )
    addErrorText(quoteViewModel.manufacturingYearError)

}

@Composable
fun sequenceVerificationForm() {

    Text(
        fontWeight = FontWeight.Bold,
        text = "Enter sequence number",
        style = MaterialTheme.typography.bodyLarge,
        color = AppColors.textColor
    )
    Spacer(modifier = Modifier.height(spaceBwFields))


    OutlinedTextField(
        value = quoteViewModel.sequenceNumber,
        onValueChange = {
            quoteViewModel.sequenceNumber = it
        },
        label = { Text("Sequence Number") },
        modifier = Modifier.fillMaxWidth(),
        colors = getOutlineTextFieldColors()
    )

    addErrorText(quoteViewModel.sequenceNumberError)

    Spacer(modifier = Modifier.height(spaceBwFields))

    TextButton(
        modifier = Modifier
            .background(Color.White, RoundedCornerShape(10.dp)),
        onClick = {

        }
    ) {
        // "Know more about types" Text
        Text(
            text = "Where can i find my car's sequence number?",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = AppColors.AppColor,
                fontWeight = FontWeight.Bold
            )
        )

        Icon(
            tint = AppColors.AppColor,
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = ""
        )
    }
}

@Composable
fun nationalIDForm(insuranceType: InsuranceType) {

    val title = when (insuranceType) {
        InsuranceType.INSURE_YOUR_VEHICLE -> {
            "National ID/Iqama ID/Company Unified ID"
        }

        InsuranceType.OWNER_TRANSFER -> {
            "National ID/Iqama ID/Company Unified ID(Buyer)"
        }

        InsuranceType.CUSTOM_CARD -> {
            "National ID/Iqama ID/Company Unified ID(NEW Owner)"
        }
    }

    val label = "National ID/Iqama ID"

    Text(
        fontWeight = FontWeight.Bold,
        text = title,
        style = MaterialTheme.typography.bodyLarge,
        color = AppColors.textColor
    )

    // National ID/Iqama ID Field
    OutlinedTextField(
        value = quoteViewModel.nationalID,
        onValueChange = {
            quoteViewModel.nationalID = it
            quoteViewModel.verifyIqamaLocally()
        },
        label = {
            Text(
                text = label
                //style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
            )
        },
        trailingIcon = { Icon(Icons.Default.Info, contentDescription = "Info") },
        modifier = Modifier.fillMaxWidth(),
        isError = quoteViewModel.nationalIDError != null,
        colors = getOutlineTextFieldColors()
    )

    addErrorText(quoteViewModel.nationalIDError)

    Spacer(modifier = Modifier.height(spaceBwFields))

    // DOB Month and Year Dropdowns
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DropdownField(
            label = "DOB Month",
            onclick = {
                quoteViewModel.selectedSheet = BottomSheetCaller.MONTH
            },
            modifier = Modifier.weight(1f),
            errorValue = quoteViewModel.dobError,
            selectedOption = getTitle(quoteViewModel.selectedMonth)
        )

        Spacer(modifier = Modifier.width(spaceBwFields))

        DropdownField(
            label = "DOB Year",
            onclick = {
                quoteViewModel.selectedSheet = BottomSheetCaller.YEAR
            },
            modifier = Modifier.weight(1f),
            errorValue = quoteViewModel.dobYearError,
            selectedOption = getTitle(quoteViewModel.selectedYear)
        )
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
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    quoteViewModel.isSheetVisible = true
                    onclick()
                },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        quoteViewModel.isSheetVisible = true
                        onclick()
                    }
                )
            },
            colors = getOutlineTextFieldColors()
        )
    }

    addErrorText(errorValue)

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