package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class VehicleInfo(
    @SerialName("bodyTypeCode")
    val bodyTypeCode: String? = null,
    @SerialName("bodyTypeDescAr")
    val bodyTypeDescAr: String? = null,
    @SerialName("capacity")
    val capacity: Int? = null,
    @SerialName("majorColor")
    val majorColor: String? = null,
    @SerialName("make")
    val make: String? = null,
    @SerialName("makeCode")
    val makeCode: String? = null,
    @SerialName("model")
    val model: String? = null,
    @SerialName("modelCode")
    val modelCode: String? = null,
    @SerialName("modelYear")
    val modelYear: String? = null,
    @SerialName("plateNumber")
    val plateNumber: String? = null,
    @SerialName("plateReturnIndex")
    val plateReturnIndex: String? = null,
    @SerialName("plateText1")
    val plateText1: String? = null,
    @SerialName("plateText2")
    val plateText2: String? = null,
    @SerialName("plateText3")
    val plateText3: String? = null,
    @SerialName("regTypeCode")
    val regTypeCode: String? = null,
    @SerialName("regTypeDescAr")
    val regTypeDescAr: String? = null,
    @SerialName("registrationExpiryDate")
    val registrationExpiryDate: String = "",
    @SerialName("registrationLocationCode")
    val registrationLocationCode: String? = null,
    @SerialName("registrationLocationDescAr")
    val registrationLocationDescAr: String? = null,
    @SerialName("vehicleClassCode")
    val vehicleClassCode: Int? = null,
    @SerialName("vehicleClassDescAr")
    val vehicleClassDescAr: String? = null,
    @SerialName("vehicleIDNumber")
    val vehicleIDNumber: String? = null,
    @SerialName("vehicleList")
    val vehicleList: VehicleList? = null,
    @SerialName("weight")
    val weight: Int? = null
)