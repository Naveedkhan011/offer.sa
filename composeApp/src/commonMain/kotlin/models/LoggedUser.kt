package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoggedUser(
    @SerialName("lastTwoDigitMob")
    val lastTwoDigitMob: String? = null,
    @SerialName("errorMessage")
    val errorMessage: String? = null,
    @SerialName("errorCode")
    val errorCode: Int? = null,
    @SerialName("message")
    val message: String? = "",
    @SerialName("otpExpiryInSec")
    val otpExpiryInSec: String? = null,
    @SerialName("otpType")
    val otpType: String? = null,
    @SerialName("token")
    val token: String? = "",
    @SerialName("user")
    val user: User? = User()
)