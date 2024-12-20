package presentation.screen.quotes_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import presentation.components.DashedBorderCard
import utils.AppColors
import utils.AppConstants

var vehicleInfoDropped by mutableStateOf(false)
var policyHolderInfoDropped by mutableStateOf(false)
var driversInfoDropped by mutableStateOf(false)
var policyDetailsDropped by mutableStateOf(false)
var selectedOption by mutableStateOf(1)

@Composable
fun ReviewInsurancePolicy(quoteViewModel: QuotesViewModel) {
    var couponCode by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .padding(vertical = 10.dp)
        ) {

            DashedBorderCard {
                Column(modifier = Modifier.padding(10.dp)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "GIG",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                        Text(
                            text = "1,377.00 SAR",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    PolicyRow("NCD Discount", "153.00 SAR", Color(0xFF28A745))
                    PolicyRow("Base Premium", "1,530.00 SAR", Color(0xFF28A745))
                    PolicyRow("Accident Loading", "0.00 SAR", Color(0xFF28A745))
                    PolicyRow("Sub Total", "1,377.00 SAR", Color.Black)
                    PolicyRow("VAT", "206.55 SAR", Color.Black)

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Total Amount",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                        Text(
                            text = "1,583.55 SAR",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Text(
                        text = "Includes all fees and taxes",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            /*
                            // Coupon Code Section
                            CouponCodeSection(couponCode) { newCode -> couponCode = newCode }

                            Spacer(modifier = Modifier.height(16.dp))
            */

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {

                // Vehicle Information Section
                VehicleInformationSection()

                Spacer(modifier = Modifier.height(spaceBwFields))

                PolicyHolderInformationSection()

                Spacer(modifier = Modifier.height(spaceBwFields))

                DriverInformationSection()

                Spacer(modifier = Modifier.height(spaceBwFields))

                PolicyDetailSection()

                Spacer(modifier = Modifier.height(spaceBwFields * 2))

                paymentSection()

                Spacer(modifier = Modifier.height(spaceBwFields * 2))

                termsAndConditionsCompose(quoteViewModel)
            }
        }

        Spacer(modifier = Modifier.height(spaceBwFields * 2))
        // Next Button
        Button(
            onClick = { /* Handle Next */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

}

@Composable
fun termsAndConditionsCompose(quoteViewModel: QuotesViewModel) {

    Row(
        modifier = Modifier.fillMaxWidth().clickable{
            quoteViewModel.termsAndConditionAccepted = !quoteViewModel.termsAndConditionAccepted
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = quoteViewModel.termsAndConditionAccepted,
            onCheckedChange = {  },
            colors = AppConstants.getCheckBoxColors()
        )

        Text(
            text = "I Accept All of the below",
            fontSize = 14.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 8.dp),
            textAlign = TextAlign.Start
        )
    }

    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
        termsText(text = "I have read and accept all the details written in quote page and this page.")
        Spacer(modifier = Modifier.height(spaceBwFields))
        termsText(text = "I agree that the estimated value for this vehicle is 2500 SAR.")
        Spacer(modifier = Modifier.height(spaceBwFields))
        termsText(text = "I accept the Terms & Conditions and the insurance company's Terms & Conditions.")
    }

}

@Composable
fun termsText(text: String) {
    // Text
    Text(
        text = text,
        fontSize = 12.sp,
        //  fontWeight = FontWeight.Bold,
        color = Color.Black,
        modifier = Modifier
            .padding(start = 8.dp),
        textAlign = TextAlign.Start
    )
}

@Composable
private fun paymentSection() {


    CustomRadioButton(text = "Mada", icon = Icons.Default.Star, isSelected = selectedOption == 1) {
        selectedOption = 1
    }

    Spacer(modifier = Modifier.height(spaceBwFields))

    CustomRadioButton(text = "Visa", icon = Icons.Default.Star, isSelected = selectedOption == 2) {
        selectedOption = 2
    }

    Spacer(modifier = Modifier.height(spaceBwFields))

    CustomRadioButton(
        text = "MasterCard",
        icon = Icons.Default.Star,
        isSelected = selectedOption == 3
    ) {
        selectedOption = 3
    }

    Spacer(modifier = Modifier.height(spaceBwFields))

    CustomRadioButton(text = "SADAD", icon = Icons.Default.Star, isSelected = selectedOption == 4) {
        selectedOption = 4
    }

}

@Composable
fun CustomRadioButton(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                shape = RoundedCornerShape(20.dp),
                width = 2.dp,
                color = if (isSelected) AppColors.AppColor else Color.LightGray.copy(alpha = 0.2f)
            )
            /*.background(
                color = if (isSelected) AppColors.AppColor else Color.LightGray.copy(alpha = 0.2f),
                shape = RoundedCornerShape(20.dp)
            )*/
            .clickable { onClick() }
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Radio Button
        RadioButton(
            selected = isSelected,
            onClick = { onClick() }, colors = RadioButtonColors(
                selectedColor = AppColors.AppColor,
                unselectedColor = Color.Gray,
                disabledSelectedColor = Color.Gray,
                disabledUnselectedColor = Color.Gray
            )
        )

        // Text
        Text(
            text = text,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            textAlign = TextAlign.Start
        )

        // Icon
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(30.dp).padding(5.dp)
        )
    }
}

