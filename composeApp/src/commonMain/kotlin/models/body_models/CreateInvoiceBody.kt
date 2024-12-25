package models.body_models

import kotlinx.serialization.Serializable

@Serializable
data class CreateInvoiceBody(
    val companyCode: String = "",
    val deductibleValue: String? = null,
    val insuranceTypeId: Int? = null,
    val referenceNo: String = "",
    val selectedBenifitIds: List<String> = listOf(),
    val userid: Int = 0,
)
