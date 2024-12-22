package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaymentOTPResponse(
    @SerialName("success")
    val success: Boolean? = null,
    @SerialName("desc")
    val desc: String? = null,
    @SerialName("lastTwoDigitMob")
    val lastTwoDigitMob: String? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("otpTimeRemaining")
    val otpTimeRemaining: Int = 180,
    @SerialName("errorCode")
    val errorCode: Int? = null,
    @SerialName("errorMessage")
    val errorMessage: String? = null
)