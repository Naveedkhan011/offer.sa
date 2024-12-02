package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InsuranceTypeCodeModel(
    @SerialName("code")
    val code: Int = 1,
    @SerialName("description")
    val description: Description = Description(),
    @SerialName("id")
    val id: Int = 13,
    @SerialName("isChecked")
    var isChecked : Boolean = false
)