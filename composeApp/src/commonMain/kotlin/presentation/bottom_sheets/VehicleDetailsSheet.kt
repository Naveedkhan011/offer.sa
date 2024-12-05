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
import presentation.screen.quotes_screen.QuotesViewModel
import utils.AppConstants.Companion.getOutlineTextFieldColors

enum class SpecificationBottomSheetCaller {
    EXPECTED_KM, VEHICLE_PARKED_AT_NIGHT, ACCIDENT_COUNT, TRANSMISSION_TYPE, ANY_MODIFICATION, VEHICLE_SPECIFICATION
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
            modifier = Modifier.padding(16.dp)
                .fillMaxHeight(0.8f).verticalScroll(rememberScrollState())
        ) {
            Text(
                "Other Details",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quoteViewModel.expectedKM,
                onValueChange = {
                    quoteViewModel.expectedKM = it
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
                value = quoteViewModel.vehicleParkedAtNight,
                onValueChange = {
                    quoteViewModel.vehicleParkedAtNight = it
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
                value = quoteViewModel.transmissionTypeV,
                onValueChange = {
                    quoteViewModel.transmissionTypeV = it
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
                value = quoteViewModel.modificationV,
                onValueChange = {
                    quoteViewModel.modificationV = it
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


            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quoteViewModel.modificationV,
                onValueChange = {
                    quoteViewModel.modificationV = it
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


            if (quoteViewModel.modificationV == "Yes"){

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = quoteViewModel.reasonForModification,
                    onValueChange = {
                        quoteViewModel.reasonForModification = it
                    },
                    label = { Text("Modification reason") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = getOutlineTextFieldColors()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Vehicle Specification")

            quoteViewModel.vehicleSpecifications?.insuranceTypeCodeModels?.forEach {item->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                        .clickable {}) {
                    Checkbox(
                        checked = item?.isChecked ?: false,
                        onCheckedChange = {
                            item?.isChecked = it
                        }
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

            /*OutlinedTextField(
                value = quoteViewModel.specification,
                onValueChange = {
                    quoteViewModel.specification = it
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            selectedSheet = SpecificationBottomSheetCaller.VEHICLE_SPECIFICATION
                            quoteViewModel.vehicleSpecificationsFieldsSheetVisible = true
                        }
                    )
                },
                label = { Text("Vehicle Specification") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )*/

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quoteViewModel.claim,
                onValueChange = {
                    quoteViewModel.claim = it
                },
                label = { Text("In Case Of Accident And Claim Wakala Fix Of Approved Warshas") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )
        }
    }

    if (quoteViewModel.vehicleSpecificationsFieldsSheetVisible) {
        BottomSheet(
            data = when (selectedSheet) {
                SpecificationBottomSheetCaller.EXPECTED_KM -> quoteViewModel.vehicleMileageExpectedAnnual
                SpecificationBottomSheetCaller.VEHICLE_PARKED_AT_NIGHT -> quoteViewModel.vehicleParking
                SpecificationBottomSheetCaller.ANY_MODIFICATION -> quoteViewModel.modificationTypes
                SpecificationBottomSheetCaller.TRANSMISSION_TYPE -> quoteViewModel.transmissionType
                SpecificationBottomSheetCaller.VEHICLE_SPECIFICATION -> quoteViewModel.vehicleSpecifications
                else -> quoteViewModel.accidentCount
            },
            onDismiss = {
                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = false
            },
            onSelected = {
                quoteViewModel.vehicleData.accidentCount = it.code.toString()
                quoteViewModel.vehicleSpecificationsFieldsSheetVisible = false
            }
        )
    }

}

