package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyPaymentOTPResponse(
    @SerialName("errorCode")
    val errorCode: Int? = null,
    @SerialName("code")
    val code: Int? = null,
    @SerialName("data")
    val `data`: DataXXXXXXXX? = DataXXXXXXXX(),
    @SerialName("flag")
    val flag: Boolean? = false,
    @SerialName("message")
    val message: String? = "",
    @SerialName("objectData")
    val objectData: String? = String(),
    @SerialName("success")
    val success: Boolean? = false,
    @SerialName("total")
    val total: String? = String()
)