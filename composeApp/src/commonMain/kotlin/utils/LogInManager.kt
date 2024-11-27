package utils

import SHARED_PREFERENCE
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.json.Json
import models.LoggedUser
import kotlinx.serialization.decodeFromString

object LogInManager {
    var loggedIn: Boolean by mutableStateOf(false)

    fun setLoggedInValue(isLoggedIn: Boolean) {
        loggedIn = isLoggedIn

        SHARED_PREFERENCE.put(
            AppConstants.SharedPreferenceKeys.LOGGED_IN,
            loggedIn
        )
    }


    fun getLoggedInUser(): LoggedUser? {
        val jsonString = SHARED_PREFERENCE.getString(AppConstants.SharedPreferenceKeys.LOGGED_IN_USER)

        // Check if jsonString is null or empty
        return if (!jsonString.isNullOrEmpty()) {
            try {
                // Deserialize the JSON string into a LoggedUser object
                Json.decodeFromString<LoggedUser>(jsonString)
            } catch (e: Exception) {
                // Log the error if deserialization fails
                println("Error deserializing user: ${e.message}")
                null
            }
        } else {
            // Return null if jsonString is empty or null
            null
        }
    }

}