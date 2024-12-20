package presentation.screen.quotes_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.InsuranceTypeCodeModel
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.ic_baseline_alternate_email_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import utils.AppColors
import utils.AppConstants.Companion.getOutlineTextFieldColors

@OptIn(ExperimentalResourceApi::class)
@Composable
fun IqamaFormScreen(quoteViewModel: QuotesViewModel) {


    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {

        // National ID/Iqama ID Field
        OutlinedTextField(
            value = quoteViewModel.policyHolderUiData.nationalId,
            onValueChange = {
                quoteViewModel.policyHolderUiData = quoteViewModel.policyHolderUiData.copy(
                    nationalId = it
                )
                quoteViewModel.verifyIqamaLocally()
            },
            label = {
                Text(
                    text = "National ID/Iqama ID/Company Unified ID",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                )
            },
            trailingIcon = { Icon(Icons.Default.Info, contentDescription = "Info") },
            modifier = Modifier.fillMaxWidth(),
            isError = quoteViewModel.policyHolderUiData.nationalIDError != null,
            colors = getOutlineTextFieldColors()
        )
        addErrorText(quoteViewModel.policyHolderUiData.nationalIDError)

        Spacer(modifier = Modifier.height(spaceBwFields))

        if (quoteViewModel.policyHolderUiData.nationalId.isNotEmpty()) {
            DropdownField(
                label = "DOB Month",
                onclick = {
                    quoteViewModel.selectedSheet = BottomSheetCaller.MONTH
                },
                modifier = Modifier.fillMaxWidth(),
                errorValue = quoteViewModel.policyHolderUiData.dobMonthError,
                selectedOption = getTitle(quoteViewModel.policyHolderUiData.dobMonth)
            )

            Spacer(modifier = Modifier.height(spaceBwFields))

            DropdownField(
                label = "DOB Year",
                onclick = {
                    quoteViewModel.selectedSheet = BottomSheetCaller.YEAR
                },
                modifier = Modifier.fillMaxWidth(),
                errorValue = quoteViewModel.policyHolderUiData.dobYearError,
                selectedOption = getTitle(quoteViewModel.policyHolderUiData.dobYear)
            )

            Spacer(modifier = Modifier.height(spaceBwFields))
        }

        OutlinedTextField(
            value = quoteViewModel.policyHolderUiData.sequenceNumber,
            onValueChange = {
                quoteViewModel.policyHolderUiData = quoteViewModel.policyHolderUiData.copy(
                    sequenceNumber = it
                )
                quoteViewModel.verifySequenceNumberLocally()
            },
            label = { Text("Sequence Number") },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_baseline_alternate_email_24),
                    contentDescription = null,
                    tint = AppColors.AppColor
                )
            },
            isError = quoteViewModel.policyHolderUiData.sequenceNumberError != null,
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )
        addErrorText(quoteViewModel.policyHolderUiData.sequenceNumberError)

        Spacer(modifier = Modifier.height(spaceBwFields))
        OutlinedTextField(
            value = quoteViewModel.policyHolderUiData.insuranceEffectiveDate,
            onValueChange = {
                quoteViewModel.policyHolderUiData =
                    quoteViewModel.policyHolderUiData.copy(insuranceEffectiveDate = it)
            },
            label = { Text("Effective Date") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = AppColors.AppColor
                )
            },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        quoteViewModel.datePickerSheetVisible = true
                    }
                )
            },
            isError = quoteViewModel.policyHolderUiData.insuranceEffectiveDate.isEmpty(),
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )
        addErrorText(quoteViewModel.policyHolderUiData.insuranceEffectiveDateError)
    }
}

fun getTitle(selectedMonth: InsuranceTypeCodeModel?): String {
    selectedMonth?.let {
        return if (currentLanguage == "en")
            it.description.en
        else
            it.description.ar
    }
    return selectedMonth?.description?.en.toString()
}



