package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Benefit(
    @SerialName("benefitCode")
    val benefitCode: Int? = null,
    @SerialName("benefitDescAr")
    val benefitDescAr: String? = null,
    @SerialName("benefitDescEn")
    val benefitDescEn: String? = null,
    @SerialName("benefitId")
    val benefitId: String = "",
    @SerialName("benefitNameAr")
    val benefitNameAr: String? = null,
    @SerialName("benefitNameEn")
    val benefitNameEn: String = "",
    @SerialName("benefitPrice")
    val benefitPrice: Double = 0.0,
    @SerialName("preInvoiceBenefitId")
    val preInvoiceBenefitId: String? = null
)