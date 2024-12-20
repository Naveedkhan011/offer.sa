package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Description(
    @SerialName("ar")
    var ar: String = "",
    @SerialName("en")
    var en: String = ""
)