package models

import kotlinx.serialization.Serializable

@Serializable
data class PersonIdInfo(
    val id: String? = null,
    val idExpirationDate: String = "",
    val idIssuePlaceDescAR: String? = null,
    val idType: String = ""
)