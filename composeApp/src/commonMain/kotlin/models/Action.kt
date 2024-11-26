package models

import kotlinx.serialization.Serializable

@Serializable
data class Action(
    val active: Boolean,
    val code: String,
    val createdBy: String,
    val createdDate: String,
    val hibernateLazyInitializer: HibernateLazyInitializer?,
    val id: Int,
    val lastModifiedBy: String?,
    val lastModifiedDate: String?,
    val name: String,
    val serialNum: Int,
    val version: Int
)