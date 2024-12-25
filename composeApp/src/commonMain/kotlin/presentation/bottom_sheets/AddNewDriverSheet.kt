package presentation.bottom_sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dropDownValues
import models.ui_models.AddLicenseUiData
import models.InsuranceTypeCodeModel
import models.ui_models.CreateDriverUiModel
import presentation.screen.quotes_screen.QuotesViewModel
import presentation.screen.quotes_screen.getTitle
import presentation.screen.quotes_screen.spaceBwFields
import utils.AppConstants
import utils.AppConstants.Companion.getCheckBoxColors
import utils.AppConstants.Companion.getOutlineTextFieldColors
import utils.language.language_manager.LanguageManager.currentLanguage


var addDriverSelectedSheet = AddNewDriverBottomSheetCaller.DOB_MONTH

enum class AddNewDriverBottomSheetCaller {
    DOB_MONTH,
    DOB_YEAR,
    VEHICLE_NIGHT_PARKING,
    DRIVER_RELATION,
    DRIVER_EDUCATION,
    DRIVER_CHILDREN_BELOW_16,
    DRIVER_BUSINESS_CITY,
    DRIVER_NOA_LAST_FIVE_YEARS,
    DRIVER_NOC_LAST_FIVE_YEARS,
    DRIVING_LICENCE_COUNTRY
}

