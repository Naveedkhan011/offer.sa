package models

import kotlinx.serialization.Serializable

@Serializable
data class PersonBasicInfoX(
    val birthDateG: String? = "",
    val birthDateH: String? = null,
    val convertDate: ConvertDate = ConvertDate(),
    val familyName: String? = "",
    val familyNameT: String? = "",
    val fatherName: String? = "",
    val fatherNameT: String? = "",
    val firstName: String? = "",
    val firstNameT: String? = "",
    val fullNameAr: String? = "",
    val fullNameEn: String? = "",
    val grandFatherName: String? = "",
    val grandFatherNameT: String? = "",
    val idExpirationDate: String? = null,
    val maritalStatusCode: String? = "",
    val maritalStatusDescAr: String? = "",
    val nationalityCode: String? = "",
    val occupationCode: String? = "",
    val occupationDescAr: String? = "",
    val personDrivingLicenses: String? = null,
    val sexCode: String? = "",
    val sexDescAr: String? = "",
    val subTribeName: String? = null
)