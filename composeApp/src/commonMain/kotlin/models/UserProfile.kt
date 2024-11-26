package models

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val accountNumber: String = "",
    val bankName: String = "",
    val cityName: String = "",
    val iban: String = "",
    val mobileNumber: String,
    val nationalId: String,
    val note: String = ""
)