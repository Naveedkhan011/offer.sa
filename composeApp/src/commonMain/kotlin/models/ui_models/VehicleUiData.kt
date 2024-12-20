package models.ui_models


import kotlinx.serialization.Serializable
import models.InsuranceTypeCodeModel

@Serializable
data class VehicleUiData(
    var vehicleUse: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    var vehicleValue: String = "",
    var insuranceType: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    var vehicleAgencyRepair: Int = 0,
    var km: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    var vehicleOvernightParkingLocation: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    var accidentCount: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    var transmission: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    var vehicleModification: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    var specificationCodeIds: ArrayList<Int> = arrayListOf(),
    var approved: String = "",
    var vehicleModificationDetails: String = ""
)