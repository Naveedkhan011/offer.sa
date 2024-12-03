package models

import kotlinx.serialization.Serializable

@Serializable
data class CreatePolicyHolderBody(
    val agentId: String? = null,
    val captchaCode: String? = null,
    val captchaId: String? = null,
    val clientId: String? = null,
    val customCard: String? = null,
    val dobMonth: String = "",
    val dobYear: String = "",
    val insuranceEffectiveDate: String = "",
    val insuranceType: String = "",
    val manufactureYear: String? = null,
    val nationalId: String = "",
    val referenceNo: String? = null,
    val selectedTab: String = "",
    val sellerNationalId: String? = null,
    val sequenceNumber: String = "",
    val userId: String = ""
)