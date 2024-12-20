package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class InsuranceTypeCodeModel(
    @SerialName("code")
    val code: Int = 0,
    @SerialName("description")
    var description: Description = Description(),
    @SerialName("id")
    val id: Int = 13,
    @SerialName("isChecked")
    var isChecked : Boolean = false
)