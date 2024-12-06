package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    @SerialName("accountNumber")
    val accountNumber: String? = null,
    @SerialName("active")
    val active: Boolean? = null,
    @SerialName("agreement")
    val agreement: String? = null,
    @SerialName("bankName")
    val bankName: String? = null,
    @SerialName("cityName")
    val cityName: String? = null,
    @SerialName("createdBy")
    val createdBy: String? = null,
    @SerialName("createdDate")
    val createdDate: String? = null,
    @SerialName("defaultLanguage")
    val defaultLanguage: String? = null,
    @SerialName("fullNameAr")
    val fullNameAr: String? = null,
    @SerialName("fullNameEn")
    val fullNameEn: String? = null,
    @SerialName("iban")
    val iban: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = null,
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = null,
    @SerialName("mobileNumber")
    val mobileNumber: String? = null,
    @SerialName("nationalId")
    val nationalId: String? = null,
    @SerialName("note")
    val note: String? = null,
    @SerialName("profilePicUrl")
    val profilePicUrl: String? = null,
    @SerialName("version")
    val version: Int? = null
)