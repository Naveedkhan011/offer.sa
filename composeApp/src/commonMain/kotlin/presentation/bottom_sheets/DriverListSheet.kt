package presentation.bottom_sheets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import dropDownValues
import presentation.screen.quotes_screen.QuotesViewModel
import utils.AppColors


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DriverListSheet(
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
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
//            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Top title
            Text(
                text = "Drivers List",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            quoteViewModel.driverList.forEach { driver ->
                // Driver Information Card
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White, shape = RoundedCornerShape(16.dp)),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White) // Set background color here
                            .padding(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            // Driver Icon
                            Icon(
                                imageVector = Icons.Default.Person, // Replace with your driver icon
                                contentDescription = "Driver Icon",
                                modifier = Modifier
                                    .size(20.dp)
                                    .padding(end = 8.dp),
                                tint = Color.Gray
                            )

                            // Driver Name and Info Button
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = driver.fullEnglishName, // Driver name
                                    style = MaterialTheme.typography.labelSmall,
                                    color = Color.Black
                                )
                            }
                            /*
                                                        // Info and Delete Actions
                                                        Row {
                                                            // Info Button
                                                            Text(
                                                                text = "Policyholder Information",
                                                                style = MaterialTheme.typography.labelSmall,
                                                                color = Color.Blue,
                                                                modifier = Modifier
                                                                    .background(Color.LightGray, RoundedCornerShape(8.dp))
                                                                    .padding(4.dp)
                                                                    .clickable { *//* Handle Info Click *//* }
                                )

                                Spacer(modifier = Modifier.width(8.dp))

                                // Delete Icon
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Driver",
                                    tint = Color.Red,
                                    modifier = Modifier.clickable { *//* Handle Delete Click *//* }
                                )
                            }*/

                            if (quoteViewModel.driverList.size > 1){
                                // Delete Icon
                                Icon(
                                    imageVector = Icons.Default.Delete,
                                    contentDescription = "Delete Driver",
                                    tint = Color.Red,
                                    modifier = Modifier.clickable { /* Handle Delete Click */ }
                                )
                            }

                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Driver Details
                        Text(
                            text = "Driver ID: ${driver.driverId}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Driving Percentage
                        Text(
                            text = "Driving Percentage:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Percentage Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            /*var selectedPercentage by remember {
                                mutableStateOf(InsuranceTypeCodeModel())
                            }*/

                            var selectedPercentage by remember {
                                mutableStateOf(false)
                            }

                            dropDownValues.driverPercentageList.insuranceTypeCodeModels.forEach { percentage ->
                                selectedPercentage = percentage.code == driver.driverDrivingPercentage

                                Box(
                                    modifier = Modifier
                                        .padding(horizontal = 2.dp, vertical = 4.dp)
                                        .weight(1f)
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(
                                                color = if (selectedPercentage) AppColors.ToastSuccess else Color.LightGray, // Change background color
                                                shape = RoundedCornerShape(50)
                                            )
                                            .padding(horizontal = 10.dp, vertical = 4.dp)
                                            .clickable {
                                                if (quoteViewModel.driverList.size > 1){
                                                    selectedPercentage = !selectedPercentage
                                                    driver.driverDrivingPercentage = percentage.code
                                                }
                                            },
                                        text = "${percentage.code}%",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (selectedPercentage) Color.White else Color.Black // Change text color
                                        ),
                                        textAlign = TextAlign.Center,
                                    )
                                }


                                /* Button(
                                     onClick = { selectedPercentage = percentage!! },
                                     colors = ButtonDefaults.buttonColors(
                                         containerColor = if (selectedPercentage == percentage) Color.Green else Color.LightGray,
                                         contentColor = Color.White
                                     ),
                                     shape = RoundedCornerShape(50), // Rounded buttons
                                     modifier = Modifier.weight(1f) // Ensures equal width buttons
                                         .padding(horizontal = 4.dp)
                                 ) {
                                     Text(
                                         text = "${percentage?.code}%",
                                         style = MaterialTheme.typography.labelSmall,
                                         textAlign = TextAlign.Center
                                     )
                                 }*/

                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Add New Driver Button
            Button(
                onClick = { quoteViewModel.addNewDriverSheetVisible = true },
                modifier = Modifier.fillMaxWidth().padding(bottom = 45.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.AppColor)
            ) {
                Text(
                    text = "Add New Driver",
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }


    if (quoteViewModel.addNewDriverSheetVisible) {
        AddNewDriverSheet(
            quoteViewModel,
            onDismiss = {
                quoteViewModel.addNewDriverSheetVisible = false
            },
            onSelected = {

            }
        )
    }
}


