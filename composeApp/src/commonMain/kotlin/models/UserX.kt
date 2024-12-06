package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserX(
    @SerialName("active")
    val active: Boolean? = false,
    @SerialName("activeEmail")
    val activeEmail: Boolean? = false,
    @SerialName("additionalDrivers")
    val additionalDrivers: String? = String(),
    @SerialName("changePassword")
    val changePassword: Boolean? = false,
    @SerialName("createdBy")
    val createdBy: String? = String(),
    @SerialName("createdDate")
    val createdDate: String? = "",
    @SerialName("dob")
    val dob: String? = String(),
    @SerialName("dobMonthAr")
    val dobMonthAr: String? = String(),
    @SerialName("dobMonthEn")
    val dobMonthEn: String? = String(),
    @SerialName("dobYearAr")
    val dobYearAr: String? = String(),
    @SerialName("dobYearEn")
    val dobYearEn: String? = String(),
    @SerialName("email")
    val email: String? = "",
    @SerialName("emailVerified")
    val emailVerified: Boolean? = false,
    @SerialName("enabled")
    val enabled: Boolean? = false,
    @SerialName("hibernateLazyInitializer")
    val hibernateLazyInitializer: HibernateLazyInitializerX? = HibernateLazyInitializerX(),
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = "",
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = "",
    @SerialName("name")
    val name: String? = "",
    @SerialName("nameAr")
    val nameAr: String? = String(),
    @SerialName("oldPassword")
    val oldPassword: String? = String(),
    @SerialName("rememberMe")
    val rememberMe: Boolean? = false,
    @SerialName("roles")
    val roles: List<RoleX>? = listOf(),
    @SerialName("sequenceId")
    val sequenceId: String? = String(),
    @SerialName("userName")
    val userName: String? = "",
    @SerialName("userPermissionList")
    val userPermissionList: List<String?>? = listOf(),
    @SerialName("userProfile")
    val userProfile: UserProfile? = UserProfile(),
    @SerialName("version")
    val version: Int? = 0
)