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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.screen.quotes_screen.QuotesViewModel
import utils.AppConstants.Companion.getOutlineTextFieldColors

data class VehicleSpecifications(var isChecked: Boolean, val title: String)

val vehicleSpecifications = listOf(
    VehicleSpecifications(false, "Anti-Theft Alarm"),
    VehicleSpecifications(false, "Fire Extinguisher"),
    VehicleSpecifications(false, "Anti-Lock Brake System"),
    VehicleSpecifications(false, "Auto Breaking System"),
    VehicleSpecifications(false, "Cruise Control"),
    VehicleSpecifications(false, "Adaptive Cruise Control"),
    VehicleSpecifications(false, "Engine Type"),
    VehicleSpecifications(false, "Engine Type"),
    VehicleSpecifications(false, "Engine Type"),
    VehicleSpecifications(false, "Engine Type"),
    VehicleSpecifications(false, "Engine Type")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleSpecificationsBottomSheet(
    quoteViewModel: QuotesViewModel,
    onDismiss: () -> Unit,
    onSelected: (selectedMonth: String) -> Unit
) {

    var searchQuery by remember { mutableStateOf("") }

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
                value = quoteViewModel.sequenceNumber,
                onValueChange = {
                    quoteViewModel.sequenceNumber = it
                },
                label = { Text("Sequence Number") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quoteViewModel.sequenceNumber,
                onValueChange = {
                    quoteViewModel.sequenceNumber = it
                },
                label = { Text("Sequence Number") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quoteViewModel.sequenceNumber,
                onValueChange = {
                    quoteViewModel.sequenceNumber = it
                },
                label = { Text("Sequence Number") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quoteViewModel.sequenceNumber,
                onValueChange = {
                    quoteViewModel.sequenceNumber = it
                },
                label = { Text("Sequence Number") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quoteViewModel.sequenceNumber,
                onValueChange = {
                    quoteViewModel.sequenceNumber = it
                },
                label = { Text("Sequence Number") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )



            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = quoteViewModel.sequenceNumber,
                onValueChange = {
                    quoteViewModel.sequenceNumber = it
                },
                label = { Text("Sequence Number") },
                modifier = Modifier.fillMaxWidth(),
                colors = getOutlineTextFieldColors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Vehicle Specifications",
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            repeat(vehicleSpecifications.size) { item ->
                Row(modifier = Modifier.fillMaxWidth().clickable{
                    vehicleSpecifications[item].isChecked = !vehicleSpecifications[item].isChecked
                }) {
                    Checkbox(
                        checked = vehicleSpecifications[item].isChecked,
                        onCheckedChange = { vehicleSpecifications[item].isChecked = it }
                        )
                    Text(vehicleSpecifications[item].title)
                }
            }
        }
    }
}

