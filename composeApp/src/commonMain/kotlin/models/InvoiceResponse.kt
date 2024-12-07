package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InvoiceResponse(
    @SerialName("errorCode")
    val errorCode: Int? =  null,
    @SerialName("errorMessage")
    val errorMessage: String? = "",

    @SerialName("code")
    val code: String? = String(),
    @SerialName("data")
    val `data`: DataXXXXX? = DataXXXXX(),
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