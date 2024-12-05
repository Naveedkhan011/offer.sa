package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class updateVehicleBody(

    @SerialName("errorCode")
    val errorCode: Int? = null,
    @SerialName("errorMessage")
    val errorMessage: String? = null,

    @SerialName("accidentCount")
    var accidentCount: String = "",
    @SerialName("approved")
    val approved: String = "",
    @SerialName("capacity")
    val capacity: Int = 0,
    @SerialName("deductibleValue")
    val deductibleValue: Int = 0,
    @SerialName("id")
    val id: Int = 0,
    @SerialName("identificationType")
    val identificationType: Int = 0,
    @SerialName("insuranceType")
    val insuranceType: Int = 0,
    @SerialName("km")
    val km: Int = 0,
    @SerialName("manufactureYear")
    val manufactureYear: String = "",
    @SerialName("modification")
    val modification: String = "",
    @SerialName("policy_holder_id")
    val policyHolderId: Int = 0,
    @SerialName("specificationCodeIds")
    val specificationCodeIds: List<Int> = listOf(),
    @SerialName("transferOwnership")
    val transferOwnership: Int = 0,
    @SerialName("transmission")
    val transmission: Int = 0,
    @SerialName("vehicleAgencyRepair")
    val vehicleAgencyRepair: Int = 0,
    @SerialName("vehicleMajorColorCode")
    val vehicleMajorColorCode: String = "",
    @SerialName("vehicleModification")
    val vehicleModification: Boolean = false,
    @SerialName("vehicleModificationDetails")
    val vehicleModificationDetails: String? = null,
    @SerialName("vehicleOvernightParkingLocationCode")
    val vehicleOvernightParkingLocationCode: Int = 0,
    @SerialName("vehicleRegExpiryDate")
    val vehicleRegExpiryDate: String = "",
    @SerialName("vehicleUseCode")
    val vehicleUseCode: Int = 0,
    @SerialName("vehicleValue")
    val vehicleValue: String = ""
)