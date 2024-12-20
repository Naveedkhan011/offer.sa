package models.VehicleListModels


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Driver(
    @SerialName("accidentCount")
    val accidentCount: String? = null,
    @SerialName("active")
    val active: Boolean? = null,
    @SerialName("additionalDriver")
    val additionalDriver: Boolean? = null,
    @SerialName("childrenBelow16")
    val childrenBelow16: String? = null,
    @SerialName("childrenBelowSixteen")
    val childrenBelowSixteen: String? = null,
    @SerialName("createdBy")
    val createdBy: String? = null,
    @SerialName("createdDate")
    val createdDate: String? = null,
    @SerialName("dobMonth")
    val dobMonth: String? = null,
    @SerialName("dobYear")
    val dobYear: String? = null,
    @SerialName("drifting")
    val drifting: Boolean? = null,
    @SerialName("driverAddressCity")
    val driverAddressCity: String? = null,
    @SerialName("driverAddressCityCode")
    val driverAddressCityCode: String? = null,
    @SerialName("driverBirthDateG")
    val driverBirthDateG: String? = null,
    @SerialName("driverBirthDateH")
    val driverBirthDateH: String? = null,
    @SerialName("driverBusinessCity")
    val driverBusinessCity: String? = null,
    @SerialName("driverBusinessCityCode")
    val driverBusinessCityCode: String? = null,
    @SerialName("driverDrivingPercentage")
    val driverDrivingPercentage: Int? = null,
    @SerialName("driverEducationCode")
    val driverEducationCode: String? = null,
    @SerialName("driverFirstNameAr")
    val driverFirstNameAr: String? = null,
    @SerialName("driverFirstNameEn")
    val driverFirstNameEn: String? = null,
    @SerialName("driverGenderCode")
    val driverGenderCode: String? = null,
    @SerialName("driverHaveTrafficViolation")
    val driverHaveTrafficViolation: Boolean? = null,
    @SerialName("driverId")
    val driverId: Long? = null,
    @SerialName("driverIdTypeCode")
    val driverIdTypeCode: Int? = null,
    @SerialName("driverLastNameAr")
    val driverLastNameAr: String? = null,
    @SerialName("driverLastNameEn")
    val driverLastNameEn: String? = null,
    @SerialName("driverLicenses")
    val driverLicenses: List<DriverLicense?>? = null,
    @SerialName("driverMiddleNameAr")
    val driverMiddleNameAr: String? = null,
    @SerialName("driverMiddleNameEn")
    val driverMiddleNameEn: String? = null,
    @SerialName("driverNationalityCode")
    val driverNationalityCode: String? = null,
    @SerialName("driverNoaLastFiveYears")
    val driverNoaLastFiveYears: Int? = null,
    @SerialName("driverNocLastFiveYears")
    val driverNocLastFiveYears: Int? = null,
    @SerialName("driverOccupationCode")
    val driverOccupationCode: String? = null,
    @SerialName("driverOccupationDescAr")
    val driverOccupationDescAr: String? = null,
    @SerialName("driverRelationship")
    val driverRelationship: String? = null,
    @SerialName("driverSocialStatusCode")
    val driverSocialStatusCode: String? = null,
    @SerialName("driverTypeCode")
    val driverTypeCode: Int? = null,
    @SerialName("drivingOppositeDirection")
    val drivingOppositeDirection: Boolean? = null,
    @SerialName("drivingPermissionToOtherCountry")
    val drivingPermissionToOtherCountry: Boolean? = null,
    @SerialName("drivingPermittedOtherCountryName")
    val drivingPermittedOtherCountryName: String? = null,
    @SerialName("education")
    val education: String? = null,
    @SerialName("fullArabicName")
    val fullArabicName: String? = null,
    @SerialName("fullEnglishName")
    val fullEnglishName: String? = null,
    @SerialName("healthConditions")
    val healthConditions: String? = null,
    @SerialName("healthConditionsValue")
    val healthConditionsValue: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = null,
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = null,
    @SerialName("licenceCountry")
    val licenceCountry: String? = null,
    @SerialName("licenceYear")
    val licenceYear: String? = null,
    @SerialName("ncdFreeYears")
    val ncdFreeYears: String? = null,
    @SerialName("ncdReference")
    val ncdReference: String? = null,
    @SerialName("otherCountryTotalDrivingExperience")
    val otherCountryTotalDrivingExperience: String? = null,
    @SerialName("overrideTraficLight")
    val overrideTraficLight: Boolean? = null,
    @SerialName("parkingViolation")
    val parkingViolation: Boolean? = null,
    @SerialName("policyHolderId")
    val policyHolderId: Int? = null,
    @SerialName("referenceNo")
    val referenceNo: String? = null,
    @SerialName("restrictions")
    val restrictions: String? = null,
    @SerialName("speedTicket")
    val speedTicket: Boolean? = null,
    @SerialName("trafficViolations")
    val trafficViolations: String? = null,
    @SerialName("type")
    val type: String? = null,
    @SerialName("userId")
    val userId: Int? = null,
    @SerialName("vehicleComprehensiveQuotation")
    val vehicleComprehensiveQuotation: String? = null,
    @SerialName("vehicleId")
    val vehicleId: Int? = null,
    @SerialName("vehicleNightParking")
    val vehicleNightParking: String? = null,
    @SerialName("version")
    val version: Int? = null,
    @SerialName("violationCodeIds")
    val violationCodeIds: String? = null,
    @SerialName("violations")
    val violations: List<String?>? = null,
    @SerialName("workAndDrivingCitySame")
    val workAndDrivingCitySame: Boolean? = null
)