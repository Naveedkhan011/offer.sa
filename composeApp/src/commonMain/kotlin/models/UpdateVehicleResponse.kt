package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateVehicleResponse(
    @SerialName("errorCode")
    val errorCode: Int? = 0,
    @SerialName("errorMessage")
    val errorMessage: String? = "",

    @SerialName("code")
    val code: String? = String(),
    @SerialName("data")
    val `data`: DataXXXX? = DataXXXX(),
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