package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataXXXX(
    @SerialName("accidentCount")
    val accidentCount: String? = "",
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("approved")
    val approved: String? = "",
    @SerialName("bodyDescAr")
    val bodyDescAr: String? = "",
    @SerialName("bodyTypeCode")
    val bodyTypeCode: String? = "",
    @SerialName("createdBy")
    val createdBy: String? = "",
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("customCard")
    val customCard: String? = String(),
    @SerialName("deductibleValue")
    val deductibleValue: Int? = 0,
    @SerialName("dobMonth")
    val dobMonth: String? = String(),
    @SerialName("dobYear")
    val dobYear: String? = String(),
    @SerialName("drivers")
    val drivers: List<Driver>? = listOf(),
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("identificationType")
    val identificationType: Int? = 0,
    @SerialName("km")
    val km: String? = "",
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = "",
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = "",
    @SerialName("manufactureYear")
    val manufactureYear: Int? = 0,
    @SerialName("ncdFreeYears")
    val ncdFreeYears: String? = "",
    @SerialName("ncdReferenceNo")
    val ncdReferenceNo: String? = "",
    @SerialName("policyHolder")
    val policyHolder: PolicyHolderX? = PolicyHolderX(),
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
    val specificationCodeIds: List<String?>? = listOf(),
    @SerialName("specifications")
    val specifications: List<String?>? = listOf(),
    @SerialName("transferOwnership")
    val transferOwnership: Boolean? = false,
    @SerialName("transmission")
    val transmission: Int? = 0,
    @SerialName("user")
    val user: User? = User(),
    @SerialName("userId")
    val userId: Int? = 0,
    @SerialName("vehicleAgencyRepair")
    val vehicleAgencyRepair: Int? = 0,
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
    val vehicleOvernightParkingLocationCode: String? = "",
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
    val vehicleTransmissionTypeCode: String? = "",
    @SerialName("vehicleTypeCode")
    val vehicleTypeCode: Int? = 0,
    @SerialName("vehicleUseCode")
    val vehicleUseCode: Int? = 0,
    @SerialName("vehicleValue")
    val vehicleValue: Int? = 0,
    @SerialName("vehicleWeight")
    val vehicleWeight: Int? = 0,
    @SerialName("version")
    val version: Int? = 0
)