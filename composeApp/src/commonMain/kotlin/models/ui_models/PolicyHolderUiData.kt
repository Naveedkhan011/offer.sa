package models.ui_models

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import models.InsuranceTypeCodeModel

data class PolicyHolderUiData(

    var customCard: String = "",
    var customCardError: String? = null,

    var dobMonth: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    var dobMonthError: String? = null,

    var dobYear: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    var dobYearError: String? = null,

    var insuranceEffectiveDate: String = formatDateWithOneDayForward(),
    var insuranceEffectiveDateError: String? = null,
    var insuranceType: String = "",

    var manufactureYear: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    var manufactureYearError: String? = null,

    var nationalId: String = "2537995140",
    var nationalIDError: String? = null,

    var selectedTab: String = "",

    var sellerNationalId: String = "",
    var sellerNationalIdError: String? = null,

    var sequenceNumber: String = "983236710",
    var sequenceNumberError: String? = null,

    var userId: Int = 0
)

fun formatDateWithOneDayForward(): String {
    // Get the current date using Clock.System
    val currentDate = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
    // Add one day to the current date
    val nextDate = currentDate.plus(1, DateTimeUnit.DAY)
    // Format the date to the desired format (YYYY-MM-DD)
    val formattedDate = "${nextDate.year}-${nextDate.monthNumber.toString().padStart(2, '0')}-${nextDate.dayOfMonth.toString().padStart(2, '0')}"
    return formattedDate
}