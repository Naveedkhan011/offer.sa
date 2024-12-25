package presentation.screen.quotes_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import dropDownValues
import models.CreatePolicyHolderBody
import models.DataXXX
import utils.language.language_manager.LanguageManager.currentLanguage

class QuotesViewModelNew : ScreenModel {

    //lookup data
    val vehiclePurposeList: DataXXX = dropDownValues.getData(13)
    val months: DataXXX = dropDownValues.getData(if (currentLanguage == "en") 39 else 38)
    val monthsEnglish: DataXXX = dropDownValues.getData(39)
    val monthsArabic: DataXXX = dropDownValues.getData(38)
    val years: DataXXX = dropDownValues.getData(37)
    val vehicleSpecifications: DataXXX = dropDownValues.getData(29)
    val vehicleParking: DataXXX = dropDownValues.getData(25)
    val vehicleMileageExpectedAnnual: DataXXX = dropDownValues.getData(30)
    val transmissionType: DataXXX = dropDownValues.getData(26)
    val modificationTypes: DataXXX = dropDownValues.getData(40)
    val accidentCount: DataXXX =
        DataXXX(
            id = 1,
            name = "Accident Count",
            insuranceTypeCodeModels = dropDownValues.getAccidentCountList()
        )
    val noOfChildren: DataXXX =
        DataXXX(
            id = 1,
            name = "No of children",
            insuranceTypeCodeModels = dropDownValues.getChildrenCount()
        )
    val driverRelation: DataXXX = dropDownValues.getData(14)
    val healthConditionList: DataXXX = dropDownValues.getData(41)
    val trafficViolationList: DataXXX = dropDownValues.getData(24)
    val driverBusinessCityList: DataXXX = dropDownValues.getData(32)
    val drivingLicenceCountryList: DataXXX = dropDownValues.getData(33)
    val educationList: DataXXX = dropDownValues.getData(12)
    val driverPercentageList: DataXXX = dropDownValues.getData(15)
    val productType: DataXXX = dropDownValues.getData(5)


    var currentStep by mutableStateOf(1)

    val createPolicyHolderBody by mutableStateOf(CreatePolicyHolderBody())

}