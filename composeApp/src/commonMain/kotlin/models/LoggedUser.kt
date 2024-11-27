package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoggedUser(
    val lastTwoDigitMob: String? = null,
    val errorMessage: String? = null,
    val errorCode: Int? = null,
    val message: String? = "",
    val otpExpiryInSec: String? = null,
    val otpType: String? = null,
    val token: String? = "",
    val user: User? = User()
)