private lateinit var quoteViewModel: QuotesViewModel
private lateinit var driver: CreateDriverUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewDriverSheet(
    quoteViewModelParameter: QuotesViewModel,
    onDismiss: () -> Unit,
    onSelected: (selectedMonth: String) -> Unit
) {

    quoteViewModel = quoteViewModelParameter
    driver = quoteViewModel.createDriver

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 45.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Top title
            Text(
                text = "Add New Driver",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(spaceBwFields))

            // Driver ID
            OutlinedTextField(
                value = driver.driverId,
                onValueChange = {
                    quoteViewModel.createDriver = driver.copy(driverId = it)
                },
                label = { Text("Driver ID") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(spaceBwFields))

            // DOB Month & Year
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedTextField(
                    value = getTitle(driver.dobMonth),
                    onValueChange = {},
                    label = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "DOB Month",
                            textAlign = TextAlign.Start,
                            fontSize = 12.sp
                        )
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            modifier = Modifier.clickable {
                                addDriverSelectedSheet =
                                    AddNewDriverBottomSheetCaller.DOB_MONTH
                                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = true
                            }
                        )
                    },
                    modifier = Modifier.weight(1.2f),
                    readOnly = true,
                    colors = getOutlineTextFieldColors()
                )

                Spacer(modifier = Modifier.width(spaceBwFields))

                OutlinedTextField(
                    value = getTitle(driver.dobYear),
                    onValueChange = {},
                    label = {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "DOB Year",
                            textAlign = TextAlign.Start,
                            fontSize = 12.sp
                        )
                    },
                    trailingIcon = {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            modifier = Modifier.clickable {
                                addDriverSelectedSheet =
                                    AddNewDriverBottomSheetCaller.DOB_YEAR
                                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = true
                            }
                        )
                    },
                    modifier = Modifier.weight(1f),
                    readOnly = true,
                    colors = getOutlineTextFieldColors()
                )
            }

            Spacer(modifier = Modifier.height(spaceBwFields))

            // Dropdown Fields
            DropdownField(
                label = "Vehicle Night Parking",
                value = getTitle(driver.vehicleNightParking),
                onValueChange = { },
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.VEHICLE_NIGHT_PARKING
                })

            DropdownField(
                label = "Driver Relationship",
                value = getTitle(driver.driverRelationship),
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_RELATION
                },
                onValueChange = { }
            )

            DropdownField(
                label = "Education",
                value = getTitle(driver.education),
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_EDUCATION
                },
                onValueChange = {})

            DropdownField(
                label = "No Of Children Below 16",
                value = getTitle(driver.childrenBelow16),
                onClick = {
                    addDriverSelectedSheet = AddNewDriverBottomSheetCaller.DRIVER_CHILDREN_BELOW_16
                },
                onValueChange = {}
            )

            Spacer(modifier = Modifier.height(spaceBwFields))

            Text(
                "Health Condition",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                    //color = Color(0xFF333333),
                    //letterSpacing = 0.15.sp,
                    //lineHeight = 24.sp,
                    //fontFamily = FontFamily.Default // You can use a custom font here
                )
            )

            dropDownValues.healthConditionList.insuranceTypeCodeModels.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            performActionOnHealthConditionClick(item)
                        }) {

                    Checkbox(
                        checked = driver.healthConditions.contains(item.code),
                        onCheckedChange = {
                            performActionOnHealthConditionClick(item)
                        },
                        colors = getCheckBoxColors()
                    )

                    Text(
                        text = item.description.en,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    )
                }
            }

            /* DropdownField(
                 label = "Health Condition",
                 value = quoteViewModel.healthCondition,
                 onClick = {
                     addDriverSelectedSheet = AddNewDriverBottomSheetCaller.HEALTH_CONDITION
                 },
                 onValueChange = {
                     quoteViewModel.healthCondition = it
                 }
             )*/

            Spacer(modifier = Modifier.height(spaceBwFields))

            Text(
                "Traffic Violations",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                    //color = Color(0xFF333333),
                    //letterSpacing = 0.15.sp,
                    //lineHeight = 24.sp,
                    //fontFamily = FontFamily.Default // You can use a custom font here
                )
            )

            dropDownValues.trafficViolationList.insuranceTypeCodeModels.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .clickable {
                            performActionOnViolationsClick(item)
                        }) {
                    Checkbox(
                        checked = driver.violationCodeIds.contains(item.code),
                        onCheckedChange = {
                            performActionOnViolationsClick(item)
                        },
                        colors = getCheckBoxColors()
                    )

                    Text(
                        text = item.description.en,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    )
                }
            }

            /* DropdownField(label = "Traffic Violations",
                 value = quoteViewModel.trafficViolations,
                 onClick = {
                     addDriverSelectedSheet = AddNewDriverBottomSheetCaller.TRAFFIC_VIOLATIONS
                 },
                 onValueChange = {
                     quoteViewModel.trafficViolations = it
                 }
             )*/


            Spacer(modifier = Modifier.height(spaceBwFields))
            DropdownField(
                label = "Driver Business City",
                value = getTitle(driver.driverBusinessCity),
                onClick = {
                    addDriverSelectedSheet = AddNewDriverBottomSheetCaller.DRIVER_BUSINESS_CITY
                },
                onValueChange = {}
            )

            Spacer(modifier = Modifier.height(spaceBwFields))
            DropdownField(
                label = "Driver NOA Last Five Years",
                value = getTitle(driver.driverNoaLastFiveYears),
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_NOA_LAST_FIVE_YEARS
                },
                onValueChange = {}
            )

            Spacer(modifier = Modifier.height(spaceBwFields))
            DropdownField(
                label = "Driver NOC Last Five Years",
                value = getTitle(driver.driverNocLastFiveYears),
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_NOC_LAST_FIVE_YEARS
                },
                onValueChange = {}
            )


            Spacer(modifier = Modifier.height(spaceBwFields))
            HorizontalDivider(modifier = Modifier.height(2.dp))

            if (driver.driverLicenses?.isNotEmpty() == true) {

                Spacer(modifier = Modifier.height(spaceBwFields))
                Text(text = "Licence", color = Color.Black)

                driver.driverLicenses?.forEach {
                    Row(modifier = Modifier.fillMaxWidth()) {

                        Text(
                            modifier = Modifier.fillMaxWidth().weight(1f),
                            text = "${getTitle(it.licenseCountryCode)} - ${it.licenseNumberYears}"
                        )

                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Dropdown",
                            tint = Color.Red,
                            modifier = Modifier.clickable {

                                val item = ArrayList(driver.driverLicenses ?: emptyList())
                                item.remove(it)

                                quoteViewModelParameter.createDriver = driver.copy(
                                    driverLicenses = item
                                )

                            }
                        )
                    }
                }
            }

            if (driver.driverLicenses.size < 2){
                Spacer(modifier = Modifier.height(spaceBwFields))
                DropdownField(
                    label = "Driving Licence Country",
                    value = getTitle(driver.licenseCountryCode),
                    onClick = {
                        addDriverSelectedSheet = AddNewDriverBottomSheetCaller.DRIVING_LICENCE_COUNTRY
                    },
                    onValueChange = {}
                )

                Spacer(modifier = Modifier.height(spaceBwFields))
                OutlinedTextField(
                    value = driver.driverLicense,
                    onValueChange = {
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                driverLicense = it
                            )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Licence Valid For") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = getOutlineTextFieldColors()
                )

                // Add Licence Button
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    colors = AppConstants.getButtonColors(),
                    onClick = {
                        if (driver.licenseCountryCode.description.en.isNotEmpty() &&
                            driver.driverLicense.isNotEmpty()
                        ) {

                            val item =
                                ArrayList(driver.driverLicenses ?: emptyList())
                            item.add(AddLicenseUiData(driver.licenseCountryCode, driver.driverLicense))

                            quoteViewModelParameter.createDriver = driver.copy(
                                driverLicenses = item
                            )
                        }
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Add Licence", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(spaceBwFields))
            HorizontalDivider(modifier = Modifier.height(2.dp))

            Spacer(modifier = Modifier.height(spaceBwFields))
            Button(
                colors = AppConstants.getButtonColors(),
                onClick = {
                    quoteViewModel.createDriver()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Add Driver", color = Color.White)
            }

        }
    }

    if (quoteViewModel.vehicleSpecificationsFieldsSheetVisible) {

        BottomSheet(
            title = "Vehicle Specification",
            data = when (addDriverSelectedSheet) {
                AddNewDriverBottomSheetCaller.DOB_MONTH -> if (quoteViewModelParameter.createDriver.driverId.startsWith("2")) dropDownValues.monthsEnglish else dropDownValues.monthsArabic
                AddNewDriverBottomSheetCaller.DOB_YEAR -> if (quoteViewModelParameter.createDriver.driverId.startsWith("2")) dropDownValues.englishYears else dropDownValues.arabicYears
                AddNewDriverBottomSheetCaller.VEHICLE_NIGHT_PARKING -> dropDownValues.vehicleParking
                AddNewDriverBottomSheetCaller.DRIVER_EDUCATION -> dropDownValues.educationList
                AddNewDriverBottomSheetCaller.DRIVER_CHILDREN_BELOW_16 -> dropDownValues.noOfChildren
                AddNewDriverBottomSheetCaller.DRIVER_BUSINESS_CITY -> dropDownValues.driverBusinessCityList
                AddNewDriverBottomSheetCaller.DRIVER_NOA_LAST_FIVE_YEARS -> dropDownValues.accidentCount
                AddNewDriverBottomSheetCaller.DRIVER_NOC_LAST_FIVE_YEARS -> dropDownValues.accidentCount
                AddNewDriverBottomSheetCaller.DRIVING_LICENCE_COUNTRY -> dropDownValues.drivingLicenceCountryList
                AddNewDriverBottomSheetCaller.DRIVER_RELATION -> dropDownValues.driverRelation
            },
            onDismiss = {
                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = false
            },
            onSelected = {
                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = false

                when (addDriverSelectedSheet) {
                    AddNewDriverBottomSheetCaller.DOB_MONTH ->
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                dobMonth = it
                            )

                    AddNewDriverBottomSheetCaller.DOB_YEAR ->
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                dobYear = it
                            )

                    AddNewDriverBottomSheetCaller.VEHICLE_NIGHT_PARKING ->
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                vehicleNightParking = it
                            )

                    AddNewDriverBottomSheetCaller.DRIVER_EDUCATION ->
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                education = it
                            )

                    AddNewDriverBottomSheetCaller.DRIVER_CHILDREN_BELOW_16 ->
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                childrenBelow16 = it
                            )

                    AddNewDriverBottomSheetCaller.DRIVER_BUSINESS_CITY ->
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                driverBusinessCity = it
                            )

                    AddNewDriverBottomSheetCaller.DRIVER_NOA_LAST_FIVE_YEARS ->
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                driverNoaLastFiveYears = it
                            )

                    AddNewDriverBottomSheetCaller.DRIVER_NOC_LAST_FIVE_YEARS ->
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                driverNocLastFiveYears = it
                            )

                    AddNewDriverBottomSheetCaller.DRIVING_LICENCE_COUNTRY ->
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                licenseCountryCode = it
                            )

                    AddNewDriverBottomSheetCaller.DRIVER_RELATION ->
                        quoteViewModelParameter.createDriver =
                            quoteViewModelParameter.createDriver.copy(
                                driverRelationship = it
                            )

                }

            }
        )
    }

    if (quoteViewModel.createDriver.driverId.length == 10) {
        quoteViewModelParameter.searchUserByNationalId(
            quoteViewModelParameter.createDriver.driverId,
            quoteViewModelParameter.createDriver.dobMonth.code.toString(),
            quoteViewModelParameter.createDriver.dobYear.description.en
        )
    }
}

fun performActionOnHealthConditionClick(item: InsuranceTypeCodeModel) {

    val list = ArrayList(driver.healthConditions)
    if (!list.contains(item.code)) {
        list.add(item.code)
    } else {
        list.remove(item.code)
    }

    quoteViewModel.createDriver = driver.copy(
        healthConditions = list,
    )
}

fun performActionOnViolationsClick(item: InsuranceTypeCodeModel) {

    val list = ArrayList(driver.violationCodeIds)
    if (!list.contains(item.code)) {
        list.add(item.code)
    } else {
        list.remove(item.code)
    }

    quoteViewModel.createDriver = driver.copy(
        violationCodeIds = list,
    )
}


@Composable
fun DropdownField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit
) {

    Box(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(label) },
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Dropdown",
                    modifier = Modifier.clickable {
                        onClick()
                        quoteViewModel.vehicleSpecificationsFieldsSheetVisible = true
                    }
                )
            },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            colors = getOutlineTextFieldColors()
        )
    }
}


