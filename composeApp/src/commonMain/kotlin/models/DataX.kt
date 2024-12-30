package models

import kotlinx.serialization.Serializable

@Serializable
data class DataX(
    val emailAvailable: Boolean?,
    val mobileAvailable: Boolean?,
    val nationalIdAvailable: Boolean?
)