package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VehicleListX(
    @SerialName("personBasicInfo")
    val personBasicInfo: String? = null
)