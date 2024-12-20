package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonBasicInfo(
    @SerialName("birthDateG")
    val birthDateG: String? = null,
    @SerialName("birthDateH")
    val birthDateH: String? = null,
    @SerialName("convertDate")
    val convertDate: String? = null,
    @SerialName("familyName")
    val familyName: String? = null,
    @SerialName("familyNameT")
    val familyNameT: String? = null,
    @SerialName("fatherName")
    val fatherName: String? = null,
    @SerialName("fatherNameT")
    val fatherNameT: String? = null,
    @SerialName("firstName")
    val firstName: String? = null,
    @SerialName("firstNameT")
    val firstNameT: String? = null,
    @SerialName("fullNameAr")
    val fullNameAr: String? = null,
    @SerialName("fullNameEn")
    val fullNameEn: String? = null,
    @SerialName("grandFatherName")
    val grandFatherName: String? = null,
    @SerialName("grandFatherNameT")
    val grandFatherNameT: String? = null,
    @SerialName("idExpirationDate")
    val idExpirationDate: String? = null,
    @SerialName("maritalStatusCode")
    val maritalStatusCode: String? = null,
    @SerialName("maritalStatusDescAr")
    val maritalStatusDescAr: String? = null,
    @SerialName("nationalityCode")
    val nationalityCode: String? = null,
    @SerialName("occupationCode")
    val occupationCode: String? = null,
    @SerialName("occupationDescAr")
    val occupationDescAr: String? = null,
    @SerialName("personDrivingLicenses")
    val personDrivingLicenses: String? = null,
    @SerialName("sexCode")
    val sexCode: String? = null,
    @SerialName("sexDescAr")
    val sexDescAr: String? = null,
    @SerialName("subTribeName")
    val subTribeName: String? = null
)