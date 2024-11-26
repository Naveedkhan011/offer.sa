package models

import kotlinx.serialization.Serializable

@Serializable
class NationalIdResponse(
    val success: Boolean,
    val code: String?,
    val data: String?,
    val flag: Boolean?,
    val message: String?,
    val objectData: String?,
    val total: String?

)