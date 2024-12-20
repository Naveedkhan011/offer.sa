package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateInvoiceResponse(
    @SerialName("code")
    val code: String? = String(),
    @SerialName("data")
    val `data`: DataXXXXXX? = DataXXXXXX(),
    @SerialName("flag")
    val flag: Boolean? = false,
    @SerialName("message")
    val message: String? = String(),
    @SerialName("objectData")
    val objectData: String? = String(),
    @SerialName("success")
    val success: Boolean? = false,
    @SerialName("total")
    val total: String? = String()
)