package models

import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val errorCode: Int? = null,
    val errorDescription: String = "",
    val errorMessage: String = "",
    val errorStackTrace: String? = null,
    val lastTwoDigitMob: String = "",
    var message: String = "",
    val otpExpiryInSec: Int = 180,
    val otpType: String = "",
    val stackTraceElements: String? = null,
    val token: String? = null,
    val url: String? = "",
    val user: String? = null
    //val total: String? = ""
)