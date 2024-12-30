package presentation.screen.quotes_screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import utils.AppColors


@Composable
fun DriversScreen(quoteViewModel: QuotesViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Drivers List Header with "Edit List"
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Drivers List",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Edit List",
                style = MaterialTheme.typography.bodyMedium.copy(color = AppColors.AppColor),
                modifier = Modifier.clickable { quoteViewModel.driverListSheetVisible = true },
                fontWeight = FontWeight.Bold
            )
        }

        // Subtitle for the Drivers List
        Text(
            text = "you can add, edit or remove",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Drivers List Content
        quoteViewModel.driverList.forEach { driver ->
            Spacer(modifier = Modifier.height(spaceBwFields))
            DriverCard(driver = driver)
        }
    }
}
