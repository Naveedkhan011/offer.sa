package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataXXX(
    @SerialName("id")
    val id: Int = 0,
    @SerialName("insuranceTypeCodeModels")
    var insuranceTypeCodeModels: List<InsuranceTypeCodeModel> = listOf(),
    @SerialName("name")
    var name: String? = null
)