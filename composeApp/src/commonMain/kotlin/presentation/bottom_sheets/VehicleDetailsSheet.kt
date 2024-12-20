package presentation.bottom_sheets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dropDownValues
import presentation.screen.quotes_screen.QuotesViewModel
import presentation.screen.quotes_screen.getTitle
import utils.AppConstants.Companion.getCheckBoxColors
import utils.AppConstants.Companion.getOutlineTextFieldColors

enum class SpecificationBottomSheetCaller {
    EXPECTED_KM, VEHICLE_PARKED_AT_NIGHT, ACCIDENT_COUNT, TRANSMISSION_TYPE, ANY_MODIFICATION
}

var selectedSheet = SpecificationBottomSheetCaller.EXPECTED_KM

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleSpecificationsBottomSheet(
    quoteViewModel: QuotesViewModel,
    onDismiss: () -> Unit,
    onSelected: (selectedMonth: String) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 45.dp)
                .fillMaxHeight(0.8f)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Other Details",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = getTitle(quoteViewModel.vehicleUiData.km),
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            selectedSheet = SpecificationBottomSheetCaller.EXPECTED_KM
                            quoteViewModel.vehicleSpecificationsFieldsSheetVisible = true
                        }
                    )
                },
                label = { Text("Expected KM per year") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = getTitle(quoteViewModel.vehicleUiData.vehicleOvernightParkingLocation),
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            selectedSheet = SpecificationBottomSheetCaller.VEHICLE_PARKED_AT_NIGHT
                            quoteViewModel.vehicleSpecificationsFieldsSheetVisible = true
                        }
                    )
                },
                label = { Text("Vehicle parked at night") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = getTitle(quoteViewModel.vehicleUiData.accidentCount),
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            selectedSheet = SpecificationBottomSheetCaller.ACCIDENT_COUNT
                            quoteViewModel.vehicleSpecificationsFieldsSheetVisible = true
                        }
                    )
                },
                label = { Text("Accident count") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = getTitle(quoteViewModel.vehicleUiData.transmission),
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            selectedSheet = SpecificationBottomSheetCaller.TRANSMISSION_TYPE
                            quoteViewModel.vehicleSpecificationsFieldsSheetVisible = true
                        }
                    )
                },
                label = { Text("Transmission type") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = getTitle(quoteViewModel.vehicleUiData.vehicleModification),
                onValueChange = {},
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            selectedSheet = SpecificationBottomSheetCaller.ANY_MODIFICATION
                            quoteViewModel.vehicleSpecificationsFieldsSheetVisible = true
                        }
                    )
                },
                label = { Text("Any Modification") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )


            if (quoteViewModel.vehicleUiData.vehicleModification.code == 2) {

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = quoteViewModel.vehicleUiData.vehicleModificationDetails,
                    onValueChange = {
                        quoteViewModel.vehicleUiData = quoteViewModel.vehicleUiData.copy(
                            vehicleModificationDetails = it
                        )
                    },
                    label = { Text("Modification reason") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = getOutlineTextFieldColors()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Vehicle Specification")

            dropDownValues.vehicleSpecifications.insuranceTypeCodeModels.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            item.code.let { code ->
                                if (quoteViewModel.newSpecificationCodeIds.contains(code)) {
                                    quoteViewModel.newSpecificationCodeIds.remove(code)
                                } else {
                                    quoteViewModel.newSpecificationCodeIds.add(code)
                                }
                                // Assign updated list to vehicleData
                                quoteViewModel.vehicleUiData = quoteViewModel.vehicleUiData.copy(
                                    specificationCodeIds = ArrayList(quoteViewModel.newSpecificationCodeIds)
                                )
                            }
                        }
                ) {
                    Checkbox(
                        colors = getCheckBoxColors(),
                        checked = item.code.let { code ->
                            quoteViewModel.newSpecificationCodeIds.contains(code)
                        },
                        onCheckedChange = { isChecked ->
                            item.code.let { code ->
                                if (isChecked) {
                                    quoteViewModel.newSpecificationCodeIds.add(code)
                                } else {
                                    quoteViewModel.newSpecificationCodeIds.remove(code)
                                }
                                // Assign updated list to vehicleData
                                quoteViewModel.vehicleUiData = quoteViewModel.vehicleUiData.copy(
                                    specificationCodeIds = ArrayList(quoteViewModel.newSpecificationCodeIds)
                                )
                            }
                        }
                    )

                    Text(
                        text = getTitle(item),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxWidth()
                            .padding(vertical = 20.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quoteViewModel.vehicleUiData.approved,
                onValueChange = {
                    quoteViewModel.vehicleUiData = quoteViewModel.vehicleUiData.copy(
                        approved = it
                    )
                },
                label = { Text("In Case Of Accident And Claim Wakala Fix Of Approved Warshas") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )
        }
    }

    if (quoteViewModel.vehicleSpecificationsFieldsSheetVisible) {
        BottomSheet(
            title = "Vehicle Specification",
            data = when (selectedSheet) {
                SpecificationBottomSheetCaller.EXPECTED_KM -> dropDownValues.vehicleMileageExpectedAnnual
                SpecificationBottomSheetCaller.VEHICLE_PARKED_AT_NIGHT -> dropDownValues.vehicleParking
                SpecificationBottomSheetCaller.ANY_MODIFICATION -> dropDownValues.modificationTypes
                SpecificationBottomSheetCaller.TRANSMISSION_TYPE -> dropDownValues.transmissionType
                else -> dropDownValues.accidentCount
            },
            onDismiss = {
                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = false
            },
            onSelected = {
                when (selectedSheet) {
                    SpecificationBottomSheetCaller.EXPECTED_KM -> {
                        quoteViewModel.vehicleUiData = quoteViewModel.vehicleUiData.copy(
                            km = it
                        )
                    }

                    SpecificationBottomSheetCaller.VEHICLE_PARKED_AT_NIGHT -> {
                        quoteViewModel.vehicleUiData = quoteViewModel.vehicleUiData.copy(
                            vehicleOvernightParkingLocation = it
                        )
                    }

                    SpecificationBottomSheetCaller.ANY_MODIFICATION -> {
                        quoteViewModel.vehicleUiData = quoteViewModel.vehicleUiData.copy(
                            vehicleModification = it
                        )
                    }

                    SpecificationBottomSheetCaller.TRANSMISSION_TYPE -> {
                        quoteViewModel.vehicleUiData = quoteViewModel.vehicleUiData.copy(
                            transmission = it
                        )
                    }

                    SpecificationBottomSheetCaller.ACCIDENT_COUNT -> {
                        quoteViewModel.vehicleUiData = quoteViewModel.vehicleUiData.copy(
                            accidentCount = it
                        )
                    }
                }

                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = false
            }
        )
    }

}

