package models

import kotlinx.serialization.Serializable

@Serializable
data class ServicesResponse(
    val code: String? = null,
    val `data`: List<Data> = emptyList(),
    val flag: Boolean = false,
    var message: String = "",
    //val objectData: String = "",
    val success: Boolean = false,
    val total: String? = ""
)