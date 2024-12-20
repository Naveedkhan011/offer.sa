package models.VehicleListModels


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DriverLicense(
    @SerialName("active")
    val active: Boolean? = null,
    @SerialName("createdBy")
    val createdBy: String? = null,
    @SerialName("createdDate")
    val createdDate: String? = null,
    @SerialName("driverLicenseExpiryDate")
    val driverLicenseExpiryDate: String? = null,
    @SerialName("driverLicenseTypeCode")
    val driverLicenseTypeCode: Int? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("lastModifiedBy")
    val lastModifiedBy: String? = null,
    @SerialName("lastModifiedDate")
    val lastModifiedDate: String? = null,
    @SerialName("licenseCountryCode")
    val licenseCountryCode: Int? = null,
    @SerialName("licenseNumberYears")
    val licenseNumberYears: Int? = null,
    @SerialName("referenceNo")
    val referenceNo: String? = null,
    @SerialName("version")
    val version: Int? = null
)