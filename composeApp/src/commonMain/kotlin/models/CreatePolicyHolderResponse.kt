package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePolicyHolderResponse(
    @SerialName("code")
    val code: String? = "",
    @SerialName("data")
    val data: DataXX = DataXX(),
    @SerialName("flag")
    val flag: Boolean? = false,
    @SerialName("message")
    val message: String? = "",
    @SerialName("success")
    val success: Boolean = false,
    @SerialName("total")
    val total: String? = String(),
    @SerialName("errorCode")
    val errorCode: Int? = null,
    @SerialName("errorMessage")
    val errorMessage: String? = String()
)