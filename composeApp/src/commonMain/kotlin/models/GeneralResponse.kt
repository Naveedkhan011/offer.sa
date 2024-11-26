package models

import kotlinx.serialization.Serializable

@Serializable
data class GeneralResponse(
    val success: Boolean,
    val code: String?,
    val data: DataX?,
    val flag: Boolean?,
    val message: String?,
    val objectData: String?,
    val total: String?
)