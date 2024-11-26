package models

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequest(
    val email: String,
    val name: String = "Client",
    val password: String,
    val userName: String,
    val userProfile: UserProfile,
    val userType: String = "Client"
)