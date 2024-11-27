package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Description(
    @SerialName("ar")
    val ar: String = "",
    @SerialName("en")
    val en: String = ""
)