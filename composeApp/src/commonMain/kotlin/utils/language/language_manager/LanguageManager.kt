package utils.language.language_manager

import SHARED_PREFERENCE
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import utils.AppConstants
import utils.language.Strings
import utils.language.languages.ArabicStrings
import utils.language.languages.EnglishStrings

object LanguageManager {
    var currentStrings: Strings by mutableStateOf(EnglishStrings)
    var currentLanguage: String by mutableStateOf(String())

    fun switchLanguage(language: String) {
        currentLanguage = language
        currentStrings = if (language == AppConstants.LANGUAGE.ARABIC) ArabicStrings else EnglishStrings
    }
}