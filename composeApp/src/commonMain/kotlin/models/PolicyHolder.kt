package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PolicyHolder(
    @SerialName("active")
    val active: Boolean? = null,
    @SerialName("additionalNumber")
    val additionalNumber: String? = null,
    @SerialName("agentId")
    val agentId: String? = null,
    @SerialName("buildingNumber")
    val buildingNumber: String? = null,
    @SerialName("clientId")
    val clientId: String? = null,
    @SerialName("companyNameAr")
    val companyNameAr: String? = null,
    @SerialName("companyNameEn")
    val companyNameEn: String? = null,
    @SerialName("createdBy")
    val createdBy: String? = null,
    @SerialName("createdDate")
    val createdDate: String? = null,
    @SerialName("customCard")
    val customCard: String? = null,
    @SerialName("dateOfBirth")
    val dateOfBirth: String? = null,
    @SerialName("district")
    val district: String? = null,
    @SerialName("dobMonth")
    val dobMonth: String? = null,
    @SerialName("dobYear")
    val dobYear: String? = null,
    @SerialName("email")
    val email: String? = null,
    @SerialName("familyNameAr")
    val familyNameAr: String? = null,
    @SerialName("familyNameEn")
    val familyNameEn: String? = null,
    @SerialName("fatherNameAr")
    val fatherNameAr: String? = null,
    @SerialName("firstNameAr")
    val firstNameAr: String? = null,
    @SerialName("firstNameEn")
    val firstNameEn: String? = null,
    @SerialName("fullNameAr")
    val fullNameAr: String? = null,
    @SerialName("fullNameEn")
    val fullNameEn: String? = null,
    @SerialName("grandFatherNameAr")
    val grandFatherNameAr: String? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("insuranceEffectiveDate")
    val insuranceEffectiveDate: String? = null,
    @SerialName("insuranceId")
    val insuranceId: String? = null,
    @SerialName("insuranceType")
    val insuranceType: Int? = null,
    @SerialName("insuredAddressCity")
    val insuredAddressCity: String? = null,
    @SerialName("insuredAddressCityCode")
    val insuredAddressCityCode: String? = null,
    @SerialName("insuredBirthDateG")
    val insuredBirthDateG: String? = null,
    @SerialName("insuredBirthDateH")
    val insuredBirthDateH: String? = null,
    @SerialName("insuredBusinessCity")
    val insuredBusinessCity: String? = null,
    @SerialName("insuredChildrenBelowSixteenYears")
    val insuredChildrenBelowSixteenYears: Int? = null,
    @SerialName("insuredEducationCode")
    val insuredEducationCode: String? = null,
    @SerialName("insuredGenderCode")
    val insuredGenderCode: String? = null,
    @SerialName("insuredIdExpireDate")
    val insuredIdExpireDate: String? = null,
    @SerialName("insuredIdIssuePlace")
    val insuredIdIssuePlace: String? = null,
    @SerialName("insuredIdIssuePlaceCode")
    val insuredIdIssuePlaceCode: String? = null,
    @SerialName("insuredIdTypeCode")
    val insuredIdTypeCode: String? = null,
    @SerialName("insuredNationalityCode")
    val insuredNationalityCode: String? = null,
    @SerialName("insuredOccupationCode")
    val insuredOccupationCode: String? = null,
    @SerialName("insuredOccupationDescAr")
    val insuredOccupationDescAr: String? = null,
    @SerialName("insuredSocialStatusCode")
    val insuredSocialStatusCode: String? = null,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = null,
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = null,
    @SerialName("manufactureYear")
    val manufactureYear: String? = null,
    @SerialName("middleNameAr")
    val middleNameAr: String? = null,
    @SerialName("middleNameEn")
    val middleNameEn: String? = null,
    @SerialName("mobile")
    val mobile: String? = null,
    @SerialName("nationalId")
    val nationalId: String? = null,
    @SerialName("nationality")
    val nationality: String? = null,
    @SerialName("postCode")
    val postCode: String? = null,
    @SerialName("promoCode")
    val promoCode: String? = null,
    @SerialName("quotationPrepId")
    val quotationPrepId: Int? = null,
    @SerialName("referenceNo")
    val referenceNo: String? = null,
    @SerialName("relationship")
    val relationship: String? = null,
    @SerialName("selectedTab")
    val selectedTab: String? = null,
    @SerialName("sellerNationalId")
    val sellerNationalId: String? = null,
    @SerialName("sequenceNo")
    val sequenceNo: String? = null,
    @SerialName("sequenceNumber")
    val sequenceNumber: String? = null,
    @SerialName("sponsoredId")
    val sponsoredId: String? = null,
    @SerialName("street")
    val street: String? = null,
    @SerialName("userId")
    val userId: Int? = null,
    @SerialName("version")
    val version: Int? = null
)