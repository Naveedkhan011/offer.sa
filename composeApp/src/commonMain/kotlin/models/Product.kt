package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("benefits")
    val benefits: List<Benefit?>? = null,
    @SerialName("deductibleValue")
    val deductibleValue: String? = null,
    @SerialName("discount")
    val discount: List<Discount?>? = null,
    @SerialName("priceDetails")
    val priceDetails: List<PriceDetail?>? = null,
    @SerialName("productDescAr")
    val productDescAr: String? = null,
    @SerialName("productDescEn")
    val productDescEn: String? = null,
    @SerialName("productId")
    val productId: String? = null,
    @SerialName("productNameAr")
    val productNameAr: String? = null,
    @SerialName("productNameEn")
    val productNameEn: String? = null,
    @SerialName("productPrice")
    val productPrice: Double? = null,
    @SerialName("proposalForms")
    val proposalForms: String? = null,
    @SerialName("vehicleLimitValue")
    val vehicleLimitValue: String? = null
)