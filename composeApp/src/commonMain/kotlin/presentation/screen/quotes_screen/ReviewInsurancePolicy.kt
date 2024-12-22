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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dropDownValues
import getFormatedAmount
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import models.InsuranceTypeCodeModel
import presentation.components.DashedBorderCard
import presentation.components.OtpDialog
import utils.AppColors
import utils.AppConstants
import utils.AppConstants.Companion.getOutlineTextFieldColors

var vehicleInfoDropped by mutableStateOf(false)
var policyHolderInfoDropped by mutableStateOf(false)
var driversInfoDropped by mutableStateOf(false)
var policyDetailsDropped by mutableStateOf(false)
var selectedOption by mutableStateOf(InsuranceTypeCodeModel())

private lateinit var quoteViewModel: QuotesViewModel

@Composable
fun ReviewInsurancePolicy(quoteModel: QuotesViewModel) {

    quoteViewModel = quoteModel
    quoteViewModel.policyDetailsSheetVisible = false

    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .padding(vertical = 10.dp)
        ) {

            Invoice()
            Spacer(modifier = Modifier.height(16.dp))

            /*
                            // Coupon Code Section
                            CouponCodeSection(couponCode) { newCode -> couponCode = newCode }

                            Spacer(modifier = Modifier.height(16.dp))
            */

            Spacer(modifier = Modifier.height(spaceBwFields))
            BillingDetailsForm()

            Spacer(modifier = Modifier.height(spaceBwFields))
            PolicyDetails()

            Spacer(modifier = Modifier.height(spaceBwFields * 2))
            paymentSection()

            Spacer(modifier = Modifier.height(spaceBwFields * 2))
            termsAndConditionsCompose()

        }

        Spacer(modifier = Modifier.height(spaceBwFields * 2))
        // Next Button
        Button(
            onClick = { quoteViewModel.sendPaymentOTP() },
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
        if (quoteViewModel.paymentOTPVisible) {
            OtpDialog(
                phoneNumber = "966 *** *** **${quoteViewModel.paymentOTPResponse.lastTwoDigitMob}",
                onOtpEntered = { enteredOtp ->
                    quoteViewModel.billingDetailsUIData = quoteViewModel.billingDetailsUIData.copy(
                        otp = enteredOtp
                    )
                    quoteViewModel.verifyPaymentOTPAndCreateInvoice()
                    println("OTP Entered: $enteredOtp") // Handle OTP submission
                },
                onResendOtp = {
                    quoteViewModel.sendPaymentOTP()
                    println("Resend OTP triggered") // Handle resend logic
                },
                onClose = {
                    quoteViewModel.paymentOTPVisible = false
                },
                timeRemaining = quoteViewModel.paymentOTPResponse.otpTimeRemaining
            )
        }
    }

}

@Composable
fun PolicyDetails() {

    // Vehicle Information Section
    VehicleInformationSection()

    Spacer(modifier = Modifier.height(spaceBwFields))

    PolicyHolderInformationSection()

    Spacer(modifier = Modifier.height(spaceBwFields))

    DriverInformationSection()

    Spacer(modifier = Modifier.height(spaceBwFields))

    PolicyDetailSection()

}

