package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordResponse(
    @SerialName("errorCode")
    val errorCode: Int? = null,
    @SerialName("errorDescription")
    val errorDescription: String? = null,
    @SerialName("errorMessage")
    val errorMessage: String? = null,
    @SerialName("flag")
    val flag: Boolean? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("success")
    val success: Boolean? = null,
    @SerialName("url")
    val url: String? = null
)