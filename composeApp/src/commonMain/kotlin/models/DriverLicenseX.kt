package models

import kotlinx.serialization.Serializable

@Serializable
data class DriverLicenseX(
    var active: Boolean? = false,
    var createdBy: String? = String(),
    var createdDate: String? = "",
    var driverLicenseExpiryDate: String? = "",
    var driverLicenseTypeCode: Int? = 0,
    var id: Int? = 0,
    var lastModifiedBy: String? = String(),
    var lastModifiedDate: String? = "",
    var licenseCountryCode: Int? = 0,
    var licenseNumberYears: Int? = 0,
    var referenceNo: String? = "",
    var version: Int? = 0
)