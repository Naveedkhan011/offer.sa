package models.ui_models


import kotlinx.serialization.Serializable
import models.AddLicense
import models.InsuranceTypeCodeModel

@Serializable
data class CreateDriverBody(
    val childrenBelow16: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val dobMonth: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val dobYear: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverAddressCity: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverBusinessCity: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverFirstNameAr: String? = "",
    val driverFirstNameEn: String? = "",
    val driverId: String = "",
    val driverLastNameAr: String? = "",
    val driverLastNameEn: String? = "",
    val driverLicenses: List<AddLicense>? = listOf(),
    val driverMiddleNameAr: String? = "",
    val driverMiddleNameEn: String? = "",
    val driverNoaLastFiveYears: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverNocLastFiveYears: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverRelationship: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverTypeCode: Int? = 0,
    val education: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val fullArabicName: String? = "",
    val fullEnglishName: String? = "",
    val healthConditions: ArrayList<String> = arrayListOf(""),
    val id: String? = "",
    val isAdditionalDriver: Boolean? = false,
    val licenseCountryCode: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val licenseNumberYears: String? = "",
    val policyHolderId: Int? = 0,
    val referenceNo: String? = "",
    val type: String? = "",
    val vehicleId: Int? = 0,
    val vehicleNightParking: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val violationCodeIds: ArrayList<String> = arrayListOf(""),
    val driverLicense: String = ""
)