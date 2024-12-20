package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataXXXXXX(
    @SerialName("additionalBenefits")
    val additionalBenefits: Double? = 0.0,
    @SerialName("basicPremium")
    val basicPremium: Double? = 0.0,
    @SerialName("benefitsObject")
    val benefitsObject: List<BenefitsObjectX>? = listOf(),
    @SerialName("insuranceTypeId")
    val insuranceTypeId: Int? = 0,
    @SerialName("loyaltyDiscount")
    val loyaltyDiscount: Double? = 0.0,
    @SerialName("ncdDiscount")
    val ncdDiscount: Double? = 0.0,
    @SerialName("occasionDiscount")
    val occasionDiscount: Double? = 0.0,
    @SerialName("promoDiscount")
    val promoDiscount: Double? = 0.0,
    @SerialName("quoteCreationTime")
    val quoteCreationTime: String? = "",
    @SerialName("subTotal")
    val subTotal: Double? = 0.0,
    @SerialName("totalAmountWithVat")
    val totalAmountWithVat: Double? = 0.0,
    @SerialName("totalVat")
    val totalVat: Double? = 0.0
)