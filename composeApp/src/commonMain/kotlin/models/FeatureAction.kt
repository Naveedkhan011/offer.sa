package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeatureAction(
    @SerialName("action")
    val action: ActionX? = ActionX(),
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("createdBy")
    val createdBy: String? = "",
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = null,
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = null,
    @SerialName("status")
    val status: Boolean? = false,
    @SerialName("version")
    val version: Int? = 0
)