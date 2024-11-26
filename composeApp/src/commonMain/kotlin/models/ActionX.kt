package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActionX(
    @SerialName("active")
    val active: Boolean? = null,
    @SerialName("code")
    val code: String? = null,
    @SerialName("createdBy")
    val createdBy: String? = null,
    @SerialName("createdDate")
    val createdDate: String? = null,
    @SerialName("hibernateLazyInitializer")
    val hibernateLazyInitializer: HibernateLazyInitializer? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = null,
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("serialNum")
    val serialNum: Int? = null,
    @SerialName("version")
    val version: Int? = null
)