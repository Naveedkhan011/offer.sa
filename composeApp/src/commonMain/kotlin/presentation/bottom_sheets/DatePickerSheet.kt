package presentation.bottom_sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDefaults.DatePickerHeadline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import presentation.screen.quotes_screen.spaceBwFields
import utils.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateBottomSheet(
    title: String,
    onDismissSheet: () -> Unit,
    onDateSelected: (selectedMonth: String) -> Unit
) {

    val state = rememberDatePickerState()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismissSheet,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                title,
                fontSize = 18.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            DatePicker(
                state = state,
                modifier = Modifier.padding(2.dp).fillMaxWidth(),
                dateFormatter = remember { DatePickerDefaults.dateFormatter() },
                title = null,
                headline = {
                    DatePickerHeadline(
                        selectedDateMillis = state.selectedDateMillis,
                        displayMode = state.displayMode,
                        dateFormatter = DatePickerDefaults.dateFormatter(),
                        modifier = Modifier.padding(16.dp)
                    )
                },
                showModeToggle = true,
                colors = DatePickerDefaults.colors(
                    selectedDayContentColor = Color.White,
                    selectedDayContainerColor = AppColors.AppColor,
                    todayContentColor = AppColors.AppColor,
                    todayDateBorderColor = AppColors.AppColor
                )
            )

            Button(
                modifier = Modifier.padding(bottom = 45.dp, end = spaceBwFields).align(Alignment.End),
                onClick = {
                    if (state.selectedDateMillis != null) {
                        onDateSelected(convertMillisToFormattedDate(state.selectedDateMillis!!))
                    }
                },
                colors = ButtonColors(
                    containerColor = AppColors.AppColor,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.White
                )
            ) {
                Text(
                    text = "Done",
                    modifier = Modifier.padding(start = 18.dp, end = 18.dp)
                )
            }

        }
    }
}

fun convertMillisToFormattedDate(millis: Long): String {
    val instant = Instant.fromEpochMilliseconds(millis)
    val timeZone = TimeZone.currentSystemDefault()
    val localDateTime = instant.toLocalDateTime(timeZone)

    // Return the formatted date as yyyy-MM-dd
    return "${localDateTime.date.year}-${localDateTime.date.monthNumber.toString().padStart(2, '0')}-${localDateTime.date.dayOfMonth.toString().padStart(2, '0')}"
}