package models.body_models

import kotlinx.serialization.Serializable

@Serializable
data class DriverLicense(
    val licenseCountryCode: Int = 0,
    val licenseNumberYears: String = ""
)