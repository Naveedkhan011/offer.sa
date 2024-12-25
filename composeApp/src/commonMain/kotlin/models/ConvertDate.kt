package models

import kotlinx.serialization.Serializable

@Serializable
data class ConvertDate(
    val dateString: String = ""
)