@Composable
fun PolicyRow(title: String, value: String, valueColor: Color) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
    }
}

@Composable
fun CouponCodeSection(couponCode: TextFieldValue, onCodeChange: (TextFieldValue) -> Unit) {
    Column {
        Text(
            text = "Coupon Code",
            fontSize = 14.sp,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF0F4F7), RoundedCornerShape(8.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp)
        ) {
            BasicTextField(
                value = couponCode,
                onValueChange = onCodeChange,
                singleLine = true,
                textStyle = LocalTextStyle.current.copy(fontSize = 14.sp, color = Color.Black),
                modifier = Modifier.weight(1f)
            )
            TextButton(
                onClick = { /* Apply Coupon */ }
            ) {
                Text(
                    text = "Apply",
                    color = Color.Gray,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun VehicleInformationSection() {

    Row {
        Text(
            modifier = Modifier.weight(1f).padding(vertical = 5.dp),
            text = "Vehicle Information",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Icon(
            modifier = Modifier.clickable {
                vehicleInfoDropped = !vehicleInfoDropped
            },
            imageVector = if (vehicleInfoDropped) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "Menu"
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    AnimatedVisibility(
        visible = vehicleInfoDropped,
        enter = androidx.compose.animation.expandVertically(animationSpec = tween(300)),
        exit = androidx.compose.animation.shrinkVertically(animationSpec = tween(300))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            VehicleRow("Vehicle Make / Model", "هونداي / اكسنت")
            VehicleRow("Plate Number", "9310 R L D")
            VehicleRow("Color", "9310 R L D")
            VehicleRow("Manufacture Year", "2020")
            VehicleRow("Repair method", "workshop")
        }
    }
}


@Composable
fun PolicyHolderInformationSection() {

    Row {
        Text(
            modifier = Modifier.weight(1f).padding(vertical = 5.dp),
            text = "Policyholder Information",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Icon(
            modifier = Modifier.clickable {
                policyHolderInfoDropped = !policyHolderInfoDropped
            },
            imageVector = if (policyHolderInfoDropped)
                Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "Menu"
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    AnimatedVisibility(
        visible = policyHolderInfoDropped,
        enter = androidx.compose.animation.expandVertically(animationSpec = tween(300)),
        exit = androidx.compose.animation.shrinkVertically(animationSpec = tween(300))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            VehicleRow("Policy Holder ID:", "هونداي / اكسنت")
            VehicleRow("Name:", "9310 R L D")
            VehicleRow("Date of Birth:", "9310 R L D")
            VehicleRow("Address:", "9310 R L D")
        }
    }

}

@Composable
fun DriverInformationSection() {

    Row {
        Text(
            modifier = Modifier.weight(1f).padding(vertical = 5.dp),
            text = "Drivers Information",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Icon(
            modifier = Modifier.clickable {
                driversInfoDropped = !driversInfoDropped
            },
            imageVector = if (driversInfoDropped) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "Menu"
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    AnimatedVisibility(
        visible = driversInfoDropped,
        enter = androidx.compose.animation.expandVertically(animationSpec = tween(300)),
        exit = androidx.compose.animation.shrinkVertically(animationSpec = tween(300))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            VehicleRow("Driver ID:", "هونداي / اكسنت")
            VehicleRow("Name:", "9310 R L D")
            VehicleRow("Date of Birth:", "9310 R L D")
            VehicleRow("Driving Percentage:", "9310 R L D")
        }
    }
}

@Composable
fun PolicyDetailSection() {

    Row {
        Text(
            modifier = Modifier.weight(1f).padding(vertical = 5.dp),
            text = "Policy Details",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Icon(
            modifier = Modifier.clickable {
                policyDetailsDropped = !policyDetailsDropped
            },
            imageVector = if (policyDetailsDropped) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "Menu"
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    AnimatedVisibility(
        visible = policyDetailsDropped,
        enter = androidx.compose.animation.expandVertically(animationSpec = tween(300)),
        exit = androidx.compose.animation.shrinkVertically(animationSpec = tween(300))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp)
        ) {
            VehicleRow("Policy Effective Date:", "هونداي / اكسنت")
            VehicleRow("Policy Issue Date:", "هونداي / اكسنت")
            VehicleRow("Insurance Type:", "هونداي / اكسنت")
            VehicleRow("Policy Expiry Date:", "هونداي / اكسنت")
            VehicleRow("Quote Reference:", "9310 R L D")
        }
    }
}


@Composable
fun VehicleRow(label: String, value: String) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
    }
}
