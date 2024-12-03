package presentation.screen.quotes_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun QuoteScreen(screenModel: QuotesViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {

        // Car Information Header
        CarHeader()

        //space
        Spacer(modifier = Modifier.height(16.dp))

        // Tabs
        Tabs(screenModel)

        //space
        Spacer(modifier = Modifier.height(16.dp))

        // Content based on selected tab
        when (screenModel.selectedTab) {
            Tab.ThirdParty -> TabContent(quotes)
            Tab.OwnDamage -> TabContent(quotes) // Replace with specific data
            Tab.Comprehensive -> TabContent(quotes) // Replace with specific data
        }

    }
}

@Composable
fun CarHeader() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(12.dp)
        ) {

            Row {
                Card(
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier.size(40.dp).background(Color.White)
                ) {
                    Image(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "logo",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)// Ensures the image stays circular inside the card
                    )
                }

                Column(modifier = Modifier.padding(10.dp)) {
                    BasicText("هيونداي اكستنت, 2020")
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
fun Tabs(screenModel: QuotesViewModel) {
    TabRow(selectedTabIndex = screenModel.tabs.indexOf(screenModel.selectedTab)) {
        screenModel.tabs.forEach { tab ->
            Tab(
                selected = tab == screenModel.selectedTab,
                onClick = { screenModel.selectedTab = tab },
                text = { Text(tab.title) }
            )
        }
    }
}

@Composable
fun TabContent(quotes: List<Quote>) {
    Column {
        quotes.forEach { quote ->
            QuoteCard(quote)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun QuoteCard(quote: Quote) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(quote.title, style = MaterialTheme.typography.titleMedium)
            Text(quote.price, style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            quote.details.forEach { detail ->
                Text("• $detail", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text("Discount: ${quote.discount}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().clip(shape = RectangleShape)
            ) {
                Text("View Policy Details")
            }

        }
    }
}
