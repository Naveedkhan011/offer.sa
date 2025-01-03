package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class showVehiclesByPolicyholderIdAndOwnerIdResponseItem(
    @SerialName("accidentCount")
    val accidentCount: String? = null,
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("approved")
    val approved: String? = null,
    @SerialName("bodyDescAr")
    val bodyDescAr: String? = "",
    @SerialName("bodyTypeCode")
    val bodyTypeCode: String? = "",
    @SerialName("createdBy")
    val createdBy: String? = null,
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("customCard")
    val customCard: String? = null,
    @SerialName("deductibleValue")
    val deductibleValue: Int? = null,
    @SerialName("dobMonth")
    val dobMonth: String? = null,
    @SerialName("dobYear")
    val dobYear: String? = null,
    @SerialName("drivers")
    val drivers: List<Drivers?>? = listOf(),
    @SerialName("id")
    val id: Int = 0,
    @SerialName("identificationType")
    val identificationType: Int? = null,
    @SerialName("km")
    val km: String? = null,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = null,
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = "",
    @SerialName("manufactureYear")
    val manufactureYear: Int? = null,
    @SerialName("ncdFreeYears")
    val ncdFreeYears: String? = "",
    @SerialName("ncdReferenceNo")
    val ncdReferenceNo: String? = "",
    @SerialName("policyHolder")
    val policyHolder: PolicyHolder? = PolicyHolder(),
    @SerialName("policyHolderId")
    val policyHolderId: Int? = 0,
    @SerialName("postCode")
    val postCode: String? = null,
    @SerialName("referenceNo")
    val referenceNo: String? = "",
    @SerialName("sellerNationalId")
    val sellerNationalId: String? = null,
    @SerialName("sequenceNumber")
    val sequenceNumber: String? = "",
    @SerialName("specification")
    val specification: Boolean? = false,
    @SerialName("specificationCodeIds")
    val specificationCodeIds: String? = null,
    @SerialName("specifications")
    val specifications: List<String?>? = listOf(),
    @SerialName("transferOwnership")
    val transferOwnership: Boolean? = false,
    @SerialName("transmission")
    val transmission: Int? = null,
    @SerialName("user")
    val user: UserX? = UserX(),
    @SerialName("userId")
    val userId: Int? = 0,
    @SerialName("vehicleAgencyRepair")
    val vehicleAgencyRepair: String? = null,
    @SerialName("vehicleAxleWeightCode")
    val vehicleAxleWeightCode: String? = null,
    @SerialName("vehicleChassisNumber")
    val vehicleChassisNumber: String? = "",
    @SerialName("vehicleClassCode")
    val vehicleClassCode: Int? = 0,
    @SerialName("vehicleClassDescAr")
    val vehicleClassDescAr: String? = "",
    @SerialName("vehicleCylinders")
    val vehicleCylinders: String? = null,
    @SerialName("vehicleEngineSizeCode")
    val vehicleEngineSizeCode: String? = null,
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
    val vehicleMileage: String? = null,
    @SerialName("vehicleMileageExpectedAnnualCode")
    val vehicleMileageExpectedAnnualCode: String? = null,
    @SerialName("vehicleModel")
    val vehicleModel: String? = "",
    @SerialName("vehicleModelCode")
    val vehicleModelCode: String? = "",
    @SerialName("vehicleModelYear")
    val vehicleModelYear: String = "",
    @SerialName("vehicleModification")
    val vehicleModification: Boolean? = false,
    @SerialName("vehicleModificationDetails")
    val vehicleModificationDetails: String? = null,
    @SerialName("vehicleOvernightParkingLocationCode")
    val vehicleOvernightParkingLocationCode: String? = null,
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
    val vehicleSeating: Int = 0,
    @SerialName("vehicleTransmissionTypeCode")
    val vehicleTransmissionTypeCode: Int? = null,
    @SerialName("vehicleTypeCode")
    val vehicleTypeCode: Int? = 0,
    @SerialName("vehicleUseCode")
    val vehicleUseCode: Int? = null,
    @SerialName("vehicleValue")
    val vehicleValue: Int? = null,
    @SerialName("vehicleWeight")
    val vehicleWeight: Int? = 0,
    @SerialName("version")
    val version: Int? = 0
)