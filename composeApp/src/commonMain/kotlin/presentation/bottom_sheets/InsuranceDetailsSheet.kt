package presentation.bottom_sheets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.sp
import currency
import models.ResponseTPL
import openWeb
import presentation.screen.quotes_screen.spaceBwFields
import utils.AppConstants


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsuranceDetailSheet(
    onDismiss: () -> Unit,
    quote: ResponseTPL?
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    var driverChecked by remember { mutableStateOf(false) }
    var passengersChecked by remember { mutableStateOf(false) }
    var roadsideChecked by remember { mutableStateOf(false) }


    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        modifier = Modifier.background(Color.White),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 45.dp)
        ) {
            // Header Section
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    elevation = CardDefaults.cardElevation(2.dp),
                    shape = CircleShape
                ) {
                    Image(
                        modifier = Modifier.size(50.dp).background(Color.White),
                        imageVector = Icons.Default.Add,
                        contentDescription = "logo",
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Gulf Insurance Group",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Text(
                        modifier = Modifier.clickable {
                            openWeb("https://www.google.com/")
                        },
                        text = "Terms and conditions",
                        fontSize = 11.sp,
                        color = Color.Blue
                    )
                }
                Text(
                    modifier = Modifier.weight(1f),
                    text = "$currency 1,583.55",
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(spaceBwFields * 2))

            // Coverage Section
            Text(
                text = "What is covered?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            CoverageItem("Cover for Third Party Bodily Injury")
            CoverageItem("Cover for Third Party Property Damage")

            Spacer(modifier = Modifier.height(16.dp))

            // Additional Coverages
            Text(
                text = "Additional coverages",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(8.dp))

            CheckboxItem(
                label = "Personal Accident cover for driver",
                price = "+ 57.50 SAR",
                checked = driverChecked,
                onCheckedChange = { driverChecked = it }
            )

            CheckboxItem(
                label = "Personal Accident cover for passengers",
                price = "+ 184.00 SAR",
                checked = passengersChecked,
                onCheckedChange = { passengersChecked = it }
            )

            CheckboxItem(
                label = "Roadside Assistance",
                price = "+ 57.50 SAR",
                checked = roadsideChecked,
                onCheckedChange = { roadsideChecked = it }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Buy Policy Button
            Button(
                onClick = { /* Handle policy purchase */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Buy policy", color = Color.White, fontSize = 16.sp)
            }
        }
    }
}


@Composable
fun CoverageItem(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Check,
            contentDescription = null,
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            fontSize = 14.sp,
            color = Color.Gray
        )
    }
}

@Composable
fun CheckboxItem(
    label: String,
    price: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.weight(1f).padding(end = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = AppConstants.getCheckBoxColors()
            )
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Black
            )
        }
        Text(
            text = price,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
    }
}

