package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Discount(
    @SerialName("percentageValue")
    val percentageValue: Double? = null,
    @SerialName("preInvoiceDiscountId")
    val preInvoiceDiscountId: String? = null,
    @SerialName("priceNameAr")
    val priceNameAr: String? = null,
    @SerialName("priceNameEn")
    val priceNameEn: String? = null,
    @SerialName("priceTypeCode")
    val priceTypeCode: String? = null,
    @SerialName("priceValue")
    val priceValue: Double = 0.0
)