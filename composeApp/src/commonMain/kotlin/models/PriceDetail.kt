package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PriceDetail(
    @SerialName("percentageValue")
    val percentageValue: Double? = null,
    @SerialName("preInvoicePriceDetailsId")
    val preInvoicePriceDetailsId: String? = null,
    @SerialName("priceNameAr")
    val priceNameAr: String? = null,
    @SerialName("priceNameEn")
    val priceNameEn: String? = null,
    @SerialName("priceTypeCode")
    val priceTypeCode: String? = null,
    @SerialName("priceValue")
    val priceValue: Double? = null
)