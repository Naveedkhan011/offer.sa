package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class UpdateVehicleBodyDTO(
    val id: Int = 0,
    val identificationType: Int = 0,
    val vehicleOvernightParkingLocationCode: Int= 0,
    val transferOwnership: Int= 0,
    val accidentCount: String = "",
    val modification: String = "",
    val vehicleModificationDetails: String? = null,
    val km: Int= 0,
    val transmission: Int= 0,
    val approved: String = "",
    val specificationCodeIds: List<Int>? = null,
    val capacity: Int= 0,
    val vehicleRegExpiryDate: String = "",
    val vehicleMajorColorCode: String = "",
    val manufactureYear: String = "",
    val insuranceType: Int= 0,
    val deductibleValue: Int= 0,
    val vehicleAgencyRepair: Int= 0,
    val vehicleUseCode: Int= 0,
    val policy_holder_id: Int= 0,
    val vehicleValue: String = "",
    val vehicleModification: Boolean = false
)