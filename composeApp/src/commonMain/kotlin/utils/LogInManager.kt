package utils

import SHARED_PREFERENCE
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object LogInManager {
    var loggedIn: Boolean by mutableStateOf(false)

    fun setLoggedInValue(isLoggedIn: Boolean) {
        loggedIn = isLoggedIn

        SHARED_PREFERENCE.put(
            AppConstants.SharedPreferenceKeys.LOGGED_IN,
            loggedIn
        )
    }
}