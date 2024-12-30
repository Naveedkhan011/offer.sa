package network

import network.Ktor.BASE_URL

const val createDriverEndPoint = "$BASE_URL/insurance/rest/createDriver"
const val servicesEndPoint = "$BASE_URL/insurance/rest/serviceList"
const val createPolicyHolderEndPoint = "$BASE_URL/insurance/rest/createPolicyHolder"
const val updateVehicleEndPoint = "$BASE_URL/insurance/rest/updateVehicle"
const val createInvoiceEndPoint = "$BASE_URL/insurance/rest/createInvoice"
const val sendOTPEndPoint = "$BASE_URL/insurance/rest/paymentOtpSend"
const val otpVerifyAndCreateInvoiceEndPoint = "$BASE_URL/insurance/rest/otpVerifyAndCreateInvoice"
const val foreignIqamaSearch = "$BASE_URL/insurance/rest/foreignEqamaSearch/"
const val saudiHawiyahSearch = "$BASE_URL/insurance/rest/saudiHawiyahSearch/"
const val showInsuranceCodeName = "$BASE_URL/insurance/rest/showInsuranceCodeName"
const val changePwd = "$BASE_URL/insurance/rest/changePwd"
const val auth = "$BASE_URL/insurance/rest/auth"
const val verifyAuthOTP = "$BASE_URL/insurance/rest/auth"
const val checkAvailability = "$BASE_URL/insurance/rest/checkAvailability"
const val sendVerificationCodeAny = "$BASE_URL/insurance/rest/sendVerificationCodeAny"
const val verifyEmailCodeAny = "$BASE_URL/insurance/rest/verifyEmailCodeAny"
const val sendNationalIdOtpEndPoint = "$BASE_URL/insurance/rest/sendNationalIdOtp"
const val verifyNationalIdByOtpEndPoint = "$BASE_URL/insurance/rest/verifyNationalIdByOtp"
const val sendVerificationCodeSMSEndPoint = "$BASE_URL/insurance/rest/sendVerificationCodeSMS"
const val verifySMSCodeAnyEndPoint = "$BASE_URL/insurance/rest/verifySMSCodeAny"
const val signup = "$BASE_URL/insurance/rest/signup"
const val sendTempPass = "$BASE_URL/insurance/rest/sendTempPass"

fun getshowVehiclesByPolicyholderIdAndOwnerIdEndPoint(policyHolderID : Int, nationalID : String) = "$BASE_URL/insurance/rest/showVehiclesByPolicyholderIdAndOwnerId/$policyHolderID/$nationalID"
fun getVehiclesSequenceIdEndPoint(sequenceID : String, nationalID : String) = "$BASE_URL/insurance/rest/getVehiclesSequenceId/$sequenceID/$nationalID"
fun getShowDriverEndPoint(vehicleId : Int, policyHolderId : String) = "$BASE_URL/insurance/rest/showDriverByVehicleId/$vehicleId/$policyHolderId"
fun getShowDriverEndPoint(referenceNo : String) = "$BASE_URL/insurance/rest/updateDriverPercentage?referenceNo=${referenceNo}"
fun getQuotesEndPoint(referenceNo : String) = "$BASE_URL/insurance/rest/ica-get-quotesErrorFree/$referenceNo"
fun getChangeReferenceNumEndPoint(referenceNo : String, policyHolderId : Int) = "$BASE_URL/insurance/rest/changeReferenceNoOnQuotation/$referenceNo/$policyHolderId"
fun deleteDriverEndPoint(driverID : Int) = "$BASE_URL/insurance/rest/deleteDriver/$driverID"
