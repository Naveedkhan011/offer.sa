package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeatureActionX(
    @SerialName("action")
    val action: ActionXX? = ActionXX(),
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("createdBy")
    val createdBy: String? = "",
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = String(),
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = String(),
    @SerialName("status")
    val status: Boolean? = false,
    @SerialName("version")
    val version: Int? = 0
)