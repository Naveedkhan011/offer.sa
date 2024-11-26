package presentation.screen.insurance_type

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetWithExpandableCards(onDismissRequest: () -> Unit) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
                .fillMaxHeight(0.8f).verticalScroll(rememberScrollState())
        ) {
            Text("Expandable Cards", fontSize = 18.sp, color = Color.Black)

            Spacer(modifier = Modifier.height(16.dp))

            // Three expandable cards
            ExpandableCard(
                title = "Used car purchasex",
                description = "This is the description for Card 1.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1."
            )
            ExpandableCard(
                title = "Card 2",
                description = "This is the description for Card 2.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1."
            )
            ExpandableCard(
                title = "Card 3",
                description = "This is the description for Card 3.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1.This is the description for Card 1."
            )
        }
    }
}

@Composable
fun ExpandableCard(title: String, description: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Start Icon",
                    tint = Color(0xFF1976D2),
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = "Expand/Collapse Icon",
                    tint = Color.Gray
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = androidx.compose.animation.expandVertically(animationSpec = tween(300)),
                exit = androidx.compose.animation.shrinkVertically(animationSpec = tween(300))
            ) {
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}
