package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Header(
    @SerialName("companyCode")
    val companyCode: Int = 0,
    @SerialName("companyImage")
    val companyImage: String = "",
    @SerialName("companyName")
    val companyName: String = "",
    @SerialName("companyNameAr")
    val companyNameAr: String? = null,
    @SerialName("termsAndConditions")
    val termsAndConditions: String? = null,
    @SerialName("termsAndConditionsAr")
    val termsAndConditionsAr: String? = null
)