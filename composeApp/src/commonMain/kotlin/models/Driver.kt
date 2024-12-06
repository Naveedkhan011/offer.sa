package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    @SerialName("accidentCount")
    val accidentCount: String? = String(),
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("additionalDriver")
    val additionalDriver: Boolean? = false,
    @SerialName("childrenBelow16")
    val childrenBelow16: String? = "",
    @SerialName("childrenBelowSixteen")
    val childrenBelowSixteen: String? = String(),
    @SerialName("createdBy")
    val createdBy: String? = "",
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("dobMonth")
    val dobMonth: String? = "",
    @SerialName("dobYear")
    val dobYear: String? = "",
    @SerialName("drifting")
    val drifting: Boolean? = false,
    @SerialName("driverAddressCity")
    val driverAddressCity: String? = "",
    @SerialName("driverAddressCityCode")
    val driverAddressCityCode: String? = String(),
    @SerialName("driverBirthDateG")
    val driverBirthDateG: String? = "",
    @SerialName("driverBirthDateH")
    val driverBirthDateH: String? = "",
    @SerialName("driverBusinessCity")
    val driverBusinessCity: String? = "",
    @SerialName("driverBusinessCityCode")
    val driverBusinessCityCode: String? = String(),
    @SerialName("driverDrivingPercentage")
    val driverDrivingPercentage: Int? = 0,
    @SerialName("driverEducationCode")
    val driverEducationCode: String? = "",
    @SerialName("driverFirstNameAr")
    val driverFirstNameAr: String? = "",
    @SerialName("driverFirstNameEn")
    val driverFirstNameEn: String? = "",
    @SerialName("driverGenderCode")
    val driverGenderCode: String? = "",
    @SerialName("driverHaveTrafficViolation")
    val driverHaveTrafficViolation: Boolean? = false,
    @SerialName("driverId")
    val driverId: Long? = 0,
    @SerialName("driverIdTypeCode")
    val driverIdTypeCode: Int? = 0,
    @SerialName("driverLastNameAr")
    val driverLastNameAr: String? = "",
    @SerialName("driverLastNameEn")
    val driverLastNameEn: String? = "",
    @SerialName("driverLicenses")
    val driverLicenses: List<DriverLicense>? = listOf(),
    @SerialName("driverMiddleNameAr")
    val driverMiddleNameAr: String? = "",
    @SerialName("driverMiddleNameEn")
    val driverMiddleNameEn: String? = "",
    @SerialName("driverNationalityCode")
    val driverNationalityCode: String? = "",
    @SerialName("driverNoaLastFiveYears")
    val driverNoaLastFiveYears: Int? = 0,
    @SerialName("driverNocLastFiveYears")
    val driverNocLastFiveYears: Int? = 0,
    @SerialName("driverOccupationCode")
    val driverOccupationCode: String? = "",
    @SerialName("driverOccupationDescAr")
    val driverOccupationDescAr: String? = "",
    @SerialName("driverRelationship")
    val driverRelationship: String? = "",
    @SerialName("driverSocialStatusCode")
    val driverSocialStatusCode: String? = "",
    @SerialName("driverTypeCode")
    val driverTypeCode: Int? = 0,
    @SerialName("drivingOppositeDirection")
    val drivingOppositeDirection: Boolean? = false,
    @SerialName("drivingPermissionToOtherCountry")
    val drivingPermissionToOtherCountry: Boolean? = false,
    @SerialName("drivingPermittedOtherCountryName")
    val drivingPermittedOtherCountryName: String? = String(),
    @SerialName("education")
    val education: String? = "",
    @SerialName("fullArabicName")
    val fullArabicName: String? = "",
    @SerialName("fullEnglishName")
    val fullEnglishName: String? = "",
    @SerialName("healthConditions")
    val healthConditions: String? = String(),
    @SerialName("healthConditionsValue")
    val healthConditionsValue: String? = String(),
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = "",
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = "",
    @SerialName("licenceCountry")
    val licenceCountry: String? = String(),
    @SerialName("licenceYear")
    val licenceYear: String? = String(),
    @SerialName("ncdFreeYears")
    val ncdFreeYears: String? = String(),
    @SerialName("ncdReference")
    val ncdReference: String? = String(),
    @SerialName("otherCountryTotalDrivingExperience")
    val otherCountryTotalDrivingExperience: String? = String(),
    @SerialName("overrideTraficLight")
    val overrideTraficLight: Boolean? = false,
    @SerialName("parkingViolation")
    val parkingViolation: Boolean? = false,
    @SerialName("policyHolderId")
    val policyHolderId: Int? = 0,
    @SerialName("referenceNo")
    val referenceNo: String? = "",
    @SerialName("restrictions")
    val restrictions: String? = String(),
    @SerialName("speedTicket")
    val speedTicket: Boolean? = false,
    @SerialName("trafficViolations")
    val trafficViolations: String? = String(),
    @SerialName("type")
    val type: String? = "",
    @SerialName("userId")
    val userId: Int? = 0,
    @SerialName("vehicleComprehensiveQuotation")
    val vehicleComprehensiveQuotation: String? = String(),
    @SerialName("vehicleId")
    val vehicleId: Int? = 0,
    @SerialName("vehicleNightParking")
    val vehicleNightParking: String? = "",
    @SerialName("version")
    val version: Int? = 0,
    @SerialName("violationCodeIds")
    val violationCodeIds: String? = String(),
    @SerialName("violations")
    val violations: List<String?>? = listOf(),
    @SerialName("workAndDrivingCitySame")
    val workAndDrivingCitySame: Boolean? = false
)