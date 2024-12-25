package models.body_models

import kotlinx.serialization.Serializable

@Serializable
data class CreateDriverBody(
    val childrenBelow16: Int = 0,
    val dobMonth: String = "",
    val dobYear: String = "",
    val driverAddressCity: String = "",
    val driverBusinessCity: Int = 0,
    val driverFirstNameAr: String = "",
    val driverFirstNameEn: String = "",
    val driverId: String = "",
    val driverLastNameAr: String = "",
    val driverLastNameEn: String = "",
    val driverLicenses: List<DriverLicense> = listOf(),
    val driverMiddleNameAr: String = "",
    val driverMiddleNameEn: String = "",
    val driverNoaLastFiveYears: Int = 0,
    val driverNocLastFiveYears: Int = 0,
    val driverRelationship: Int = 0,
    val driverTypeCode: Int = 0,
    val education: Int = 0,
    val fullArabicName: String = "",
    val fullEnglishName: String = "",
    val healthConditions: List<Int>? = null,
    val id: String = "",
    val isAdditionalDriver: Boolean = false,
    val licenseCountryCode: String = "",
    val licenseNumberYears: String = "",
    val policyHolderId: Int = 0,
    val referenceNo: String = "",
    val type: String = "",
    val vehicleId: Int = 0,
    val vehicleNightParking: Int = 0,
    val violationCodeIds: List<Int>? = null
)