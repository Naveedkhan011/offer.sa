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
import androidx.compose.material3.CheckboxDefaults
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
import presentation.screen.quotes_screen.QuotesViewModel
import presentation.screen.quotes_screen.getTitle
import utils.AppColors
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
                value = quoteViewModel.vehicleData.expectedKMTitle,
                onValueChange = {
                    quoteViewModel.vehicleData.expectedKMTitle = it
                },
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
                value = quoteViewModel.vehicleData.vehicleOvernightParkingLocationTitle,
                onValueChange = {
                    quoteViewModel.vehicleData.vehicleOvernightParkingLocationTitle = it
                },
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
                value = quoteViewModel.vehicleData.accidentCount,
                onValueChange = {
                    quoteViewModel.vehicleData.accidentCount = it
                },
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
                value = quoteViewModel.vehicleData.transmissionTitle,
                onValueChange = {
                    quoteViewModel.vehicleData.transmissionTitle = it
                },
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
                value = quoteViewModel.vehicleData.modification,
                onValueChange = {
                    quoteViewModel.vehicleData.modification = it
                },
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


            if (quoteViewModel.vehicleData.modification == "Yes" || quoteViewModel.vehicleData.modification == "نعم") {

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = quoteViewModel.reasonForModification,
                    onValueChange = {
                        quoteViewModel.reasonForModification = it
                        quoteViewModel.vehicleData.vehicleModificationDetails =
                            quoteViewModel.reasonForModification
                    },
                    label = { Text("Modification reason") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = getOutlineTextFieldColors()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Vehicle Specification")

            quoteViewModel.vehicleSpecifications?.insuranceTypeCodeModels?.forEach { item ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            item?.code?.let { code ->
                                if (quoteViewModel.newSpecificationCodeIds.contains(code)) {
                                    quoteViewModel.newSpecificationCodeIds.remove(code)
                                } else {
                                    quoteViewModel.newSpecificationCodeIds.add(code)
                                }
                                // Assign updated list to vehicleData
                                quoteViewModel.vehicleData = quoteViewModel.vehicleData.copy(
                                    specificationCodeIds = ArrayList(quoteViewModel.newSpecificationCodeIds)
                                )
                            }
                        }
                ) {
                    Checkbox(
                        colors = getCheckBoxColors(),
                        checked = item?.code?.let { code ->
                            quoteViewModel.newSpecificationCodeIds.contains(code)
                        } ?: false,
                        onCheckedChange = { isChecked ->
                            item?.code?.let { code ->
                                if (isChecked) {
                                    quoteViewModel.newSpecificationCodeIds.add(code)
                                } else {
                                    quoteViewModel.newSpecificationCodeIds.remove(code)
                                }
                                // Assign updated list to vehicleData
                                quoteViewModel.vehicleData = quoteViewModel.vehicleData.copy(
                                    specificationCodeIds = ArrayList(quoteViewModel.newSpecificationCodeIds)
                                )
                            }
                        }
                    )

                    Text(
                        text = item?.description?.en.orEmpty(),
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
                value = quoteViewModel.claimText,
                onValueChange = {
                    quoteViewModel.claimText = it
                    quoteViewModel.vehicleData.approved = it
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
                SpecificationBottomSheetCaller.EXPECTED_KM -> quoteViewModel.vehicleMileageExpectedAnnual
                SpecificationBottomSheetCaller.VEHICLE_PARKED_AT_NIGHT -> quoteViewModel.vehicleParking
                SpecificationBottomSheetCaller.ANY_MODIFICATION -> quoteViewModel.modificationTypes
                SpecificationBottomSheetCaller.TRANSMISSION_TYPE -> quoteViewModel.transmissionType
                else -> quoteViewModel.accidentCount
            },
            onDismiss = {
                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = false
            },
            onSelected = {
                when (selectedSheet) {
                    SpecificationBottomSheetCaller.EXPECTED_KM -> {
                        quoteViewModel.vehicleData.expectedKMTitle = getTitle(it)
                        quoteViewModel.vehicleData.km = it.code
                    }

                    SpecificationBottomSheetCaller.VEHICLE_PARKED_AT_NIGHT -> {
                        quoteViewModel.vehicleData.vehicleOvernightParkingLocationTitle =
                            getTitle(it)
                        quoteViewModel.vehicleData.vehicleOvernightParkingLocationCode = it.code
                    }

                    SpecificationBottomSheetCaller.ANY_MODIFICATION -> {
                        quoteViewModel.vehicleData.modification = getTitle(it)
                        quoteViewModel.vehicleData.vehicleModification = it.description.en == "Yes"
                    }

                    SpecificationBottomSheetCaller.TRANSMISSION_TYPE -> {
                        quoteViewModel.vehicleData.transmissionTitle = getTitle(it)
                        quoteViewModel.vehicleData.transmission = it.code

                    }

                    SpecificationBottomSheetCaller.ACCIDENT_COUNT -> {
                        quoteViewModel.vehicleData.accidentCount = getTitle(it)
                    }
                }

                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = false
            }
        )
    }

}

