package presentation.screen.quotes_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.ic_baseline_alternate_email_24
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.bottom_sheets.quoteViewModel
import utils.AppColors
import utils.AppConstants.Companion.getOutlineTextFieldColors


@OptIn(ExperimentalResourceApi::class)
@Composable
fun OwnerTransferFormScreen(viewModel: QuotesViewModel) {
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
                    text = "National ID/Iqama ID/Company Unified ID(Buyer)",
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

        Spacer(modifier = Modifier.height(spaceBwFields))

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
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        viewModel.datePickerSheetVisible = true
                    }
                )
            },
            readOnly = true,
            isError = viewModel.effectiveYearError != null,
            modifier = Modifier.fillMaxWidth(),
            colors = getOutlineTextFieldColors()
        )
        addErrorText(viewModel.effectiveYearError)

        // National ID/Iqama ID Field
        Spacer(modifier = Modifier.height(spaceBwFields))
        OutlinedTextField(
            value = viewModel.sellerNationalId,
            onValueChange = { viewModel.sellerNationalId = it },
            label = {
                Text(
                    text = "National ID/Iqama ID/Company Unified ID(Seller)",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp)
                )
            },
            trailingIcon = { Icon(Icons.Default.Info, contentDescription = "Info") },
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.sellerNationalIdError != null,
            colors = getOutlineTextFieldColors()
        )
        addErrorText(viewModel.sellerNationalIdError)

    }
}



