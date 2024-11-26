package utils.language.language_manager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import utils.AppConstants
import utils.language.Strings
import utils.language.languages.ArabicStrings
import utils.language.languages.EnglishStrings

object LanguageManager {
    var currentStrings: Strings by mutableStateOf(EnglishStrings)

    fun switchLanguage(language: String) {
        currentStrings = if (language == AppConstants.LANGUAGE.ARABIC) ArabicStrings else EnglishStrings
    }
}