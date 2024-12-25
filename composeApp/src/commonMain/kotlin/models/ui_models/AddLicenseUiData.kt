package models.ui_models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import models.InsuranceTypeCodeModel

@Serializable
data class AddLicenseUiData(
    @SerialName("licenseCountryCode")
    val licenseCountryCode: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    @SerialName("licenseNumberYears")
    val licenseNumberYears: String = ""
)