package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataXXXXXXX(
    @SerialName("newReferenceNoCurrentReferenceNo")
    val newReferenceNoCurrentReferenceNo: String? = "",
    @SerialName("newReferenceNoDriver")
    val newReferenceNoDriver: String? = "",
    @SerialName("newReferenceNoDriverLicense")
    val newReferenceNoDriverLicense: String? = "",
    @SerialName("newReferenceNoPolicyHolder")
    val newReferenceNoPolicyHolder: String? = "",
    @SerialName("newReferenceNoQuotationPrep")
    val newReferenceNoQuotationPrep: String? = "",
    @SerialName("newReferenceNoVehicle")
    val newReferenceNoVehicle: String? = "",
    @SerialName("policyHolder")
    val policyHolder: PolicyHolderXX? = PolicyHolderXX()
)