package presentation.screen.quotes_screen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import currency
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import models.Discount
import models.ResponseTPL
import presentation.components.MyTabIndicator
import utils.AppColors
import utils.language.language_manager.LanguageManager.currentLanguage

private lateinit var quoteViewModel : QuotesViewModel
@Composable
fun QuoteScreen(screenModel: QuotesViewModel) {
    var parentWidth by remember { mutableStateOf(0.dp) }
    val current = LocalDensity.current

    Column(modifier = Modifier.fillMaxSize().onGloballyPositioned { coordinates ->
        parentWidth = with(current) { coordinates.size.width.toDp() }
    }) {
        quoteViewModel = screenModel

        // Car Information Header
        CarHeader(screenModel)

        //space
        Spacer(modifier = Modifier.height(16.dp))

        // Tabs
        Tabs(screenModel, parentWidth)

        //space
        Spacer(modifier = Modifier.height(16.dp))

        // Content based on selected tab
        when (screenModel.selectedTab) {
            Tab.ThirdParty -> TabContent(
                screenModel.quotes.responseTPL,
                screenModel.quotes.completed
            )

            Tab.OwnDamage -> TabContent(
                screenModel.quotes.responseTPL,
                screenModel.quotes.completed
            ) // Replace with specific data
            Tab.Comprehensive -> TabContent(
                screenModel.quotes.responseComp,
                screenModel.quotes.completed
            ) // Replace with specific data
        }

    }
}

@Composable
fun CarHeader(quotesViewModel: QuotesViewModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(White, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(12.dp)
        ) {

            Row {
                Card(
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.size(35.dp).background(White)
                ) {
                    Image(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "logo",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(White)
                    )
                }

                Column(modifier = Modifier.fillMaxHeight().padding(10.dp)) {
                    BasicText(
                        style = TextStyle.Default.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        ),
                        text = "${quotesViewModel.vehicleList[0].vehicleModelYear} , ${quotesViewModel.vehicleList[0].vehicleMaker + quotesViewModel.vehicleList[0].vehicleModel}"
                    )
                    BasicText("Start Date: 04/12/2024")
                }
            }

            /*Spacer(modifier = Modifier.height(8.dp))
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(horizontal = 2.dp)
                        .fillMaxHeight(),
                    textAlign = TextAlign.Center,
                    text = "You're eligible for a 15% discount due to your claims history.",
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Normal
                )
            }*/
        }
    }

}

@Composable
fun Tabs(screenModel: QuotesViewModel, parentWidth: Dp) {
    CustomTab(
        items = screenModel.tabs,
        tabBackgroundColor = AppColors.tabBackgroundColor,
        tabWidth = (parentWidth / 2),
        selectedItemIndex = screenModel.selectedTab,
        selectedTabColor = AppColors.AppColor,
        onClick = { screenModel.selectedTab = it },
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
    selectedItemIndex: Tab,
    items: List<Tab>,
    modifier: Modifier = Modifier,
    tabWidth: Dp = 150.dp,
    selectedTabColor: Color = White,
    tabBackgroundColor: Color = Blue,
    onClick: (index: Tab) -> Unit,
) {
    val indicatorOffset: Dp by animateDpAsState(
        targetValue = tabWidth * items.indexOf(selectedItemIndex),
        animationSpec = tween(easing = LinearEasing),
    )

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(tabBackgroundColor)
            .padding(6.dp)
            .height(intrinsicSize = IntrinsicSize.Min),
    ) {
        MyTabIndicator(
            indicatorWidth = tabWidth - 12.dp,
            indicatorOffset = indicatorOffset,
            indicatorColor = selectedTabColor,
        )
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.clip(CircleShape),
        ) {
            items.forEach { tab ->
                val isSelected = quoteViewModel.selectedTab == tab
                MyTabItem(
                    isSelected = isSelected,
                    onClick = { onClick(tab) },
                    tabWidth = tabWidth,
                    text = tab.title,
                )
            }
        }
    }
}

@Composable
fun TabContent(quotes: List<ResponseTPL?>?, completed: Boolean) {
    Column(Modifier.fillMaxWidth()) {
        if (quotes == null) return
        quotes.forEach { quote ->
            if (quote != null) {
                QuoteCard(quote)
                //InsuranceCard()
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        if (!completed) {
            Spacer(modifier = Modifier.height(spaceBwFields))
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = AppColors.AppColor
            )
        }
    }
}


@Composable
fun InsuranceCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2F9FF)),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header Section: Title and Price
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        text = "Wafi Smart Motor Insurance - AI",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Text(
                    text = "SAR 4,481.91",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Subtext: Own Damage with checkmark
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "✔ Own Damage",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // View Policy Button
            Button(
                onClick = { /* Action */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007BFF)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "View Policy Details", color = White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Discounts Section
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "1 Discounts applied",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "433.03 SAR Total discount",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00B300) // Green color
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // NCD Discount Section
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "NCD Discount",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "10%   433.03 SAR",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00B300) // Green color
                )
            }
        }
    }
}


