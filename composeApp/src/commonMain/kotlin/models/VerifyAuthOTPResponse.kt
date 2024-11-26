package models

data class VerifyAuthOTPResponse(
    val errorCode: Int,
    val errorDescription: String,
    val errorMessage: String,
    val errorStackTrace: String? = null,
    val stackTraceElements: String? = null,
    val url: String
)