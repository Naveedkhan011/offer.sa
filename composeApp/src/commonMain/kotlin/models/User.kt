package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("activeEmail")
    val activeEmail: Boolean? = false,
    @SerialName("additionalDrivers")
    val additionalDrivers: String? = null,
    @SerialName("changePassword")
    val changePassword: Boolean? = false,
    @SerialName("createdBy")
    val createdBy: String? = null,
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("dob")
    val dob: String? = null,
    @SerialName("dobMonthAr")
    val dobMonthAr: String? = null,
    @SerialName("dobMonthEn")
    val dobMonthEn: String? = null,
    @SerialName("dobYearAr")
    val dobYearAr: String? = null,
    @SerialName("dobYearEn")
    val dobYearEn: String? = null,
    @SerialName("email")
    val email: String? = "",
    @SerialName("emailVerified")
    val emailVerified: Boolean? = false,
    @SerialName("enabled")
    val enabled: Boolean? = false,
    @SerialName("id")
    val id: Int = 0,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = null,
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = "",
    @SerialName("name")
    val name: String? = "",
    @SerialName("nameAr")
    val nameAr: String? = null,
    @SerialName("oldPassword")
    val oldPassword: String? = null,
    @SerialName("rememberMe")
    val rememberMe: Boolean? = false,
    @SerialName("roles")
    val roles: List<Role>? = listOf(),
    @SerialName("sequenceId")
    val sequenceId: String? = null,
    @SerialName("userName")
    val userName: String? = "",
    @SerialName("userPermissionList")
    val userPermissionList: List<String?>? = listOf(),
    @SerialName("userProfile")
    val userProfile: UserProfileX? = UserProfileX(),
    @SerialName("version")
    val version: Int? = 0
)