package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Header(
    @SerialName("companyCode")
    val companyCode: String? = null,
    @SerialName("companyImage")
    val companyImage: String? = null,
    @SerialName("companyName")
    val companyName: String? = null,
    @SerialName("companyNameAr")
    val companyNameAr: String? = null,
    @SerialName("termsAndConditions")
    val termsAndConditions: String? = null,
    @SerialName("termsAndConditionsAr")
    val termsAndConditionsAr: String? = null
)