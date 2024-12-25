package presentation.screen.signup_screen

import SHARED_PREFERENCE
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
import models.NationalIdResponse
import models.SignUpRequest
import models.UserProfileX
import models.enums.SignUpStates
import network.Ktor
import utils.AppConstants
import utils.language.language_manager.LanguageManager

class SignupViewModel : ScreenModel {
    var signUpStates by mutableStateOf(SignUpStates.EMAIL_VALIDATION)

    var email by mutableStateOf("")
    var verifyingEmail by mutableStateOf(false)
    var emailOTPVerification by mutableStateOf("")

    var nationalId by mutableStateOf("")
    var verifyingNationalID by mutableStateOf(false)
    var nationalIdOTP by mutableStateOf("")

    var mobileNumber by mutableStateOf("")
    var verifyingMobile by mutableStateOf(false)
    var mobileOtp by mutableStateOf("")

    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    var isTermsChecked by mutableStateOf(false)
    var buttonEnabled by mutableStateOf(false)

    var emailError by mutableStateOf<String?>(null)
    var emailOTPError by mutableStateOf<String?>(null)

    var nationalIdError by mutableStateOf<String?>(null)
    var nationalIdOTPError by mutableStateOf<String?>(null)

    var mobileNumberError by mutableStateOf<String?>(null)
    var mobileOtpError by mutableStateOf<String?>(null)

    var passwordError by mutableStateOf<String?>(null)
    var confirmPasswordError by mutableStateOf<String?>(null)


    // email verification section
    private fun isValidEmail(): Boolean {
        var isValid = true
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"

        emailError = if (email.isBlank()) {
            isValid = false
            LanguageManager.currentStrings.emailIsRequired
        } else if (!email.matches(emailRegex.toRegex())) {
            isValid = false
            "Invalid email format"
        } else null

        return isValid
    }

