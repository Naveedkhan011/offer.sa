package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VehicleList(
    @SerialName("personBasicInfo")
    val personBasicInfo: PersonBasicInfo? = null
)