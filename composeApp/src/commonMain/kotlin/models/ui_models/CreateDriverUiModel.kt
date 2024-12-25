package models.ui_models


import kotlinx.serialization.Serializable
import models.InsuranceTypeCodeModel

@Serializable
data class CreateDriverUiModel(
    var driverId : String = "",
    val childrenBelow16: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val dobMonth: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val dobYear: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverAddressCity: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverBusinessCity: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverLicenses: List<AddLicenseUiData> = listOf(),
    val driverNoaLastFiveYears: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverNocLastFiveYears: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val driverRelationship: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val education: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val healthConditions: ArrayList<Int> = arrayListOf(),
    val licenseCountryCode: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val vehicleNightParking: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val violationCodeIds: ArrayList<Int> = arrayListOf(),
    var driverLicense: String = "",
    val licenseNumberYears: String? = "",
    val driverTypeCode: InsuranceTypeCodeModel = InsuranceTypeCodeModel()
)