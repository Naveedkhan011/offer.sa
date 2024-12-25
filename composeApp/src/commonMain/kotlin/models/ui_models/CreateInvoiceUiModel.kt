package models.ui_models

import kotlinx.serialization.Serializable

@Serializable
data class CreateInvoiceUiModel(
    val companyCode: String = "",
    val deductibleValue: String? = null,
    val insuranceTypeId: Int? = null,
    val referenceNo: String = "",
    val selectedBenifitIds: ArrayList<String> = arrayListOf(""),
    val userid: Int = 0,
)
