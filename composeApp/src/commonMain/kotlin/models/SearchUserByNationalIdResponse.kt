package models

import kotlinx.serialization.Serializable

@Serializable
data class SearchUserByNationalIdResponse(
    val dateString: String? = "",
    val personBasicInfo: PersonBasicInfoX = PersonBasicInfoX(),
    val personId: String? = "",
    val personIdInfo: PersonIdInfo = PersonIdInfo()
)