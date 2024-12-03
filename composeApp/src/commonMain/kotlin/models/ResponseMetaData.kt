package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMetaData(
    @SerialName("maxLiability")
    val maxLiability: Int? = null,
    @SerialName("policyEffectiveDate")
    val policyEffectiveDate: String? = null,
    @SerialName("policyExpiryDate")
    val policyExpiryDate: String? = null,
    @SerialName("policyIssueDate")
    val policyIssueDate: String? = null,
    @SerialName("policyNumber")
    val policyNumber: String? = null,
    @SerialName("quotationDate")
    val quotationDate: String? = null,
    @SerialName("quotationExpiryDate")
    val quotationExpiryDate: String? = null,
    @SerialName("quotationNo")
    val quotationNo: String? = null,
    @SerialName("referenceId")
    val referenceId: String? = null
)