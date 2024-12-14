package presentation.screen.insurance_type

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import models.enums.InsuranceType
import navigator
import presentation.screen.quotes_screen.GetQuotes
import utils.AppColors
import utils.language.language_manager.LanguageManager

class InsuranceTypeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        var showBottomSheet by remember { mutableStateOf(false) }
        val selection by remember { mutableStateOf(1) }

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Top App Bar with Back Button
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                },
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier.padding(horizontal = 20.dp),
                text = "Buy Motor Insurance",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    //fontFamily = FontFamily.Serif
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            // List of Insurance Options
            InsuranceCard(
                onCardClick = { navigator.push(GetQuotes(InsuranceType.INSURE_YOUR_VEHICLE)) },
                title = LanguageManager.currentStrings.insureYourVehicle,
                description = "Protect your car with comprehensive insurance coverage.",
                icon = Icons.Default.MailOutline // Replace with appropriate icon resource
            )

            InsuranceCard(
                title = LanguageManager.currentStrings.ownerTransfer,
                onCardClick = { navigator.push(GetQuotes(InsuranceType.OWNER_TRANSFER)) },
                description = "Easily transfer insurance for your newly purchased car.",
                icon = Icons.Default.ShoppingCart // Replace with appropriate icon resource
            )

            InsuranceCard(
                title = LanguageManager.currentStrings.customCard,
                onCardClick = { navigator.push(GetQuotes(InsuranceType.CUSTOM_CARD)) },
                description = "Get specialized coverage for custom or imported cars.",
                icon = Icons.Default.Info // Replace with appropriate icon resource
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(10.dp))
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    showBottomSheet = true
                }
            ) {
                // "Know more about types" Text
                Text(
                    text = "Know more about types",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AppColors.AppColor,
                        fontWeight = FontWeight.Bold
                    )
                )

                Icon(
                    tint = AppColors.AppColor,
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = ""
                )
            }


            // Display the bottom sheet if showBottomSheet is true
            if (showBottomSheet) {
                BottomSheetWithExpandableCards(
                    onDismissRequest = { showBottomSheet = false }
                )
            }
        }
    }


}

@Composable
fun InsuranceCard(
    title: String = "",
    description: String,
    icon: ImageVector,
    onCardClick: (String) -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 8.dp, horizontal = 20.dp)
            .clickable {
                onCardClick.invoke(title)
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(Color.White) // Set background color here
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color(0xFF2196F3),
                    modifier = Modifier
                        .size(50.dp)
                        .padding(end = 16.dp)
                )

                // Text Content
                Column(modifier = Modifier.weight(1f).padding(horizontal = 5.dp)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    )
                }

                // Forward Arrow Icon
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Forward",
                    tint = Color.Gray
                )
            }
        }
    }
}
