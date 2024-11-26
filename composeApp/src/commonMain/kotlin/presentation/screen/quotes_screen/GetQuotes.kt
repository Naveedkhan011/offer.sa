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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import models.enums.InsuranceType
import navigator
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.ic_baseline_alternate_email_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.AppColors
import utils.AppConstants.Companion.getButtonColors
import utils.AppConstants.Companion.getOutlineTextFieldColors

private const val TOTAL_STEPS = 5
private var selectedInsuranceType: InsuranceType = InsuranceType.INSURE_YOUR_VEHICLE
private lateinit var quoteViewModel: QuotesViewModel

enum class BottomSheetCaller {
    MONTH, YEAR, PURPOSE, OTHER
}

val spaceBwFields = 10.dp

class GetQuotes(private val insuranceType: InsuranceType = InsuranceType.INSURE_YOUR_VEHICLE) :
    Screen {


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        selectedInsuranceType = insuranceType;
        quoteViewModel = getScreenModel<QuotesViewModel>()
        var currentStep by remember { mutableStateOf(1) }

        Scaffold(topBar = {
            CenterAlignedTopAppBar(title = { Text(text = "Get Quotes") }, navigationIcon = {
                IconButton(onClick = {
                    navigator.pop()
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
                    modifier = Modifier.fillMaxSize().fillMaxSize().padding(padding)
                        .verticalScroll(rememberScrollState())
                ) {

                    // Step Indicator
                    StepIndicator(currentStep)

                    Spacer(modifier = Modifier.height(30.dp))

                    // Input Fields
                    GetQuoteForm(currentStep)


                }

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
                            if (currentStep == TOTAL_STEPS) {
                                navigator.pop()
                            } else {
                                currentStep++
                            }
                            quoteViewModel.verifyForm()
                        }, enabled = true, // Initially disabled
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    ) {
                        Text("Next")
                    }
                }
            }
        }



        if (quoteViewModel.isSheetVisible) {
            BottomSheet(
                onDismiss = {
                    quoteViewModel.isSheetVisible = false
                },
                onYearSelected = {
                    when (quoteViewModel.selectedSheet) {
                        BottomSheetCaller.MONTH -> quoteViewModel.selectedMonth = it
                        BottomSheetCaller.YEAR -> quoteViewModel.selectedYear = it
                        BottomSheetCaller.PURPOSE -> quoteViewModel.purposeOfUse = it
                        BottomSheetCaller.OTHER -> {}
                    }
                    quoteViewModel.isSheetVisible = false
                },
                data = when (quoteViewModel.selectedSheet) {
                    BottomSheetCaller.MONTH -> quoteViewModel.months
                    BottomSheetCaller.YEAR -> quoteViewModel.years
                    BottomSheetCaller.PURPOSE -> quoteViewModel.purposeList
                    BottomSheetCaller.OTHER -> quoteViewModel.purposeList
                }
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
            //nationalIDForm(selectedInsuranceType)
        }

        2 -> {
            when (selectedInsuranceType) {
                InsuranceType.INSURE_YOUR_VEHICLE,
                InsuranceType.OWNER_TRANSFER -> {
                    sequenceVerificationForm()
                }

                InsuranceType.CUSTOM_CARD -> {
                    CustomCardsForm()
                }
            }
        }

        3 -> {
            VehicleDetailsForm()
        }

        4 -> {
            DriversScreen()
        }

        else -> {

        }
    }
}

@Composable
fun DriversScreen() {
    val drivers = remember { quoteViewModel.drivers }
    val otherDetailsVisible = remember { quoteViewModel.otherDetailsVisible }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Drivers List Header with "Edit List"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Drivers List",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Edit List",
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Blue),
                modifier = Modifier.clickable { quoteViewModel.editDrivers() }
            )
        }

        // Subtitle for the Drivers List
        Text(
            text = "you can add, edit or remove",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )

        // Drivers List Content
        drivers.forEach { driver ->
            DriverCard(driver = driver)
        }

        // Other Details Section
        Text(
            text = "Other Details",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Card(modifier = Modifier.background(Color.White)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
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
fun DriverCard(driver: Driver) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(
                color = Color(0xFFF7F7F7),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = driver.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Driver ID: ${driver.id}",
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
fun VehicleDetailsForm() {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
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
                        text = "هونداي اكستنت",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "2020",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                    Text(
                        text = "Sequence Number: \n983236710",
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
                            text = "1",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        VerticalDivider(thickness = 2.dp)

                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = "2",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                    }

                    HorizontalDivider(thickness = 1.dp)

                    Row {
                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = "3",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )

                        VerticalDivider(thickness = 2.dp)

                        Text(
                            modifier = Modifier
                                .weight(1f),
                            text = "4",
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
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(spaceBwFields))
        OutlinedTextField(
            value = quoteViewModel.purposeOfUse,
            onValueChange = { quoteViewModel.purposeOfUse = it },
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
            value = quoteViewModel.vehicleEstimatedValue,
            onValueChange = { quoteViewModel.vehicleEstimatedValue = it },
            label = { Text("Vehicle Estimated Value") },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_baseline_alternate_email_24),
                    contentDescription = null,
                    tint = AppColors.AppColor
                )
            },
            trailingIcon = {
                Text(text = "SAR")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )

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
            selectedOption = quoteViewModel.selectedMonth
        )

        Spacer(modifier = Modifier.width(spaceBwFields))

        DropdownField(
            label = "DOB Year",
            onclick = {
                quoteViewModel.selectedSheet = BottomSheetCaller.YEAR
            },
            modifier = Modifier.weight(1f),
            errorValue = quoteViewModel.dobYearError,
            selectedOption = quoteViewModel.selectedYear
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
                    onclick.invoke()
                },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        quoteViewModel.isSheetVisible = true
                        onclick.invoke()
                    }
                )
            }
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