    fun emailAvailable() {
        if (isValidEmail()) {
            screenModelScope.launch {
                verifyingEmail = true
                try {
                    val endPoints = "/insurance/rest/checkAvailability"
                    val response = Ktor.client.post(endPoints) {
                        setBody(mapOf("email" to email))
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<GeneralResponse>()

                    buttonEnabled = response.data?.emailAvailable == false

                    if (response.data?.emailAvailable == true) {
                        emailError = response.message
                    }

                } catch (e: Exception) {
                    emailError = e.message
                } finally {
                    verifyingEmail = false
                }
            }
        } else
            buttonEnabled = false
    }

    fun sendOtpToEmail() {
        screenModelScope.launch {
            screenModelScope.launch {
                try {
                    val endPoints = "/insurance/rest/sendVerificationCodeAny"
                    val response = Ktor.client.post(endPoints) {
                        setBody(mapOf("email" to email))
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<GeneralResponse>()

                    if (response.success) {
                        signUpStates = SignUpStates.EMAIL_OTP_VALIDATION
                    } else
                        emailOTPError = response.message
                } catch (e: Exception) {
                    emailOTPError = e.message
                }
            }
        }
    }

    fun verifyEmailOTP(otp: String) {
        screenModelScope.launch {
            screenModelScope.launch {
                try {
                    val endPoints = "/insurance/rest/verifyEmailCodeAny"
                    val response = Ktor.client.post(endPoints) {
                        setBody(mapOf("email" to email, "code" to otp))
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<GeneralResponse>()

                    buttonEnabled = !response.success

                    if (response.success) {
                        signUpStates = SignUpStates.IQAMA_VALIDATION
                    } else
                        emailOTPError = response.message

                } catch (e: Exception) {
                    emailOTPError = e.message
                }
            }
        }
    }


    //iqama verification section
    private fun isValidIqama(): Boolean {
        var isValid = true

        nationalIdError = if (nationalId.isBlank()) {
            isValid = false
            "National ID is required"
        } else null
        return isValid
    }

    fun verifyIqama() {
        if (isValidIqama()) {
            screenModelScope.launch {
                verifyingNationalID = true
                try {
                    val endPoints = "/insurance/rest/checkAvailability"
                    val response = Ktor.client.post(endPoints) {
                        setBody(mapOf("nationalId" to nationalId))
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<GeneralResponse>()

                    buttonEnabled = response.data?.nationalIdAvailable == false

                    if (response.data?.nationalIdAvailable == true) {
                        nationalIdError = LanguageManager.currentStrings.noInfoFound
                    }

                } catch (e: Exception) {
                    nationalIdError = e.message
                } finally {
                    verifyingNationalID = false
                }
            }
        } else
            buttonEnabled = false
    }

    fun sendNationalIdOtp() {
        if (isValidIqama()) {
            screenModelScope.launch {
                try {
                    val endPoints = "/insurance/rest/sendNationalIdOtp"
                    val response = Ktor.client.post(endPoints) {
                        setBody(
                            mapOf(
                                "email" to email,
                                "nationalId" to nationalId,
                                "lang" to (SHARED_PREFERENCE.getString(AppConstants.SharedPreferenceKeys.LANGUAGE)
                                    ?.uppercase() ?: "EN")
                            )
                        )
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<NationalIdResponse>()

                    buttonEnabled = response.success

                    if (response.success) {
                        signUpStates = SignUpStates.IQAMA_OTP_VALIDATION
                    } else
                        nationalIdError = response.message

                } catch (e: Exception) {
                    nationalIdError = e.message
                }
            }
        } else
            buttonEnabled = false
    }

    fun verifyNationalIdByOtp(email: String, otp: String) {
        if (isValidIqama()) {
            screenModelScope.launch {
                try {
                    val endPoints = "/insurance/rest/verifyNationalIdByOtp"
                    val response = Ktor.client.post(endPoints) {
                        setBody(
                            mapOf(
                                "email" to email,
                                "otp" to otp
                            )
                        )
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<NationalIdResponse>()

                    buttonEnabled = !response.success

                    //signUpStates = SignUpStates.MOBILE_VALIDATION

                    if (response.success) {
                        signUpStates = SignUpStates.MOBILE_VALIDATION
                    } else
                        nationalIdOTPError = response.message
                    //nationalIdOTPError = LanguageManager.currentStrings.emailNotAvailable


                } catch (e: Exception) {
                    nationalIdOTPError = e.message
                }
            }
        } else
            buttonEnabled = false
    }


    //mobile verification section
    private fun isValidMobile(): Boolean {
        var isValid = true

        mobileNumberError = if (mobileNumber.isBlank()) {
            isValid = false
            "Mobile number is required"
        } else if (mobileNumber.length != 9) { // Assuming a 9-digit mobile number
            isValid = false
            "Invalid mobile number"
        } else null

        return isValid
    }

    fun checkMobileAvailability(mobileNumber: String) {
        if (isValidMobile()) {
            screenModelScope.launch {
                verifyingMobile = true
                try {
                    val endPoints = "/insurance/rest/checkAvailability"
                    val response = Ktor.client.post(endPoints) {
                        setBody(
                            mapOf(
                                "mobile" to mobileNumber
                            )
                        )
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<GeneralResponse>()

                    buttonEnabled = (response.data?.mobileAvailable ?: false) == false

                    if (response.data?.mobileAvailable == true) {
                        mobileNumberError = LanguageManager.currentStrings.mobileNumberNotAvailable
                    }

                } catch (e: Exception) {
                    mobileNumberError = e.message
                } finally {
                    verifyingMobile = false
                }
            }
        } else
            buttonEnabled = false
    }

    fun sendVerificationCodeSMS(email: String, mobileNumber: String) {
        if (isValidMobile()) {
            screenModelScope.launch {
                try {
                    val endPoints = "/insurance/rest/sendVerificationCodeSMS"
                    val response = Ktor.client.post(endPoints) {
                        setBody(
                            mapOf(
                                "email" to email,
                                "mobile" to mobileNumber
                            )
                        )
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<GeneralResponse>()

                    if (response.success) {
                        signUpStates = SignUpStates.MOBILE_OTP_VALIDATION
                    } else
                        mobileNumberError = response.message

                } catch (e: Exception) {
                    mobileNumberError = e.message
                }
            }
        } else
            buttonEnabled = false
    }

    fun verifySMSCodeAny(email: String, otp: String) {
        if (isValidMobile()) {
            screenModelScope.launch {
                try {
                    val endPoints = "/insurance/rest/verifySMSCodeAny"
                    val response = Ktor.client.post(endPoints) {
                        setBody(
                            mapOf(
                                "email" to email,
                                "otp" to otp
                            )
                        )
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<GeneralResponse>()

                    if (response.success) {
                        signUpStates = SignUpStates.PASSWORD_VALIDATION
                    } else
                        mobileOtpError = response.message

                } catch (e: Exception) {
                    mobileOtpError = e.message
                }
            }
        } else
            buttonEnabled = false

    }

    // sign up section
    private fun validateForm(): Boolean {
        var isValid = true

        emailError = if (email.isBlank()) {
            isValid = false
            LanguageManager.currentStrings.emailIsRequired
        }/* else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isValid = false
            "Invalid email format"
        }*/ else null

        nationalIdError = if (nationalId.isBlank()) {
            isValid = false
            "National ID is required"
        } else null

        mobileNumberError = if (mobileNumber.isBlank()) {
            isValid = false
            "Mobile number is required"
        } else if (mobileNumber.length != 9) { // Assuming a 9-digit mobile number
            isValid = false
            "Invalid mobile number"
        } else null

        passwordError = if (password.isBlank()) {
            isValid = false
            LanguageManager.currentStrings.passwordIsRequired
        } else if (password.length < 8) {
            isValid = false
            LanguageManager.currentStrings.passwordValidationMessage
        } else null

        confirmPasswordError = if (confirmPassword != password) {
            isValid = false
            "Passwords do not match"
        } else null

        return isValid
    }

    fun onSignup() {
        if (validateForm()) {
            screenModelScope.launch {
                try {
                    val endPoints = "/insurance/rest/signup"
                    val response = Ktor.client.post(endPoints) {
                        setBody(
                            SignUpRequest(
                                email = email,
                                password = password,
                                userName = email,
                                userProfile = UserProfileX(
                                    mobileNumber = mobileNumber,
                                    nationalId = nationalId
                                ),
                            )
                        )
                        contentType(ContentType.Application.Json)
                        accept(ContentType.Application.Json)
                    }.body<GeneralResponse>()

                    if (response.success) {
                        signUpStates = SignUpStates.SIGN_UP_SUCCESSFULLY
                    } else
                        confirmPasswordError = response.message
                } catch (e: Exception) {
                    confirmPasswordError = e.message
                }
            }
        }
    }
}