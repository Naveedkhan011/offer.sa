package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActionXX(
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("code")
    val code: String? = "",
    @SerialName("createdBy")
    val createdBy: String? = "",
    @SerialName("createdDate")
    val createdDate: String? = "",
    /*@SerialName("hibernateLazyInitializer")
    val hibernateLazyInitializer: HibernateLazyInitializer? = HibernateLazyInitializer(),
    */
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = String(),
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = String(),
    @SerialName("name")
    val name: String? = "",
    @SerialName("serialNum")
    val serialNum: Int? = 0,
    @SerialName("version")
    val version: Int? = 0
)