@Composable
fun QuoteCard(quote: ResponseTPL) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        val isExpended = quote.isExpended.value

        Column {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier.background(White, CircleShape),
                        elevation = CardDefaults.cardElevation(2.dp),
                        shape = CircleShape
                    ) {
                        KamelImage(
                            modifier = Modifier
                                .padding(5.dp)
                                .size(50.dp)
                                .clip(CircleShape)
                                .background(White),
                            resource = asyncPainterResource(data = quote.header.companyImage),
                            contentScale = ContentScale.Crop,
                            contentDescription = "A picture of ${quote.header.companyName}"
                        )
                    }

                    Text(
                        modifier = Modifier.weight(1f).padding(5.dp),
                        text = quote.header.companyName,
                        style = MaterialTheme.typography.titleMedium
                    )


                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            modifier = Modifier,
                            text = currency,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Normal
                        )

                        Text(
                            modifier = Modifier,
                            text = quote.products[0]?.productPrice.toString(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                }

                Spacer(modifier = Modifier.height(spaceBwFields))
                /*Text(
                    "Discount: ${quote.header.companyCode}",
                    style = MaterialTheme.typography.bodySmall
                )*/
                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    colors = ButtonColors(
                        containerColor = AppColors.AppColor,
                        contentColor = White,
                        disabledContainerColor = Color.Gray,
                        disabledContentColor = Color.Gray
                    ),
                    onClick = {
                        quoteViewModel.selectedQuote = quote
                        quoteViewModel.policyDetailsSheetVisible = true
                    },
                    modifier = Modifier.fillMaxWidth().clip(shape = RectangleShape)
                ) {
                    Text("View Policy Details")
                }

            }

            Column(
                modifier = Modifier.fillMaxWidth().background(AppColors.tabBackgroundColor)
            ) {

                Column(
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 5.dp)
                ) {
                    Row {

                        Text(
                            modifier = Modifier.weight(1f),
                            text = "Total Discount"
                        )

                        Text(
                            modifier = Modifier
                                .padding(horizontal = 2.dp)
                                .fillMaxHeight(),
                            textAlign = TextAlign.End,
                            text = "$currency ${
                                if (quote.products.isNotEmpty() && quote.products[0]!!.discount!!.isNotEmpty())
                                    getTotalDiscount(quote.products[0]!!.discount!!).toString() else "0"
                            }",
                            fontWeight = FontWeight.Normal
                        )

                        Icon(
                            if (quote.isExpended.value) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            modifier = Modifier.clickable {
                                quote.isExpended.value = !isExpended
                            }
                        )
                    }

                    if (quote.isExpended.value){
                        if (quote.products.isNotEmpty() && quote.products[0]!!.discount!!.isNotEmpty()) {
                            quote.products[0]!!.discount!!.forEach {

                                Row(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        modifier = Modifier.weight(1f),
                                        text = if (currentLanguage == "en") {
                                            it.priceNameEn ?: ""
                                        } else {
                                            it.priceNameAr ?: ""
                                        }
                                    )

                                    Text(
                                        modifier = Modifier
                                            .padding(horizontal = 2.dp)
                                            .fillMaxHeight(),
                                        textAlign = TextAlign.End,
                                        text = "$currency ${
                                            if (quote.products.isNotEmpty() && quote.products[0]!!.discount!!.isNotEmpty())
                                                getTotalDiscount(quote.products[0]!!.discount!!).toString() else "0"
                                        }",
                                        fontWeight = FontWeight.Normal
                                    )
                                }
                            }
                        }
                    }



                    /*AnimatedVisibility(
                        visible = quote.isDiscountVisible,
                        enter = androidx.compose.animation.expandVertically(animationSpec = tween(300)),
                        exit = androidx.compose.animation.shrinkVertically(animationSpec = tween(300))
                    ) {

                    }*/
                }
            }
        }
    }
}

fun getTotalDiscount(discount: List<Discount>): Double {
    var totalDiscount = 0.0

    discount.let {
        discount.forEach {
            totalDiscount += it.priceValue
        }
    }
    return totalDiscount
}
