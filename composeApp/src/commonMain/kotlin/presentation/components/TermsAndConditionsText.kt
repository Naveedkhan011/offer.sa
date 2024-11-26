package presentation.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import utils.AppColors
import utils.language.language_manager.LanguageManager

@Composable
fun TermsAndConditionsText(onTermsClick: () -> Unit, onPrivacyClick: () -> Unit) {
    val annotatedText = buildAnnotatedString {
        append(LanguageManager.currentStrings.iAgreeWith)

        // Add clickable text for "Terms and Conditions"
        pushStringAnnotation(tag = "TERMS", annotation = "terms")
        withStyle(style = SpanStyle(color = AppColors.AppColor, textDecoration = TextDecoration.Underline)) {
            append(LanguageManager.currentStrings.termsAndConditions)
        }
        pop()

        append( LanguageManager.currentStrings.andText)

        // Add clickable text for "Privacy Policy"
        pushStringAnnotation(tag = "PRIVACY", annotation = "privacy")
        withStyle(style = SpanStyle(color = AppColors.AppColor, textDecoration = TextDecoration.Underline)) {
            append(LanguageManager.currentStrings.privacyPolicy)
        }
        pop()
    }

    ClickableText(
        text = annotatedText,
        style = MaterialTheme.typography.bodySmall.copy(color = AppColors.textColor), // Customize as needed
        onClick = { offset ->
            annotatedText.getStringAnnotations(tag = "TERMS", start = offset, end = offset)
                .firstOrNull()?.let {
                    onTermsClick() // Handle "Terms and Conditions" click
                }
            annotatedText.getStringAnnotations(tag = "PRIVACY", start = offset, end = offset)
                .firstOrNull()?.let {
                    onPrivacyClick() // Handle "Privacy Policy" click
                }
        }
    )
}

fun pushStringAnnotation(tag: String, annotation: String) {

}
