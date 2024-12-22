package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DataXXXXXXXX(
    @SerialName("paymentId")
    val paymentId: String? = null,
    @SerialName("paymentType")
    val paymentType: String? = null
)