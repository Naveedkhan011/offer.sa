package models.VehicleListModels


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RolePermission(
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("createdBy")
    val createdBy: String? = "",
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("featureAction")
    val featureAction: FeatureAction? = FeatureAction(),
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = "",
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = "",
    @SerialName("version")
    val version: Int? = 0
)