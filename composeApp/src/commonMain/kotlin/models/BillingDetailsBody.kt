package models

import kotlinx.serialization.Serializable

@Serializable
data class BillingDetailsBody(
    val billingCity: String? = null,
    val billingCountry: String? = null,
    val billingIban: String? = null,
    val billingMobileNo: String? = null,
    val billingPostcode: String? = null,
    val billingState: String? = null,
    val billingStreet1: String? = null,
    val billingVat: String? = null,
    val cardType: String? = null,
    val customerEmail: String? = null,
    val customerGivenName: String? = null,
    val customerSurname: String? = null,
    val lang: String? = null,
    val otp: String? = null,
    val referenceNo: String? = null
)