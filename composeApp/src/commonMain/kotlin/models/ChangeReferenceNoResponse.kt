package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangeReferenceNoResponse(
    @SerialName("code")
    val code: String? = "",
    @SerialName("data")
    val `data`: DataXXXXXXX? = DataXXXXXXX(),
    @SerialName("flag")
    val flag: Boolean? = false,
    @SerialName("message")
    val message: String = "",
    @SerialName("objectData")
    val objectData: String? = String(),
    @SerialName("success")
    val success: Boolean = false,
    @SerialName("total")
    val total: String? = String()
)