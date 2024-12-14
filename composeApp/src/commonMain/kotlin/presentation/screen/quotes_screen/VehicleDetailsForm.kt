package presentation.screen.quotes_screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import currency
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.ic_baseline_alternate_email_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.AppColors
import utils.AppConstants.Companion.getOutlineTextFieldColors


@OptIn(ExperimentalResourceApi::class)
@Composable
fun VehicleDetailsForm(
    quoteViewModel: QuotesViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val selectedCoverageType = quoteViewModel.vehicleData.insuranceType
        val selectedRepairOption = quoteViewModel.vehicleData.vehicleAgencyRepair

        val firstItem = quoteViewModel.vehicleList[0]
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
            //modifier = Modifier.padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(spaceBwFields))
        OutlinedTextField(
            value = quoteViewModel.vehicleData.vehicleUseTitle,
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

        Spacer(modifier = Modifier.height(spaceBwFields))

        OutlinedTextField(
            value = quoteViewModel.vehicleData.vehicleValue,
            onValueChange = {
                quoteViewModel.vehicleData = quoteViewModel.vehicleData.copy(vehicleValue = it)
            },
            label = { Text("Vehicle Estimated Value") },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_baseline_alternate_email_24),
                    contentDescription = null,
                    tint = AppColors.AppColor
                )
            },
            singleLine = true,
            trailingIcon = {
                Text(text = currency, modifier = Modifier.padding(5.dp))
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )

        Spacer(modifier = Modifier.height(spaceBwFields))
        Spacer(modifier = Modifier.height(spaceBwFields))

        Text(
            text = "Choose Your Coverage Type",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Third-Party Card
        CoverageOptionCard(
            title = "Third-Party Insurance",
            description = "In the event of an accident, you'd seek to shield yourself from legal and financial responsibilities for third-party damages.",
            isSelected = selectedCoverageType == 1,
            onClick = {
                quoteViewModel.vehicleData = quoteViewModel.vehicleData.copy(insuranceType = 1)
            },
            showRepairOptions = false
        )

        Spacer(modifier = Modifier.height(spaceBwFields))

        // Comprehensive Card
        CoverageOptionCard(
            title = "Comprehensive",
            description = "Offers broader protection, including damages to your own vehicle.",
            isSelected = selectedCoverageType == 2,
            onClick = {
                quoteViewModel.vehicleData = quoteViewModel.vehicleData.copy(insuranceType = 2)
            },
            showRepairOptions = selectedCoverageType == 2,
            selectedRepairOption = selectedRepairOption,
            onRepairOptionClick = {
                quoteViewModel.vehicleData =
                    quoteViewModel.vehicleData.copy(vehicleAgencyRepair = it)
            }
        )


        Spacer(modifier = Modifier.height(spaceBwFields))

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
fun CoverageOptionCard(
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    showRepairOptions: Boolean = false,
    selectedRepairOption: Int? = null,
    onRepairOptionClick: ((Int) -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                if (isSelected) 1.dp else 0.dp,
                color = AppColors.AppColor,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White//if (isSelected) Color(0xFFE6F7EE) else Color.White
        )
    ) {
        Row {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(50.dp)
                    .padding(10.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    modifier = Modifier
                        .background(
                            Color(0xFFDDEFFF),
                            shape = CircleShape
                        ).align(
                            Alignment.Center
                        ),
                    imageVector = Icons.Filled.Add,
                    contentDescription = null,
                    tint = Color(0xFF4CAF50)
                )
            }


            Column(modifier = Modifier.padding(vertical = 10.dp, horizontal = 5.dp).weight(1f)) {

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    if (isSelected) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = null,
                            tint = Color(0xFF4CAF50)
                        )
                    }
                }

                Text(
                    text = description,
                    fontSize = 10.sp,
                    style = MaterialTheme.typography.bodySmall.copy(
                        lineHeight = 15.sp // Adjust the line height as needed
                    ),
                    color = Color.Gray
                )

                if (selectedRepairOption != null && onRepairOptionClick != null) {
                    Spacer(modifier = Modifier.height(10.dp))

                    Row(horizontalArrangement = Arrangement.SpaceBetween) {
                        RepairOptionChip(
                            label = "Workshop",
                            isSelected = selectedRepairOption == 0,
                            onClick = { onRepairOptionClick(0) }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        RepairOptionChip(
                            label = "Agency",
                            isSelected = selectedRepairOption == 1,
                            onClick = { onRepairOptionClick(1) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RepairOptionChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .background(
                color = if (isSelected) Color(0xFF4CAF50) else Color(0xFFE0E0E0),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) Color.White else Color.Black,
            fontSize = 10.sp
        )
    }
}
