package presentation.screen.forgot_password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.ktor.client.call.body
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.launch
import models.GeneralResponse
import network.Ktor
import utils.language.language_manager.LanguageManager

class ForgotPasswordViewModel : ScreenModel {

    var email by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)
    var verifyingEmail by mutableStateOf(false)
    var buttonEnabled by mutableStateOf(false)
    var responseMessage by mutableStateOf("")
    var success by mutableStateOf(false)


    // email verification section
    fun isValidEmail(): Boolean {
        var isValid = true
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"

        emailError = if (email.isBlank()) {
            isValid = false
            LanguageManager.currentStrings.emailIsRequired
        } else if (!email.matches(emailRegex.toRegex())) {
            isValid = false
            "Invalid email format"
        } else null

        buttonEnabled = isValid

        return isValid
    }


    fun sendTemporaryPassword() {
        if (isValidEmail()) {
            screenModelScope.launch {
                verifyingEmail = true
                try {
                    val endPoints = "/insurance/rest/sendTempPass"
                    val response = Ktor.client.post(endPoints) {
                        setBody(mapOf("email" to email))
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<GeneralResponse>()

                    if (response.success) {
                        responseMessage = response.message!!
                        success = true
                    } else {
                        emailError = response.message!!
                        responseMessage = response.message
                    }

                } catch (e: Exception) {
                    emailError = LanguageManager.currentStrings.emailNotAvailable
                } finally {
                    verifyingEmail = false
                }
            }
        }
    }

    fun reset() {
        email = ""
        emailError = null
        verifyingEmail = false
        buttonEnabled = false
        responseMessage = ""
        success = false
    }

}