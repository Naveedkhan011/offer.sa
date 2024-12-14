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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.screen.quotes_screen.QuotesViewModel
import utils.AppConstants.Companion.getCheckBoxColors
import utils.AppConstants.Companion.getOutlineTextFieldColors


var addDriverSelectedSheet = AddNewDriverBottomSheetCaller.DOB_MONTH

enum class AddNewDriverBottomSheetCaller {
    DOB_MONTH,
    DOB_YEAR,
    VEHICLE_NIGHT_PARKING,
    DRIVER_RELATIONSHIP,
    EDUCATION,
    NO_OF_CHILDREN_BELOW_16,
    HEALTH_CONDITION,
    TRAFFIC_VIOLATIONS,
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

            Spacer(modifier = Modifier.height(8.dp))

            // Driver ID
            OutlinedTextField(
                value = quoteViewModel.driverId,
                onValueChange = { quoteViewModel.driverId = it },
                label = { Text("Driver ID") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // DOB Month & Year
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                OutlinedTextField(
                    value = quoteViewModel.dobMonth,
                    onValueChange = { quoteViewModel.dobMonth = it },
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
                    modifier = Modifier.weight(1f),
                    readOnly = true,
                    colors = getOutlineTextFieldColors()
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = quoteViewModel.dobYear,
                    onValueChange = { quoteViewModel.dobYear = it },
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

            Spacer(modifier = Modifier.height(8.dp))

            // Dropdown Fields
            DropdownField(
                label = "Vehicle Night Parking",
                value = quoteViewModel.vehicleNightParking,
                onValueChange = { quoteViewModel.vehicleNightParking = it },
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.VEHICLE_NIGHT_PARKING
                })

            DropdownField(
                label = "Driver Relationship",
                value = quoteViewModel.driverRelationship,
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_RELATIONSHIP
                },
                onValueChange = { quoteViewModel.driverRelationship = it }
            )

            DropdownField(
                label = "Education",
                value = quoteViewModel.education,
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.EDUCATION
                },
                onValueChange = {
                    quoteViewModel.education = it
                })

            DropdownField(
                label = "No Of Children Below 16",
                value = quoteViewModel.noOfChildrenBelow16,
                onClick = {
                    addDriverSelectedSheet = AddNewDriverBottomSheetCaller.NO_OF_CHILDREN_BELOW_16
                },
                onValueChange = {
                    quoteViewModel.noOfChildrenBelow16 = it
                }
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

            quoteViewModel.healthConditionList?.insuranceTypeCodeModels?.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .clickable {}) {
                    Checkbox(
                        checked = item?.isChecked ?: false,
                        onCheckedChange = {
                            item?.isChecked = it
                        },
                        colors = getCheckBoxColors()
                    )

                    Text(
                        text = item?.description?.en ?: "",
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

            quoteViewModel.trafficViolationList?.insuranceTypeCodeModels?.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .clickable {}) {
                    Checkbox(
                        checked = item?.isChecked ?: false,
                        onCheckedChange = {
                            item?.isChecked = it
                        },
                        colors = getCheckBoxColors()
                    )

                    Text(
                        text = item?.description?.en ?: "",
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

            DropdownField(
                label = "Driver Business City",
                value = quoteViewModel.driverBusinessCity,
                onClick = {
                    addDriverSelectedSheet = AddNewDriverBottomSheetCaller.DRIVER_BUSINESS_CITY
                },
                onValueChange = {
                    quoteViewModel.driverBusinessCity = it
                }
            )

            DropdownField(
                label = "Driver NOA Last Five Years",
                value = quoteViewModel.driverNOALastFiveYears,
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_NOA_LAST_FIVE_YEARS
                },
                onValueChange = {
                    quoteViewModel.driverNOALastFiveYears = it
                }
            )
            DropdownField(
                label = "Driver NOC Last Five Years",
                value = quoteViewModel.driverNocLastFiveYears,
                onClick = {
                    addDriverSelectedSheet =
                        AddNewDriverBottomSheetCaller.DRIVER_NOC_LAST_FIVE_YEARS
                },
                onValueChange = {
                    quoteViewModel.driverNocLastFiveYears = it
                }
            )

            DropdownField(
                label = "Driving Licence Country",
                value = quoteViewModel.drivingLicenceCountry,
                onClick = {
                    addDriverSelectedSheet = AddNewDriverBottomSheetCaller.DRIVING_LICENCE_COUNTRY
                },
                onValueChange = {
                    quoteViewModel.drivingLicenceCountry = it
                }
            )

            // Driver ID
            OutlinedTextField(
                value = quoteViewModel.licenceValidFor,
                onValueChange = { quoteViewModel.licenceValidFor = it },
                label = { Text("Licence Valid For") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Add Licence Button
            Button(
                onClick = { /* Handle Add Licence */ },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Add Licence")
            }

        }
    }

    if (quoteViewModel.vehicleSpecificationsFieldsSheetVisible) {

        BottomSheet(
            title = "Vehicle Specification",
            data = when (addDriverSelectedSheet) {
                AddNewDriverBottomSheetCaller.DOB_MONTH -> quoteViewModel.months
                AddNewDriverBottomSheetCaller.DOB_YEAR -> quoteViewModel.years
                AddNewDriverBottomSheetCaller.VEHICLE_NIGHT_PARKING -> quoteViewModel.vehicleParking
                AddNewDriverBottomSheetCaller.DRIVER_RELATIONSHIP -> quoteViewModel.driverRelation
                AddNewDriverBottomSheetCaller.EDUCATION -> quoteViewModel.educationList
                AddNewDriverBottomSheetCaller.NO_OF_CHILDREN_BELOW_16 -> quoteViewModel.noOfChildren
                AddNewDriverBottomSheetCaller.HEALTH_CONDITION -> quoteViewModel.healthConditionList
                AddNewDriverBottomSheetCaller.TRAFFIC_VIOLATIONS -> quoteViewModel.trafficViolationList
                AddNewDriverBottomSheetCaller.DRIVER_BUSINESS_CITY -> quoteViewModel.driverBusinessCityList
                AddNewDriverBottomSheetCaller.DRIVER_NOA_LAST_FIVE_YEARS -> quoteViewModel.accidentCount
                AddNewDriverBottomSheetCaller.DRIVER_NOC_LAST_FIVE_YEARS -> quoteViewModel.accidentCount
                AddNewDriverBottomSheetCaller.DRIVING_LICENCE_COUNTRY -> quoteViewModel.drivingLicenceCountryList
            },
            onDismiss = {
                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = false
            },
            onSelected = {
                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = false
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


