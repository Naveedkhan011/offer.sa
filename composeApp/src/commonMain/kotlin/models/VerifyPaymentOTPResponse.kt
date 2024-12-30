package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VerifyPaymentOTPResponse(
    val errorCode: Int? = null,
    val code: Int? = null,
    val `data`: DataXXXXXXXX? = DataXXXXXXXX(),
    val flag: Boolean? = false,
    val message: String? = "",
    val objectData: String? = String(),
    val success: Boolean? = false,
    val total: String? = String()
)