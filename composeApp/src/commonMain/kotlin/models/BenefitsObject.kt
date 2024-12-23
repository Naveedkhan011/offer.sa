package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BenefitsObject(
    @SerialName("benefitCode")
    val benefitCode: Int? = null,
    @SerialName("benefitDescAr")
    val benefitDescAr: String? = null,
    @SerialName("benefitDescEn")
    val benefitDescEn: String? = null,
    @SerialName("benefitId")
    val benefitId: String? = null,
    @SerialName("benefitNameAr")
    val benefitNameAr: String? = null,
    @SerialName("benefitNameEn")
    val benefitNameEn: String? = null,
    @SerialName("benefitPrice")
    val benefitPrice: Double? = null,
    @SerialName("preInvoiceBenefitId")
    val preInvoiceBenefitId: String? = null
)