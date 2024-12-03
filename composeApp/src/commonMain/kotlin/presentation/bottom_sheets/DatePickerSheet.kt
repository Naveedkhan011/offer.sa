package presentation.bottom_sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDefaults.DatePickerHeadline
import androidx.compose.material3.DatePickerDefaults.DatePickerTitle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
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
import models.enums.ToastType
import showToastUsingLaunchEffect

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateBottomSheet(
    title: String,
    onDismiss: () -> Unit,
    onDateSelected: (selectedMonth: String) -> Unit
) {

    val state = rememberDatePickerState()
    var toast by mutableStateOf(true)

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
                .fillMaxHeight(0.8f)
        ) {

            Text(title, fontSize = 18.sp, color = Color.Black, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(16.dp))

            DatePicker(
                state = state,
                modifier = Modifier.fillMaxWidth(),
                dateFormatter = remember { DatePickerDefaults.dateFormatter() },
                title = {
                    DatePickerTitle(
                        displayMode = state.displayMode,
                        modifier = Modifier.padding(16.dp)
                    )
                },
                headline = {
                    DatePickerHeadline(
                        selectedDateMillis = state.selectedDateMillis,
                        displayMode = state.displayMode,
                        dateFormatter = DatePickerDefaults.dateFormatter(),
                        modifier = Modifier.padding(16.dp)
                    )
                },
                showModeToggle = true,
                colors = DatePickerDefaults.colors()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                toast = !toast
            }) {
                Text(text = "Done")
            }


            if (toast)
                showToastUsingLaunchEffect("clicked", ToastType.ERROR)

        }
    }
}

