package models.ui_models


import kotlinx.serialization.Serializable
import models.InsuranceTypeCodeModel
import models.enums.InsuranceType

@Serializable
data class BillingDetailsUIData(
    val billingCity: String = "",
    val billingCityError: String? = null,

    val billingCountry: InsuranceTypeCodeModel = InsuranceTypeCodeModel(),
    val billingCountryError: String? = null,

    val billingIban: String = "",
    val billingIbanError: String? = null,

    val billingMobileNo: String = "",
    val billingMobileNoError: String? = null,

    val billingPostcode: String = "",
    val billingPostcodeError: String? = null,

    val billingState: String = "",
    val billingStateError: String? = null,

    val billingStreet1: String = "",
    val billingStreet1Error: String? = null,

    val billingVat: String = "",
    val billingVatError: String? = null,

    val cardType: String = "",
    val cardTypeError: String? = null,

    val customerEmail: String = "",
    val customerEmailError: String? = null,

    val customerGivenName: String = "",
    val customerGivenNameError: String? = null,

    val customerSurname: String = "",
    val customerSurnameError: String? = null,

    val otp: String = "",
    val otpError: String? = null,
)