package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RoleX(
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("createdBy")
    val createdBy: String? = String(),
    @SerialName("createdDate")
    val createdDate: String? = String(),
    @SerialName("description")
    val description: String? = "",
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = String(),
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = String(),
    @SerialName("name")
    val name: String? = "",
    @SerialName("rolePermissionList")
    val rolePermissionList: List<RolePermissionX>? = listOf(),
    @SerialName("version")
    val version: Int? = 0
)