@Composable
fun Invoice() {

    DashedBorderCard {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier.padding(4.dp),
                    elevation = CardDefaults.cardElevation(2.dp),
                    shape = CircleShape
                ) {
                    KamelImage(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        resource = asyncPainterResource(data = quoteViewModel.selectedQuote.header.companyImage),
                        contentScale = ContentScale.Crop,
                        contentDescription = "A picture of ${quoteViewModel.selectedQuote.header.companyName}"
                    )
                }

                Text(
                    text = getFormatedAmount(quoteViewModel.selectedQuote.products[0]!!.productPrice.toString()),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(color = Color.LightGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(8.dp))
            val invoiceData = quoteViewModel.createInvoiceResponse.data

            if (invoiceData != null) {
                PolicyRow(
                    "Basic Premium",
                    getFormatedAmount(invoiceData.basicPremium.toString()), Color(0xFF28A745)
                )
                if (invoiceData.ncdDiscount != null && invoiceData.ncdDiscount > 0.0) {
                    PolicyRow(
                        "NCD discount",
                        getFormatedAmount(invoiceData.ncdDiscount),
                        Color(0xFF28A745)
                    )
                }
                if (invoiceData.promoDiscount != null && invoiceData.promoDiscount > 0.0) {
                    PolicyRow(
                        "Promo discount",
                        getFormatedAmount(invoiceData.promoDiscount),
                        Color(0xFF28A745)
                    )
                }
                if (invoiceData.loyaltyDiscount != null && invoiceData.loyaltyDiscount > 0.0) {
                    PolicyRow(
                        "Loyalty discount",
                        getFormatedAmount(invoiceData.loyaltyDiscount),
                        Color(0xFF28A745)
                    )
                }
                if (invoiceData.occasionDiscount != null && invoiceData.occasionDiscount > 0.0) {
                    PolicyRow(
                        "OccasionDiscount discount",
                        getFormatedAmount(invoiceData.occasionDiscount),
                        Color(0xFF28A745)
                    )
                }
                if (invoiceData.occasionDiscount != null && invoiceData.occasionDiscount > 0.0) {
                    PolicyRow(
                        "OccasionDiscount discount",
                        getFormatedAmount(invoiceData.occasionDiscount),
                        Color(0xFF28A745)
                    )
                }

                if (!invoiceData.benefitsObject.isNullOrEmpty()) {
                    invoiceData.benefitsObject.forEach {
                        if (it.benefitPrice > 0.0) {
                            PolicyRow(
                                if (currentLanguage == "en") it.benefitNameEn.toString() else it.benefitNameAr.toString(),
                                getFormatedAmount(it.benefitPrice),
                                Color(0xFF28A745)
                            )
                        }
                    }
                }

                if (invoiceData.subTotal > 0.0) {
                    PolicyRow(
                        "Sub Total",
                        getFormatedAmount(invoiceData.subTotal),
                        Color(0xFF28A745)
                    )
                }

                if (invoiceData.totalVat > 0.0) {
                    PolicyRow("VAT", getFormatedAmount(invoiceData.totalVat), Color.Black)
                }

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
                        text = getFormatedAmount(invoiceData.totalAmountWithVat),
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
    }

}

@Composable
fun BillingDetailsForm() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(10.dp)),
        elevation = CardDefaults.cardElevation(3.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxWidth().background(Color.White).padding(10.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = "Billing Information", style = MaterialTheme.typography.titleLarge)

            Spacer(modifier = Modifier.height(spaceBwFields))

            // First and Last Name
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    OutlinedTextField(
                        value = quoteViewModel.billingDetailsUIData.customerGivenName,
                        onValueChange = {
                            quoteViewModel.billingDetailsUIData =
                                quoteViewModel.billingDetailsUIData.copy(
                                    customerGivenName = it
                                )
                        },
                        isError = quoteViewModel.billingDetailsUIData.customerGivenNameError != null,
                        label = { Text(text = "First Name") },
                        colors = getOutlineTextFieldColors()
                    )
                    addErrorText(quoteViewModel.billingDetailsUIData.customerGivenNameError)
                }

                Column(
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) {
                    OutlinedTextField(
                        value = quoteViewModel.billingDetailsUIData.customerSurname,
                        onValueChange = {
                            quoteViewModel.billingDetailsUIData =
                                quoteViewModel.billingDetailsUIData.copy(
                                    customerSurname = it
                                )
                        },
                        isError = quoteViewModel.billingDetailsUIData.customerSurnameError != null,
                        label = { Text(text = "Last Name") },
                        colors = getOutlineTextFieldColors()
                    )
                    addErrorText(quoteViewModel.billingDetailsUIData.customerSurnameError)
                }
            }

            Spacer(modifier = Modifier.height(spaceBwFields))
            OutlinedTextField(
                value = quoteViewModel.billingDetailsUIData.customerEmail,
                onValueChange = {
                    quoteViewModel.billingDetailsUIData = quoteViewModel.billingDetailsUIData.copy(
                        customerEmail = it
                    )
                },
                isError = quoteViewModel.billingDetailsUIData.customerEmailError != null,
                label = { Text(text = "Email") },
                colors = getOutlineTextFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )
            addErrorText(quoteViewModel.billingDetailsUIData.customerEmailError)


            Spacer(modifier = Modifier.height(spaceBwFields))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    OutlinedTextField(
                        value = quoteViewModel.billingDetailsUIData.billingMobileNo,
                        onValueChange = {
                            quoteViewModel.billingDetailsUIData =
                                quoteViewModel.billingDetailsUIData.copy(
                                    billingMobileNo = it
                                )
                        },
                        isError = quoteViewModel.billingDetailsUIData.billingMobileNoError != null,
                        label = { Text(text = "Mobile Number") },
                        colors = getOutlineTextFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    addErrorText(quoteViewModel.billingDetailsUIData.billingMobileNoError)
                }

                Column(
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) {
                    OutlinedTextField(
                        value = quoteViewModel.billingDetailsUIData.billingStreet1,
                        onValueChange = {
                            quoteViewModel.billingDetailsUIData =
                                quoteViewModel.billingDetailsUIData.copy(
                                    billingStreet1 = it
                                )
                        },
                        isError = quoteViewModel.billingDetailsUIData.billingStreet1Error != null,
                        label = { Text(text = "Street") },
                        colors = getOutlineTextFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    addErrorText(quoteViewModel.billingDetailsUIData.billingStreet1Error)
                }
            }

            Spacer(modifier = Modifier.height(spaceBwFields))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    OutlinedTextField(
                        value = quoteViewModel.billingDetailsUIData.billingCity,
                        onValueChange = {
                            quoteViewModel.billingDetailsUIData =
                                quoteViewModel.billingDetailsUIData.copy(
                                    billingCity = it
                                )
                        },
                        isError = quoteViewModel.billingDetailsUIData.billingCityError != null,
                        label = { Text(text = "City") },
                        colors = getOutlineTextFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    addErrorText(quoteViewModel.billingDetailsUIData.billingCityError)
                }

                Column(
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) {
                    OutlinedTextField(
                        value = quoteViewModel.billingDetailsUIData.billingState,
                        onValueChange = {
                            quoteViewModel.billingDetailsUIData =
                                quoteViewModel.billingDetailsUIData.copy(
                                    billingState = it
                                )
                        },
                        isError = quoteViewModel.billingDetailsUIData.billingStateError != null,
                        label = { Text(text = "State") },
                        colors = getOutlineTextFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    addErrorText(quoteViewModel.billingDetailsUIData.billingStateError)
                }
            }

            Spacer(modifier = Modifier.height(spaceBwFields))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(
                    modifier = Modifier.weight(1f).padding(end = 4.dp)
                ) {
                    OutlinedTextField(
                        value = getTitle(quoteViewModel.billingDetailsUIData.billingCountry),
                        onValueChange = {
                            quoteViewModel.billingDetailsUIData =
                                quoteViewModel.billingDetailsUIData.copy(
                                    billingState = it
                                )
                        },
                        trailingIcon = {
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown",
                                modifier = Modifier.clickable {
                                    quoteViewModel.selectedSheet = BottomSheetCaller.COUNTRY
                                    quoteViewModel.isSheetVisible = true
                                }
                            )
                        },
                        isError = quoteViewModel.billingDetailsUIData.billingCountryError != null,
                        label = { Text(text = "Country") },
                        colors = getOutlineTextFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    addErrorText(quoteViewModel.billingDetailsUIData.billingCountryError)

                }

                Column(
                    modifier = Modifier.weight(1f).padding(start = 4.dp)
                ) {
                    OutlinedTextField(
                        value = quoteViewModel.billingDetailsUIData.billingPostcode,
                        onValueChange = {
                            quoteViewModel.billingDetailsUIData =
                                quoteViewModel.billingDetailsUIData.copy(
                                    billingPostcode = it
                                )
                        },
                        isError = quoteViewModel.billingDetailsUIData.billingPostcodeError != null,
                        label = { Text(text = "Postcode") },
                        colors = getOutlineTextFieldColors(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    addErrorText(quoteViewModel.billingDetailsUIData.billingPostcodeError)
                }
            }


            Spacer(modifier = Modifier.height(spaceBwFields))
            OutlinedTextField(
                value = quoteViewModel.billingDetailsUIData.billingIban,
                onValueChange = {
                    quoteViewModel.billingDetailsUIData = quoteViewModel.billingDetailsUIData.copy(
                        billingIban = it
                    )
                },
                leadingIcon = {
                    Text("SA")
                },
                isError = quoteViewModel.billingDetailsUIData.billingIbanError != null,
                label = { Text(text = "SA IBAN Number") },
                colors = getOutlineTextFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )
            addErrorText(quoteViewModel.billingDetailsUIData.billingIbanError)


            Spacer(modifier = Modifier.height(spaceBwFields))
            OutlinedTextField(
                value = quoteViewModel.billingDetailsUIData.billingVat,
                onValueChange = {
                    quoteViewModel.billingDetailsUIData = quoteViewModel.billingDetailsUIData.copy(
                        billingVat = it
                    )
                },
                isError = quoteViewModel.billingDetailsUIData.billingVatError != null,
                label = { Text(text = "VAT Number (Optional)") },
                colors = getOutlineTextFieldColors(),
                modifier = Modifier.fillMaxWidth()
            )
            addErrorText(quoteViewModel.billingDetailsUIData.billingVatError)


            /*// Payment Method
            Text(text = "Select Payment Method:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButtonWithLabel(
                    selected = billingInfo.paymentMethod == "Mada",
                    onClick = { billingInfo = billingInfo.copy(paymentMethod = "Mada") },
                    label = "Mada"
                )
                RadioButtonWithLabel(
                    selected = billingInfo.paymentMethod == "Visa",
                    onClick = { billingInfo = billingInfo.copy(paymentMethod = "Visa") },
                    label = "Visa"
                )
                RadioButtonWithLabel(
                    selected = billingInfo.paymentMethod == "MasterCard",
                    onClick = { billingInfo = billingInfo.copy(paymentMethod = "MasterCard") },
                    label = "MasterCard"
                )
                RadioButtonWithLabel(
                    selected = billingInfo.paymentMethod == "SADAD",
                    onClick = { billingInfo = billingInfo.copy(paymentMethod = "SADAD") },
                    label = "SADAD"
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Submit Button
            Button(
                onClick = {
                    errors = BillingInfo(
                        firstName = if (billingInfo.firstName.isEmpty()) "Error" else "",
                        lastName = if (billingInfo.lastName.isEmpty()) "Error" else "",
                        email = if (billingInfo.email.isEmpty()) "Error" else "",
                        mobileNumber = if (billingInfo.mobileNumber.isEmpty()) "Error" else "",
                        street = if (billingInfo.street.isEmpty()) "Error" else "",
                        city = if (billingInfo.city.isEmpty()) "Error" else "",
                        state = if (billingInfo.state.isEmpty()) "Error" else "",
                        postcode = if (billingInfo.postcode.isEmpty()) "Error" else "",
                        iban = if (billingInfo.iban.isEmpty()) "Error" else ""
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Submit")
            }*/
        }
    }

}

