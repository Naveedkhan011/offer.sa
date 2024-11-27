package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataXXX(
    @SerialName("id")
    val id: Int? = null,
    @SerialName("insuranceTypeCodeModels")
    val insuranceTypeCodeModels: List<InsuranceTypeCodeModel?>? = null,
    @SerialName("name")
    val name: String? = null
)