package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePolicyHolderBody(
    @SerialName("agentId")
    val agentId: String? = null,
    @SerialName("captchaCode")
    val captchaCode: String? = null,
    @SerialName("captchaId")
    val captchaId: String? = null,
    @SerialName("clientId")
    val clientId: String? = null,
    @SerialName("customCard")
    val customCard: String? = null,
    @SerialName("dobMonth")
    val dobMonth: String = "",
    @SerialName("dobYear")
    val dobYear: String = "",
    @SerialName("insuranceEffectiveDate")
    val insuranceEffectiveDate: String = "",
    @SerialName("insuranceType")
    val insuranceType: String = "",
    @SerialName("manufactureYear")
    val manufactureYear: String? = null,
    @SerialName("nationalId")
    val nationalId: String = "",
    @SerialName("referenceNo")
    val referenceNo: String? = null,
    @SerialName("selectedTab")
    val selectedTab: String = "",
    @SerialName("sellerNationalId")
    val sellerNationalId: String? = null,
    @SerialName("sequenceNumber")
    val sequenceNumber: String = "",
    @SerialName("userId")
    val userId: Int = 0
)