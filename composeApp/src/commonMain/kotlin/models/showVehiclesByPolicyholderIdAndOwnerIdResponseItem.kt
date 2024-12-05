package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class showVehiclesByPolicyholderIdAndOwnerIdResponseItem(
    @SerialName("accidentCount")
    val accidentCount: String? = String(),
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("approved")
    val approved: String? = String(),
    @SerialName("bodyDescAr")
    val bodyDescAr: String? = "",
    @SerialName("bodyTypeCode")
    val bodyTypeCode: String? = "",
    @SerialName("createdBy")
    val createdBy: String? = String(),
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("customCard")
    val customCard: String? = String(),
    @SerialName("deductibleValue")
    val deductibleValue: String? = String(),
    @SerialName("dobMonth")
    val dobMonth: String? = String(),
    @SerialName("dobYear")
    val dobYear: String? = String(),
    @SerialName("drivers")
    val drivers: List<String?>? = listOf(),
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("identificationType")
    val identificationType: String? = String(),
    @SerialName("km")
    val km: String? = String(),
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = String(),
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = "",
    @SerialName("manufactureYear")
    val manufactureYear: String? = String(),
    @SerialName("ncdFreeYears")
    val ncdFreeYears: String? = "",
    @SerialName("ncdReferenceNo")
    val ncdReferenceNo: String? = "",
    @SerialName("policyHolder")
    val policyHolder: PolicyHolder? = PolicyHolder(),
    @SerialName("policyHolderId")
    val policyHolderId: Int? = 0,
    @SerialName("postCode")
    val postCode: String? = String(),
    @SerialName("referenceNo")
    val referenceNo: String? = "",
    @SerialName("sellerNationalId")
    val sellerNationalId: String? = String(),
    @SerialName("sequenceNumber")
    val sequenceNumber: String? = "",
    @SerialName("specification")
    val specification: Boolean? = false,
    @SerialName("specificationCodeIds")
    val specificationCodeIds: String? = String(),
    @SerialName("specifications")
    val specifications: List<String?>? = listOf(),
    @SerialName("transferOwnership")
    val transferOwnership: Boolean? = false,
    @SerialName("transmission")
    val transmission: String? = String(),
    @SerialName("user")
    val user: UserX? = UserX(),
    @SerialName("userId")
    val userId: Int? = 0,
    @SerialName("vehicleAgencyRepair")
    val vehicleAgencyRepair: String? = String(),
    @SerialName("vehicleAxleWeightCode")
    val vehicleAxleWeightCode: String? = String(),
    @SerialName("vehicleChassisNumber")
    val vehicleChassisNumber: String? = "",
    @SerialName("vehicleClassCode")
    val vehicleClassCode: Int? = 0,
    @SerialName("vehicleClassDescAr")
    val vehicleClassDescAr: String? = "",
    @SerialName("vehicleCylinders")
    val vehicleCylinders: String? = String(),
    @SerialName("vehicleEngineSizeCode")
    val vehicleEngineSizeCode: String? = String(),
    @SerialName("vehicleId")
    val vehicleId: Int? = 0,
    @SerialName("vehicleMajorColor")
    val vehicleMajorColor: String? = "",
    @SerialName("vehicleMajorColorCode")
    val vehicleMajorColorCode: String? = "",
    @SerialName("vehicleMaker")
    val vehicleMaker: String? = "",
    @SerialName("vehicleMakerCode")
    val vehicleMakerCode: String? = "",
    @SerialName("vehicleMileage")
    val vehicleMileage: String? = String(),
    @SerialName("vehicleMileageExpectedAnnualCode")
    val vehicleMileageExpectedAnnualCode: String? = String(),
    @SerialName("vehicleModel")
    val vehicleModel: String? = "",
    @SerialName("vehicleModelCode")
    val vehicleModelCode: String? = "",
    @SerialName("vehicleModelYear")
    val vehicleModelYear: String? = "",
    @SerialName("vehicleModification")
    val vehicleModification: Boolean? = false,
    @SerialName("vehicleModificationDetails")
    val vehicleModificationDetails: String? = String(),
    @SerialName("vehicleOvernightParkingLocationCode")
    val vehicleOvernightParkingLocationCode: String? = String(),
    @SerialName("vehicleOwnerId")
    val vehicleOwnerId: Long? = 0,
    @SerialName("vehicleOwnerName")
    val vehicleOwnerName: String? = "",
    @SerialName("vehicleOwnerTransfer")
    val vehicleOwnerTransfer: Boolean? = false,
    @SerialName("vehiclePlateNumber")
    val vehiclePlateNumber: Int? = 0,
    @SerialName("vehiclePlateText1")
    val vehiclePlateText1: String? = "",
    @SerialName("vehiclePlateText2")
    val vehiclePlateText2: String? = "",
    @SerialName("vehiclePlateText3")
    val vehiclePlateText3: String? = "",
    @SerialName("vehiclePlateTypeCode")
    val vehiclePlateTypeCode: String? = "",
    @SerialName("vehiclePlateTypeId")
    val vehiclePlateTypeId: String? = "",
    @SerialName("vehicleRegCityCode")
    val vehicleRegCityCode: String? = "",
    @SerialName("vehicleRegExpiryDate")
    val vehicleRegExpiryDate: String? = "",
    @SerialName("vehicleRegPlace")
    val vehicleRegPlace: String? = "",
    @SerialName("vehicleSeating")
    val vehicleSeating: Int? = 0,
    @SerialName("vehicleTransmissionTypeCode")
    val vehicleTransmissionTypeCode: String? = String(),
    @SerialName("vehicleTypeCode")
    val vehicleTypeCode: Int? = 0,
    @SerialName("vehicleUseCode")
    val vehicleUseCode: String? = String(),
    @SerialName("vehicleValue")
    val vehicleValue: String? = String(),
    @SerialName("vehicleWeight")
    val vehicleWeight: Int? = 0,
    @SerialName("version")
    val version: Int? = 0
)