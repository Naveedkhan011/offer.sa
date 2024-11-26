package models

import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val active: Boolean?,
    val createdBy: String?,
    val createdDate: String?,
    val description: String?,
    val descriptionAr: String?,
    val id: Int?,
    val imageURL: String,
    val keyword: String?,
    val language: String?,
    val lastModifiedBy: String?,
    val lastModifiedDate: String?,
    val pageLink: String?,
    val pageTag: String?,
    val published: Boolean?,
    var title: String?,
    val titleAr: String?,
    val version: Int?
)