@Composable
fun termsAndConditionsCompose() {

    Row(
        modifier = Modifier.fillMaxWidth().clickable {
            quoteViewModel.termsAndConditionAccepted = !quoteViewModel.termsAndConditionAccepted
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = quoteViewModel.termsAndConditionAccepted,
            onCheckedChange = { },
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

    dropDownValues.paymentMethodsList.insuranceTypeCodeModels.forEach {

        CustomRadioButton(
            text = getTitle(it),
            icon = Icons.Default.Star,
            isSelected = selectedOption == it
        ) {
            quoteViewModel.billingDetailsUIData = quoteViewModel.billingDetailsUIData.copy(
                cardType = it.description.en
            )
            selectedOption = it
        }
        Spacer(modifier = Modifier.height(spaceBwFields))

    }


    /*
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
        }*/

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
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(end = 10.dp).weight(2f),
            text = title,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            modifier = Modifier.weight(1f),
            text = value,
            textAlign = TextAlign.End,
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
    val vehicle = quoteViewModel.vehicleList[0]
    val vehiclePlateChar =
        vehicle.vehiclePlateText1 + " " + vehicle.vehiclePlateText2 + " " + vehicle.vehiclePlateText3

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
            VehicleRow("Vehicle Make / Model", "${vehicle.vehicleMaker}  ${vehicle.vehicleModel}")
            VehicleRow("Plate Number", "${vehicle.vehiclePlateNumber} $vehiclePlateChar")
            VehicleRow("Sequence Number", "${vehicle.sequenceNumber}")
            VehicleRow("Color", "${vehicle.vehicleMajorColor}")
            VehicleRow("Manufacture Year", "${vehicle.manufactureYear}")
            VehicleRow(
                "Repair method",
                if (vehicle.vehicleAgencyRepair != null) "Agency" else "Workshop"
            )
        }
    }
}


