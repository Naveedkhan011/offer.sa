package presentation.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import navigator
import presentation.screen.languageScreen.Language
import utils.language.language_manager.LanguageManager
import utils.language.languages.ArabicStrings

@Composable
fun MoreScreen() {

    val layoutDirection =
        if (LanguageManager.currentStrings == ArabicStrings) LayoutDirection.Rtl else LayoutDirection.Ltr

    CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Settings section
            Section(title = LanguageManager.currentStrings.settings) {
                SectionItem(
                    icon = Icons.Default.Settings,
                    title = LanguageManager.currentStrings.notificationSettings,
                    function = {})
                HorizontalDivider(modifier = Modifier.padding(horizontal = 15.dp))
                SectionItem(
                    icon = Icons.Default.Place,
                    title = LanguageManager.currentStrings.changeLanguage
                ) {
                    navigator.push(Language())
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Reach Us section
            Section(title = "Reach us") {
                SectionItem(icon = Icons.Default.Check, title = "Contact Us", function = {})
                HorizontalDivider(modifier = Modifier.padding(horizontal = 15.dp))
                SectionItem(icon = Icons.Default.Check, title = "FAQs", function = {})
            }

            Spacer(modifier = Modifier.height(16.dp))

            // About section
            Section(title = "About") {
                SectionItem(icon = Icons.Default.Check, title = "Privacy And Policy", function = {})
                HorizontalDivider(modifier = Modifier.padding(horizontal = 15.dp))
                SectionItem(icon = Icons.Default.Check, title = "Terms & Conditions", function = {})
                HorizontalDivider(modifier = Modifier.padding(horizontal = 15.dp))
                SectionItem(icon = Icons.Default.Info, title = "About Offer", function = {})
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Version info
            Text(
                text = "Offer 1.0.0",
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }

}

@Composable
fun Section(title: String, content: @Composable ColumnScope.() -> Unit) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier.padding(vertical = 8.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF5F5F5)) // Light background color for each section
    ) {
        content()
    }
}

@Composable
fun SectionItem(icon: ImageVector, title: String, function: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { function() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = "Arrow",
            tint = Color.Gray,
            modifier = Modifier.size(24.dp)
        )
    }
}
