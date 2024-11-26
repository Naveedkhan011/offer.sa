package presentation.screen.languageScreen

import SHARED_PREFERENCE
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import navigator
import utils.AppColors
import utils.AppConstants
import utils.language.language_manager.LanguageManager
import utils.language.languages.ArabicStrings

class Language : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val language = SHARED_PREFERENCE.getString(AppConstants.SharedPreferenceKeys.LANGUAGE)

        var selectedOption by remember { mutableStateOf(if (language == AppConstants.LANGUAGE.ARABIC) 1 else 2) }
        val layoutDirection =
            if (LanguageManager.currentStrings == ArabicStrings) LayoutDirection.Rtl else LayoutDirection.Ltr

        CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {

            Scaffold(topBar = {
                CenterAlignedTopAppBar(title = { Text(text = LanguageManager.currentStrings.changeLanguage) },
                    navigationIcon = {
                        IconButton(onClick = {
                            navigator.pop()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                contentDescription = "Menu"
                            )
                        }
                    })
            }) { padding ->

                Box(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                                .clickable(onClick = { selectedOption = 1 })
                                .padding(8.dp, padding.calculateTopPadding(), 8.dp, 2.dp)
                        ) {
                            RadioButton(
                                selected = selectedOption == 1,
                                onClick = { selectedOption = 1 })
                            Text(
                                text = "العربية", modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(onClick = { selectedOption = 2 })
                                .padding(8.dp, 8.dp, 8.dp, 2.dp)
                        ) {
                            RadioButton(
                                selected = selectedOption == 2,
                                onClick = { selectedOption = 2 })
                            Text(
                                text = "English", modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }

                    Button(
                        shape = androidx.compose.foundation.shape.CircleShape,
                        colors = ButtonColors(
                            containerColor = AppColors.AppColor,
                            contentColor = Color.White,
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        ),
                        onClick = {
                            val selectedLanguage = if (selectedOption == 1) {
                                AppConstants.LANGUAGE.ARABIC
                            } else
                                AppConstants.LANGUAGE.ENGLISH

                            SHARED_PREFERENCE.put(
                                AppConstants.SharedPreferenceKeys.LANGUAGE,
                                selectedLanguage
                            )
                            LanguageManager.switchLanguage(selectedLanguage)

                            navigator.pop()
                        },
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 20.dp, horizontal = 20.dp)
                            .clickable {}
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(
                            text = LanguageManager.currentStrings.changeLanguage, style = TextStyle(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                }
            }
        }
    }
}