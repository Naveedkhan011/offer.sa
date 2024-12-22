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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dropDownValues
import models.AddLicense
import presentation.screen.quotes_screen.QuotesViewModel
import presentation.screen.quotes_screen.currentLanguage
import presentation.screen.quotes_screen.getTitle
import presentation.screen.quotes_screen.spaceBwFields
import utils.AppConstants.Companion.getCheckBoxColors
import utils.AppConstants.Companion.getOutlineTextFieldColors


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

lateinit var quoteViewModel: QuotesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNewDriverSheet(
    quoteViewModelParameter: QuotesViewModel,
    onDismiss: () -> Unit,
    onSelected: (selectedMonth: String) -> Unit
) {

    quoteViewModel = quoteViewModelParameter

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
                value = quoteViewModel.createDriver.driverId,
                onValueChange = {
                    quoteViewModel.createDriver = quoteViewModel.createDriver.copy(driverId = it)
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
                    value = getTitle(quoteViewModel.createDriver.dobMonth),
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
                    value = getTitle(quoteViewModel.createDriver.dobYear),
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
                value = getTitle(quoteViewModel.createDriver.vehicleNightParking),
                onValueChange = { },
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.VEHICLE_NIGHT_PARKING
                })

            DropdownField(
                label = "Driver Relationship",
                value = getTitle(quoteViewModel.createDriver.driverRelationship),
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_RELATION
                },
                onValueChange = { }
            )

            DropdownField(
                label = "Education",
                value = getTitle(quoteViewModel.createDriver.education),
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_EDUCATION
                },
                onValueChange = {})

            DropdownField(
                label = "No Of Children Below 16",
                value = getTitle(quoteViewModel.createDriver.childrenBelow16),
                onClick = {
                    addDriverSelectedSheet = AddNewDriverBottomSheetCaller.DRIVER_CHILDREN_BELOW_16
                },
                onValueChange = {}
            )

            Spacer(modifier = Modifier.height(10.dp))

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
                        .clickable {}) {
                    Checkbox(
                        checked = item.isChecked,
                        onCheckedChange = {
                            item.isChecked = it
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

            Spacer(modifier = Modifier.height(10.dp))

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
                        .clickable {}) {
                    Checkbox(
                        checked = item.isChecked,
                        onCheckedChange = {
                            item.isChecked = it
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


            Spacer(modifier = Modifier.height(10.dp))
            DropdownField(
                label = "Driver Business City",
                value = getTitle(quoteViewModel.createDriver.driverBusinessCity),
                onClick = {
                    addDriverSelectedSheet = AddNewDriverBottomSheetCaller.DRIVER_BUSINESS_CITY
                },
                onValueChange = {}
            )

            Spacer(modifier = Modifier.height(10.dp))
            DropdownField(
                label = "Driver NOA Last Five Years",
                value = getTitle(quoteViewModel.createDriver.driverNoaLastFiveYears),
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_NOA_LAST_FIVE_YEARS
                },
                onValueChange = {}
            )

            Spacer(modifier = Modifier.height(10.dp))
            DropdownField(
                label = "Driver NOC Last Five Years",
                value = getTitle(quoteViewModel.createDriver.driverNocLastFiveYears),
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_NOC_LAST_FIVE_YEARS
                },
                onValueChange = {}
            )


            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(modifier = Modifier.height(2.dp))

            if (quoteViewModelParameter.createDriver.driverLicenses?.isEmpty() != true){
                Text(text = "Licence", color = Color.White)
            }

            quoteViewModelParameter.createDriver.driverLicenses?.forEach {
                Text(text = "${it.licenseCountryCode} - ${it.licenseNumberYears}")
            }

            Spacer(modifier = Modifier.height(10.dp))
            DropdownField(
                label = "Driving Licence Country",
                value = getTitle(quoteViewModel.createDriver.licenseCountryCode),
                onClick = {
                    addDriverSelectedSheet = AddNewDriverBottomSheetCaller.DRIVING_LICENCE_COUNTRY
                },
                onValueChange = {}
            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = quoteViewModel.createDriver.driverId,
                onValueChange = { quoteViewModel.licenceValidFor = it },
                label = { Text("Licence Valid For") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add Licence Button
            Button(
                onClick = {
                    if (!quoteViewModel.createDriver.licenseCountryCode.description.en.isEmpty() ||
                        !quoteViewModel.createDriver.driverLicense.isEmpty()){

                        val item = ArrayList(quoteViewModel.createDriver.driverLicenses ?: emptyList())
                        item.add(AddLicense(1, quoteViewModel.createDriver.driverLicense))

                        quoteViewModel.createDriver = quoteViewModel.createDriver.copy(
                            driverLicenses = item
                        )
                    }
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Add Licence", color = Color.White)
            }

        }
    }

    if (quoteViewModel.vehicleSpecificationsFieldsSheetVisible) {

        BottomSheet(
            title = "Vehicle Specification",
            data = when (addDriverSelectedSheet) {
                AddNewDriverBottomSheetCaller.DOB_MONTH -> if (currentLanguage == "en") dropDownValues.monthsEnglish else dropDownValues.monthsArabic
                AddNewDriverBottomSheetCaller.DOB_YEAR -> if (currentLanguage == "en") dropDownValues.englishYears else dropDownValues.arabicYears
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