@Composable
fun PolicyHolderInformationSection() {

    val vehicle = quoteViewModel.vehicleList[0]
    val policyHolder = vehicle.policyHolder

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
            VehicleRow("Policy Holder ID:", "${policyHolder?.nationalId}")
            VehicleRow(
                "Name:",
                "${if (currentLanguage == "en") policyHolder?.fullNameEn else policyHolder?.fullNameAr}"
            )
            VehicleRow("Date of Birth:", "${policyHolder?.dobMonth} / ${policyHolder?.dobYear}")
            VehicleRow("Address:", "${policyHolder?.street}")
        }
    }

}

@Composable
fun DriverInformationSection() {

    val vehicle = quoteViewModel.vehicleList[0]
    val driver = vehicle.drivers

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
        driver?.forEach {
            Column(
                modifier = Modifier.fillMaxWidth().padding(10.dp)
            ) {
                VehicleRow("Driver ID:", it.driverId.toString())
                VehicleRow(
                    "Name:",
                    "${if (currentLanguage == "en") it.fullEnglishName else it.fullArabicName}"
                )
                VehicleRow("Date of Birth:", "${it.dobMonth} / ${it.dobYear}")
                VehicleRow("Driving Percentage:", "${it.driverDrivingPercentage}")
            }
        }
    }
}

@Composable
fun PolicyDetailSection() {
    val vehicle = quoteViewModel.vehicleList[0]
    val policyDetails = vehicle.policyHolder

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
            VehicleRow("Policy Effective Date:", "${policyDetails?.insuranceEffectiveDate}")
            VehicleRow("Policy Issue Date:", "${policyDetails?.insuredIdExpireDate}")
            VehicleRow("Insurance Type:", "${policyDetails?.insuranceType}")
            VehicleRow("Policy Expiry Date:", "${policyDetails?.insuredIdExpireDate}")
            VehicleRow("Quote Reference:", "${policyDetails?.referenceNo}")
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

