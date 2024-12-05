package presentation.screen.quotes_screen

import SHARED_PREFERENCE
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
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
import presentation.screen.login.signInViewModel
import utils.AppColors
import utils.AppConstants
import utils.AppConstants.Companion.getOutlineTextFieldColors
import utils.language.language_manager.LanguageManager

@OptIn(ExperimentalResourceApi::class)
@Composable
fun IqamaFormScreen(viewModel: QuotesViewModel) {


    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth()
    ) {

        // National ID/Iqama ID Field
        OutlinedTextField(
            value = viewModel.nationalID,
            onValueChange = {
                viewModel.nationalID = it
                viewModel.verifyIqamaLocally()
            },
            label = {
                Text(
                    text = "National ID/Iqama ID/Company Unified ID",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                )
            },
            trailingIcon = { Icon(Icons.Default.Info, contentDescription = "Info") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.nationalIDError != null,
            colors = getOutlineTextFieldColors()
        )
        addErrorText(viewModel.nationalIDError)

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
                viewModel.selectedSheet = BottomSheetCaller.MONTH
            },
            modifier = Modifier.fillMaxWidth(),
            errorValue = viewModel.dobError,
            selectedOption = getTitle(viewModel.selectedMonth)
        )

        Spacer(modifier = Modifier.width(spaceBwFields))

        DropdownField(
            label = "DOB Year",
            onclick = {
                viewModel.selectedSheet = BottomSheetCaller.YEAR
            },
            modifier = Modifier.fillMaxWidth(),
            errorValue = viewModel.dobYearError,
            selectedOption = getTitle(viewModel.selectedYear)
        )

        Spacer(modifier = Modifier.height(spaceBwFields))
        OutlinedTextField(
            value = viewModel.sequenceNumber,
            onValueChange = {
                viewModel.sequenceNumber = it
                viewModel.verifySequenceNumberLocally()
            },
            label = { Text("Sequence Number") },
            leadingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_baseline_alternate_email_24),
                    contentDescription = null,
                    tint = AppColors.AppColor
                )
            },
            isError = viewModel.sequenceNumberError != null,
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )
        addErrorText(viewModel.sequenceNumberError)

        Spacer(modifier = Modifier.height(spaceBwFields))
        OutlinedTextField(
            value = viewModel.effectiveYear,
            onValueChange = { viewModel.effectiveYear = it },
            label = { Text("Effective Date") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.MailOutline,
                    contentDescription = null,
                    tint = AppColors.AppColor
                )
            },
            isError = viewModel.effectiveYearError != null,
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )
        addErrorText(viewModel.effectiveYearError)

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



