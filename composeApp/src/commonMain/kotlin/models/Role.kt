package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Role(
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("createdBy")
    val createdBy: String? = null,
    @SerialName("createdDate")
    val createdDate: String? = null,
    @SerialName("description")
    val description: String? = "",
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = null,
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = null,
    @SerialName("name")
    val name: String? = "",
    @SerialName("rolePermissionList")
    val rolePermissionList: List<RolePermission>? = listOf(),
    @SerialName("version")
    val version: Int? = 0
)