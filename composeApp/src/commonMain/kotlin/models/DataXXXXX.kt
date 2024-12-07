package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataXXXXX(
    @SerialName("additionalBenefits")
    val additionalBenefits: Double? = null,
    @SerialName("basicPremium")
    val basicPremium: Double? = null,
    @SerialName("benefitsObject")
    val benefitsObject: List<BenefitsObject?>? = null,
    @SerialName("insuranceTypeId")
    val insuranceTypeId: Int? = null,
    @SerialName("loyaltyDiscount")
    val loyaltyDiscount: Double? = null,
    @SerialName("ncdDiscount")
    val ncdDiscount: Double? = null,
    @SerialName("occasionDiscount")
    val occasionDiscount: Double? = null,
    @SerialName("promoDiscount")
    val promoDiscount: Double? = null,
    @SerialName("quoteCreationTime")
    val quoteCreationTime: String? = null,
    @SerialName("subTotal")
    val subTotal: Double? = null,
    @SerialName("totalAmountWithVat")
    val totalAmountWithVat: Double? = null,
    @SerialName("totalVat")
    val totalVat: Double? = null
)