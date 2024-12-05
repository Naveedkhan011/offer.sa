package models

@kotlinx.serialization.Serializable
data class ErrorResponse(
    val errorCode: Int,
    val errorMessage: String
)