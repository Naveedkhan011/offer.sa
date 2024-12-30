package utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.launch
import models.AllDropDownValues
import models.DataXXX
import models.Description
import models.InsuranceTypeCodeModel
import network.Ktor
import network.showInsuranceCodeName

class DropDownManager : ScreenModel {

    private var allDropDownValues by mutableStateOf(AllDropDownValues())

    // Reactive state for UI or dependent logic to observe
    var isDataLoaded by mutableStateOf(false)
        private set

    // API Call
    fun getDropDownValues() {
        screenModelScope.launch {
            try {
                val response =
                    Ktor.client
                        .get(showInsuranceCodeName)
                        .body<AllDropDownValues>()
                allDropDownValues = response
                isDataLoaded = true // Notify that data is ready
            } catch (_: Exception) {
                isDataLoaded = false // Handle error state if needed
            }
        }
    }

    // Lookup function to get data by ID
    fun getData(id: Int): DataXXX {
        allDropDownValues.data?.let { list ->
            list.forEach { item ->
                if (item.id == id) return item
            }
        }
        return DataXXX() // Return default if not found
    }

    val vehiclePurposeList: DataXXX
        get() = getData(13)

    val monthsEnglish: DataXXX
        get() = getData(39)

    val monthsArabic: DataXXX
        get() = getData(38)

    val arabicYears: DataXXX
        get() = getYear(true)

    val englishYears: DataXXX
        get() = getYear(false)

    private fun getYear(arabic: Boolean): DataXXX {
        val originalYear = getData(37)
        val yearCopy = originalYear.copy(
            insuranceTypeCodeModels = originalYear.insuranceTypeCodeModels.map { item ->
                item.copy(
                    description = item.description.copy()
                )
            }
        )

        yearCopy.insuranceTypeCodeModels.forEach { item ->
            if (arabic) {
                item.description.en = item.description.ar
            } else {
                item.description.ar = item.description.en
            }
        }

        return yearCopy
    }

    val manufactureYear: DataXXX
        get() = getYear(false)

    val vehicleSpecifications: DataXXX
        get() = getData(29)

    val vehicleParking: DataXXX
        get() = getData(25)

    val driverType: DataXXX
        get() = getData(9)

    val vehicleMileageExpectedAnnual: DataXXX
        get() = getData(30)

    val transmissionType: DataXXX
        get() = getData(26)

    val modificationTypes: DataXXX
        get() = getData(40)

    val accidentCount: DataXXX
        get() = DataXXX(
            id = 1,
            name = "Accident Count",
            insuranceTypeCodeModels = getAccidentCountList()
        )

    val noOfChildren: DataXXX
        get() = DataXXX(
            id = 1,
            name = "No of childrens",
            insuranceTypeCodeModels = getChildrenCount()
        )

    val driverRelation: DataXXX
        get() = getData(14)

    val healthConditionList: DataXXX
        get() = getData(41)

    val trafficViolationList: DataXXX
        get() = getData(24)

    val driverBusinessCityList: DataXXX
        get() = getData(32)

    val drivingLicenceCountryList: DataXXX
        get() = getData(33)

    val educationList: DataXXX
        get() = getData(12)

    val driverPercentageList: DataXXX
        get() = getData(15)

    val productType: DataXXX
        get() = getData(5)

    val paymentMethodsList: DataXXX
        get() = getData(43)

    // Static Data
    fun getChildrenCount(): List<InsuranceTypeCodeModel> {
        return listOf(
            InsuranceTypeCodeModel(code = 1, description = Description(en = "1", ar = "1")),
            InsuranceTypeCodeModel(code = 2, description = Description(en = "2", ar = "2")),
            InsuranceTypeCodeModel(code = 3, description = Description(en = "3", ar = "3")),
            InsuranceTypeCodeModel(code = 4, description = Description(en = "4", ar = "4")),
            InsuranceTypeCodeModel(code = 5, description = Description(en = "5", ar = "5"))
        )
    }

    fun getAccidentCountList(): List<InsuranceTypeCodeModel> {
        return listOf(
            InsuranceTypeCodeModel(code = 0, description = Description(en = "0", ar = "0")),
            InsuranceTypeCodeModel(code = 1, description = Description(en = "1", ar = "1")),
            InsuranceTypeCodeModel(code = 2, description = Description(en = "2", ar = "2")),
            InsuranceTypeCodeModel(code = 3, description = Description(en = "3", ar = "3")),
            InsuranceTypeCodeModel(code = 4, description = Description(en = "4", ar = "4")),
            InsuranceTypeCodeModel(code = 5, description = Description(en = "5", ar = "5")),
            InsuranceTypeCodeModel(code = 6, description = Description(en = "6", ar = "6")),
            InsuranceTypeCodeModel(code = 7, description = Description(en = "7", ar = "7")),
            InsuranceTypeCodeModel(code = 8, description = Description(en = "8", ar = "8")),
            InsuranceTypeCodeModel(code = 9, description = Description(en = "9", ar = "9")),
            InsuranceTypeCodeModel(code = 10, description = Description(en = "10", ar = "10")),
        )
    }
}
