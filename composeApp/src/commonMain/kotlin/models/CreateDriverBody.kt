package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateDriverBody(
    @SerialName("childrenBelow16")
    val childrenBelow16: Int? = 0,
    @SerialName("dobMonth")
    val dobMonth: String? = "",
    @SerialName("dobYear")
    val dobYear: String? = "",
    @SerialName("driverAddressCity")
    val driverAddressCity: String? = "",
    @SerialName("driverBusinessCity")
    val driverBusinessCity: Int? = 0,
    @SerialName("driverFirstNameAr")
    val driverFirstNameAr: String? = "",
    @SerialName("driverFirstNameEn")
    val driverFirstNameEn: String? = "",
    @SerialName("driverId")
    val driverId: String = "",
    @SerialName("driverLastNameAr")
    val driverLastNameAr: String? = "",
    @SerialName("driverLastNameEn")
    val driverLastNameEn: String? = "",
    @SerialName("driverLicenses")
    val driverLicenses: List<AddLicense>? = listOf(),
    @SerialName("driverMiddleNameAr")
    val driverMiddleNameAr: String? = "",
    @SerialName("driverMiddleNameEn")
    val driverMiddleNameEn: String? = "",
    @SerialName("driverNoaLastFiveYears")
    val driverNoaLastFiveYears: Int? = 0,
    @SerialName("driverNocLastFiveYears")
    val driverNocLastFiveYears: Int? = 0,
    @SerialName("driverRelationship")
    val driverRelationship: Int? = 0,
    @SerialName("driverTypeCode")
    val driverTypeCode: Int? = 0,
    @SerialName("education")
    val education: Int? = 0,
    @SerialName("fullArabicName")
    val fullArabicName: String? = "",
    @SerialName("fullEnglishName")
    val fullEnglishName: String? = "",
    @SerialName("healthConditions")
    val healthConditions: String? = String(),
    @SerialName("id")
    val id: String? = "",
    @SerialName("isAdditionalDriver")
    val isAdditionalDriver: Boolean? = false,
    @SerialName("licenseCountryCode")
    val licenseCountryCode: String? = "",
    @SerialName("licenseNumberYears")
    val licenseNumberYears: String? = "",
    @SerialName("policyHolderId")
    val policyHolderId: Int? = 0,
    @SerialName("referenceNo")
    val referenceNo: String? = "",
    @SerialName("type")
    val type: String? = "",
    @SerialName("vehicleId")
    val vehicleId: Int? = 0,
    @SerialName("vehicleNightParking")
    val vehicleNightParking: Int? = 0,
    @SerialName("violationCodeIds")
    val violationCodeIds: String? = String()
)