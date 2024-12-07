package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class updateVehicleBody(

    @SerialName("accidentCount")
    var accidentCount: String = "",

    @SerialName("approved")
    var approved: String = "",

    @SerialName("capacity")
    var capacity: Int = 0,

    @SerialName("deductibleValue")
    var deductibleValue: Int = 0,

    @SerialName("id")
    var id: Int = 0,

    @SerialName("identificationType")
    var identificationType: Int? = null,

    @SerialName("insuranceType")
    var insuranceType: Int = 0,

    @Transient
    var expectedKMTitle: String = "",
    @SerialName("km")
    var km: Int = 0,

    @SerialName("manufactureYear")
    var manufactureYear: String = "",

    @SerialName("modification")
    var modification: String = "",

    @SerialName("policy_holder_id")
    var policyHolderId: Int = 0,

    @SerialName("specificationCodeIds")
    var specificationCodeIds: ArrayList<Int> = arrayListOf(),

    @SerialName("transferOwnership")
    var transferOwnership: Int = 0,

    @Transient
    var transmissionTitle: String = "",
    @SerialName("transmission")
    var transmission: Int = 0,

    @SerialName("vehicleAgencyRepair")
    var vehicleAgencyRepair: Int = 0,

    @SerialName("vehicleMajorColorCode")
    var vehicleMajorColorCode: String = "",

    @Transient
    var modificationTypeTitle: String = "",
    @SerialName("vehicleModification")
    var vehicleModification: Boolean = false,

    @SerialName("vehicleModificationDetails")
    var vehicleModificationDetails: String = "",

    @Transient
    var vehicleOvernightParkingLocationTitle: String = "",
    @SerialName("vehicleOvernightParkingLocationCode")
    var vehicleOvernightParkingLocationCode: Int = 0,

    @SerialName("vehicleRegExpiryDate")
    var vehicleRegExpiryDate: String = "",

/*    @Transient
    var vehicleUseTitle: String = "",*/
    @SerialName("vehicleUseCode")
    var vehicleUseCode: Int = 0,

    @SerialName("vehicleValue")
    var vehicleValue: String = ""
)