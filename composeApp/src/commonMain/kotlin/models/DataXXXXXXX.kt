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
/*
    @SerialName("version")
    val version: String? = "",
    @SerialName("createdBy")
    val createdBy: String? = "",
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = "",
    @SerialName("id")
    val id: String? = "",*/
    @SerialName("currentReferenceNo")
    val currentReferenceNo: String? = "",/*
    @SerialName("expiryDate")
    val expiryDate: String? = "",
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = "",
    @SerialName("active")
    val active: String? = "",*/

    @SerialName("policyHolder")
    val policyHolder: PolicyHolderXX? = PolicyHolderXX()
)