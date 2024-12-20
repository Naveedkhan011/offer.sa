package models.VehicleListModels


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FeatureAction(
    @SerialName("action")
    val action: Action? = Action(),
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("createdBy")
    val createdBy: String? = "",
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = "",
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = "",
    @SerialName("status")
    val status: Boolean? = false,
    @SerialName("version")
    val version: Int? = 0
)