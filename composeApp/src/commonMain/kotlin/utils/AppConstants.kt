package utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.SerializationException
import models.ErrorResponse
import navigator
import presentation.screen.login.LoginScreen
import presentation.screen.quotes_screen.json
import showError
import utils.language.language_manager.LanguageManager

class AppConstants {

    companion object {


        suspend fun onError(response: HttpResponse) {
            if (response.status.value == 401) {
                logoutAndNavigateToLoginScreen()
            } else {
                try {
                    val errorResponse =
                        json.decodeFromString(ErrorResponse.serializer(), response.bodyAsText())
                    showError(errorResponse.errorMessage)
                } catch (ex: SerializationException) {
                    showError("un Expected Error")
                }
            }
        }

        suspend fun logoutAndNavigateToLoginScreen() {
            //LogInManager.setLoggedInValue(false)
            navigator.push(LoginScreen())
            showError(LanguageManager.currentStrings.unauthenticated)
        }


        @Composable
        fun getOutlineTextFieldColors(): TextFieldColors {
            return OutlinedTextFieldDefaults.colors(
                focusedLabelColor = AppColors.AppColor,
                focusedBorderColor = AppColors.AppColor,
                unfocusedBorderColor = AppColors.AppColor,
                errorBorderColor = AppColors.Red,
                errorLabelColor = AppColors.Red
            )
        }

        @Composable
        fun getCheckBoxColors(): CheckboxColors {
            return CheckboxDefaults.colors(
                checkedColor = AppColors.AppColor,
                uncheckedColor = AppColors.AppColor,
                checkmarkColor = Color.White
            )
        }

        @Composable
        fun getButtonColors(): ButtonColors {
            return ButtonDefaults.buttonColors(
                containerColor = AppColors.AppColor,
                contentColor = AppColors.textColorOnAppColorBackground,
                disabledContainerColor = Color.Gray,
                disabledContentColor = Color.LightGray
            )
        }

        @Composable
        fun CustomButton(
            buttonColors: ButtonColors = getButtonColors(),
            onClick: () -> Unit,
            modifier: Modifier = Modifier,
            text: String = "",
            enable: Boolean = true
        ) {

            Button(
                modifier = modifier.fillMaxWidth(),
                onClick = onClick,
                colors = buttonColors,
                enabled = enable
            ) {
                Text(text = text)
            }
        }
    }

    interface LANGUAGE {
        companion object {
            const val ENGLISH = "en"
            const val ARABIC = "ar"
        }
    }

    interface SharedPreferenceKeys {
        companion object {
            const val LANGUAGE = "LANGUAGE"
            const val LOGGED_IN_USER = "LOGGED_IN_USER"
            const val LOGGED_IN = "LOGGED_IN"
            const val IS_ALREADY_VIEWED = "isAlreadyViewed"
        }
    }


}