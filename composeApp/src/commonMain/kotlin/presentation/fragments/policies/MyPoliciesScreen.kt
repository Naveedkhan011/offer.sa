package presentation.fragments.policies


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import utils.AppColors


@Composable
fun MyPoliciesScreen() {

    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Policies", "Active Quotes")
    var parentWidth by remember { mutableStateOf(0.dp) }
    val current = LocalDensity.current
    val tabBackgroundColor = Color(0xFFE6F7FF)

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)
        .onGloballyPositioned { coordinates ->
            parentWidth = with(current) { coordinates.size.width.toDp() }
        }) {

        CustomTab(
            items = tabs,
            tabBackgroundColor = AppColors.tabBackgroundColor,
            tabWidth = (parentWidth / 2),
            selectedItemIndex = selectedTab,
            selectedTabColor = AppColors.AppColor,
            onClick = { selectedTab = it },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tab Content
        when (selectedTab) {
            0 -> PoliciesTabContent()
            1 -> ActiveQuotesTabContent()
        }
    }
}

@Composable
fun PoliciesTabContent() {
    var selectedSubTab by remember { mutableStateOf(0) }
    val subTabs = listOf("Active", "To Renew", "Expired")

    Column {
        // Sub Tabs
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            subTabs.forEachIndexed { index, title ->
                Button(
                    modifier = Modifier
                        .height(34.dp)
                        .padding(horizontal = 2.dp),
                    onClick = { selectedSubTab = index },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedSubTab == index) Color(0xFF28A745) else White,
                        contentColor = if (selectedSubTab == index) White else Color.Black
                    ),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(
                        1.dp,
                        if (selectedSubTab == index) Color(0xFF28A745) else Color.Gray
                    )
                ) {
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .fillMaxHeight(),
                        textAlign = TextAlign.Center,
                        text = title,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Content for each sub-tab
        when (selectedSubTab) {
            0 -> PoliciesEmptyState("You Don’t Have Any Policies Yet")
            1 -> PoliciesEmptyState("You Don’t Have Any Policies to Renew Yet")
            2 -> PoliciesEmptyState("You Don’t Have Any Expired Policies Yet")
        }
    }
}

@Composable
fun PoliciesEmptyState(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Placeholder for empty state icon or illustration
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color(0xFFE0E0E0), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            // You can add an Image or Icon here
            Text(text = "Icon", color = Color.Gray)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = message,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Gray
        )
    }
}

@Composable
fun ActiveQuotesTabContent() {
    // Add content for the "Active Quotes" tab
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Active Quotes Content Here")
    }
}

@Composable
private fun MyTabIndicator(
    indicatorWidth: Dp,
    indicatorOffset: Dp,
    indicatorColor: Color,
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .width(
                width = indicatorWidth,
            )
            .offset(
                x = indicatorOffset,
            )
            .clip(
                shape = CircleShape,
            )
            .background(
                color = indicatorColor,
            ),
    )
}

@Composable
private fun MyTabItem(
    isSelected: Boolean,
    onClick: () -> Unit,
    tabWidth: Dp,
    text: String,
) {
    val tabTextColor: Color by animateColorAsState(
        targetValue = if (isSelected) {
            White
        } else {
            Black
        },
        animationSpec = tween(easing = LinearEasing),
    )
    Text(
        modifier = Modifier
            .clip(CircleShape)
            .clickable {
                onClick()
            }
            .width(tabWidth)
            .padding(
                vertical = 8.dp,
                horizontal = 12.dp,
            ),
        style = TextStyle(
            fontSize = 14.sp,
        ),
        text = text,
        color = tabTextColor,
        textAlign = TextAlign.Center,
    )
}

@Composable
fun CustomTab(
    selectedItemIndex: Int,
    items: List<String>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 150.dp,
    selectedTabColor: Color = White,
    tabBackgroundColor: Color = Blue,
    onClick: (index: Int) -> Unit,
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * selectedItemIndex,
        animationSpec = tween(easing = LinearEasing),
    )

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(tabBackgroundColor)
            .height(intrinsicSize = IntrinsicSize.Min),
    ) {
        MyTabIndicator(
            indicatorWidth = tabWidth,
            indicatorOffset = indicatorOffset,
            indicatorColor = selectedTabColor,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.clip(CircleShape),
        ) {
            items.mapIndexed { index, text ->
                val isSelected = index == selectedItemIndex
                MyTabItem(
                    isSelected = isSelected,
                    onClick = {
                        onClick(index)
                    },
                    tabWidth = tabWidth,
                    text = text,
                )
            }
        }
    }
}

