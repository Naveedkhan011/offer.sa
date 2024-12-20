package models

import kotlinx.serialization.Serializable

@Serializable
data class Drivers(
    var accidentCount: String? = String(),
    var active: Boolean? = false,
    var additionalDriver: Boolean? = false,
    var childrenBelow16: String? = "",
    var childrenBelowSixteen: String? = String(),
    var createdBy: String? = String(),
    var createdDate: String? = "",
    var dobMonth: String? = "",
    var dobYear: String? = "",
    var drifting: Boolean? = false,
    var driverAddressCity: String? = "",
    var driverAddressCityCode: String? = String(),
    var driverBirthDateG: String? = "",
    var driverBirthDateH: String? = "",
    var driverBusinessCity: String? = "",
    var driverBusinessCityCode: String? = String(),
    var driverDrivingPercentage: Int? = 0,
    var driverEducationCode: String? = "",
    var driverFirstNameAr: String? = "",
    var driverFirstNameEn: String? = "",
    var driverGenderCode: String? = "",
    var driverHaveTrafficViolation: Boolean? = false,
    var driverId: Long? = 0,
    var driverIdTypeCode: Int? = 0,
    var driverLastNameAr: String? = "",
    var driverLastNameEn: String? = "",
    var driverLicenses: List<DriverLicenseX?> = listOf(),
    var driverMiddleNameAr: String? = "",
    var driverMiddleNameEn: String? = "",
    var driverNationalityCode: String? = "",
    var driverNoaLastFiveYears: Int? = 0,
    var driverNocLastFiveYears: Int? = 0,
    var driverOccupationCode: String? = "",
    var driverOccupationDescAr: String? = "",
    var driverRelationship: String? = "",
    var driverSocialStatusCode: String? = "",
    var driverTypeCode: Int? = 0,
    var drivingOppositeDirection: Boolean? = false,
    var drivingPermissionToOtherCountry: Boolean? = false,
    var drivingPermittedOtherCountryName: String? = String(),
    var education: String? = "",
    var fullArabicName: String? = "",
    var fullEnglishName: String? = "",
    var healthConditions: String? = String(),
    var healthConditionsValue: String? = String(),
    var id: Int? = 0,
    var lastModifiedBy: String? = String(),
    var lastModifiedDate: String? = "",
    var licenceCountry: String? = String(),
    var licenceYear: String? = String(),
    var ncdFreeYears: String? = String(),
    var ncdReference: String? = String(),
    var otherCountryTotalDrivingExperience: String? = String(),
    var overrideTraficLight: Boolean? = false,
    var parkingViolation: Boolean? = false,
    var policyHolderId: Int? = 0,
    var referenceNo: String? = "",
    var restrictions: String? = String(),
    var speedTicket: Boolean? = false,
    var trafficViolations: String? = String(),
    var type: String? = "",
    var userId: Int? = 0,
    var vehicleComprehensiveQuotation: String? = String(),
    var vehicleId: Int? = 0,
    var vehicleNightParking: String? = "",
    var version: Int? = 0,
    var violationCodeIds: String? = String(),
    var violations: List<String?>? = listOf(),
    var workAndDrivingCitySame: Boolean? = false
)