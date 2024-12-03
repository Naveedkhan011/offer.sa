package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class QuotesListResponse(
    @SerialName("agentId")
    val agentId: String? = null,
    @SerialName("completed")
    val completed: Boolean? = null,
    @SerialName("createdAt")
    val createdAt: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("empty")
    val empty: Boolean? = null,
    @SerialName("id")
    val id: String? = null,
    @SerialName("mobile")
    val mobile: String? = null,
    @SerialName("quoteExpiry")
    val quoteExpiry: String? = null,
    @SerialName("referenceNo")
    val referenceNo: String? = null,
    @SerialName("responseComp")
    val responseComp: List<String?>? = null,
    @SerialName("responseTPL")
    val responseTPL: List<ResponseTPL?>? = null,
    @SerialName("updatedAt")
    val updatedAt: String? = null,
    @SerialName("userId")
    val userId: String? = null,
    @SerialName("errorCode")
    val errorCode: Int? = null,
    @SerialName("errorMessage")
    val errorMessage: String? = null
) {
}