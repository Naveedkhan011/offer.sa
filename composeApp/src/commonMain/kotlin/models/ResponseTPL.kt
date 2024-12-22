package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseTPL(
    @SerialName("errors")
    val errors: List<String?>? = null,
    @SerialName("header")
    val header: Header = Header(),
    @SerialName("products")
    val products: List<Product?> = listOf(),
    @SerialName("responseMetaData")
    val responseMetaData: ResponseMetaData? = null,
    @SerialName("rootErrors")
    val rootErrors: List<String?>? = null,
    @SerialName("statusCode")
    val statusCode: Int? = null,

    var isExpended : Boolean = false
)