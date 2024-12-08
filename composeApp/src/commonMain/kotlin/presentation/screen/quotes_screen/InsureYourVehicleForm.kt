package presentation.screen.quotes_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
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
            value = quoteViewModel.nationalID,
            onValueChange = {
                quoteViewModel.nationalID = it
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
            isError = quoteViewModel.nationalIDError != null,
            colors = getOutlineTextFieldColors()
        )
        addErrorText(quoteViewModel.nationalIDError)

        Spacer(modifier = Modifier.height(spaceBwFields))

        /*// DOB Month and Year Dropdowns
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

        }*/

        DropdownField(
            label = "DOB Month",
            onclick = {
                quoteViewModel.selectedSheet = BottomSheetCaller.MONTH
            },
            modifier = Modifier.fillMaxWidth(),
            errorValue = quoteViewModel.dobError,
            selectedOption = getTitle(quoteViewModel.selectedMonth)
        )

        Spacer(modifier = Modifier.width(spaceBwFields))

        DropdownField(
            label = "DOB Year",
            onclick = {
                quoteViewModel.selectedSheet = BottomSheetCaller.YEAR
            },
            modifier = Modifier.fillMaxWidth(),
            errorValue = quoteViewModel.dobYearError,
            selectedOption = getTitle(quoteViewModel.selectedYear)
        )

        Spacer(modifier = Modifier.height(spaceBwFields))
        OutlinedTextField(
            value = quoteViewModel.sequenceNumber,
            onValueChange = {
                quoteViewModel.sequenceNumber = it
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
            isError = quoteViewModel.sequenceNumberError != null,
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )
        addErrorText(quoteViewModel.sequenceNumberError)

        Spacer(modifier = Modifier.height(spaceBwFields))
        OutlinedTextField(
            value = quoteViewModel.effectiveYear,
            onValueChange = { quoteViewModel.effectiveYear = it },
            label = { Text("Effective Date") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.MailOutline,
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
            isError = quoteViewModel.effectiveYearError != null,
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )
        addErrorText(quoteViewModel.effectiveYearError)

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



