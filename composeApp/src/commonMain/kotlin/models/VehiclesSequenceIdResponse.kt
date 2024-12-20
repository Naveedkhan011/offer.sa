package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VehiclesSequenceIdResponse(
    @SerialName("coOwnerId")
    val coOwnerId: String? = String(),
    @SerialName("customNo")
    val customNo: String? = String(),
    @SerialName("modelYear")
    val modelYear: String? = String(),
    @SerialName("ownerId")
    val ownerId: String? = "",
    @SerialName("sequenceNo")
    val sequenceNo: String? = "",
    @SerialName("vehicleInfo")
    val vehicleInfo: VehicleInfo = VehicleInfo(),
    @SerialName("vehicleList")
    val vehicleList: VehicleListX? = VehicleListX()
)