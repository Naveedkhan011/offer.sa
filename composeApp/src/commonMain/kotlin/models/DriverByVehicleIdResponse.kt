package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverByVehicleIdResponse(
    val errorCode : Int? = null,
    val errorMessage : String? = "",
    val driverList : ArrayList<DriverByVehicleIdResponseItem> = arrayListOf()
)