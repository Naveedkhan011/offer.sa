package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddLicense(
    @SerialName("licenseCountryCode")
    val licenseCountryCode: Int? = null,
    @SerialName("licenseNumberYears")
    val licenseNumberYears: String? = null
)