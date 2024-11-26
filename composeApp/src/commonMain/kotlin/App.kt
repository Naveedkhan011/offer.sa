import KoinInitializer.initializeKoin
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.LayoutDirection
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.transitions.SlideTransition
import data.MongoDB
import kotlinx.coroutines.delay
import models.enums.ToastType
import org.koin.core.context.startKoin
import org.koin.dsl.module
import preference.KMMPreference
import presentation.components.CustomToast
import presentation.components.FullScreenLoadingDialog
import presentation.screen.forgot_password.ForgotPasswordViewModel
import presentation.screen.login.LoginScreen
import presentation.screen.login.SignInViewModel
import presentation.screen.main_screen.HomeViewModel
import presentation.screen.quotes_screen.GetQuotes
import presentation.screen.quotes_screen.QuotesViewModel
import presentation.screen.signup_screen.SignupViewModel
import presentation.screen.splash.Splash
import presentation.screen.task.TaskViewModel
import utils.AppConstants
import utils.LogInManager
import utils.language.language_manager.LanguageManager
import utils.language.languages.ArabicStrings


val lightRedColor = Color(color = 0xFFF57D88)
val darkRedColor = Color(color = 0xFF77000B)
lateinit var SHARED_PREFERENCE: KMMPreference
val layoutDirection =
    if (LanguageManager.currentStrings == ArabicStrings) LayoutDirection.Rtl else LayoutDirection.Ltr

data class Loader(
    var isToastVisible: Boolean = false,
    var messageToDisplay: String = "",
    var type: ToastType = ToastType.INFO
)


lateinit var navigator: Navigator

var loaderData by mutableStateOf(Loader())
var isLoading by mutableStateOf(false)

@Composable
fun App(sharedPreference: KMMPreference) {
    SHARED_PREFERENCE = sharedPreference
    LogInManager.setLoggedInValue(
        SHARED_PREFERENCE.getBool(
            AppConstants.SharedPreferenceKeys.LOGGED_IN,
            false
        )
    )

    initializeKoin() // Ensure this only runs once

    val lightColors = lightColorScheme(
        primary = lightRedColor,
        onPrimary = darkRedColor,
        primaryContainer = lightRedColor,
        onPrimaryContainer = darkRedColor
    )
    val darkColors = darkColorScheme(
        primary = lightRedColor,
        onPrimary = darkRedColor,
        primaryContainer = lightRedColor,
        onPrimaryContainer = darkRedColor
    )
    val colors by mutableStateOf(
        if (isSystemInDarkTheme()) darkColors else lightColors
    )
    MaterialTheme(colorScheme = colors) {

        Navigator(Splash()) {
            SlideTransition(it)
        }

        FullScreenLoadingDialog(isLoading)

        CustomToast(
            loader = loaderData,
            onDismiss = {
                loaderData = Loader(isToastVisible = false, messageToDisplay = "")
            }
        )
    }

    // This is fine to call here since it's not composable
    val language = SHARED_PREFERENCE.getString(AppConstants.SharedPreferenceKeys.LANGUAGE)
    language?.let { LanguageManager.switchLanguage(it) }
}

val mongoModule = module {
    single { MongoDB() }
    factory { HomeViewModel(get()) }
    factory { TaskViewModel(get()) }
    single { SignInViewModel() }
    single { SignupViewModel() }
    single { QuotesViewModel() }
    single { ForgotPasswordViewModel() }
}

@Composable
fun showToastUsingLaunchEffect(messageToDisplay: String, type: ToastType = ToastType.SUCCESS) {
    LaunchedEffect(Unit) {
        loaderData = Loader(
            isToastVisible = true,
            messageToDisplay = messageToDisplay,
            type = type
        )
    }
}

@Composable
fun showToast(messageToDisplay: String, type: ToastType = ToastType.SUCCESS) {
    loaderData = Loader(
        isToastVisible = true,
        messageToDisplay = messageToDisplay,
        type = type
    )
}


object KoinInitializer {
    var isInitialized = false
    fun initializeKoin() {
        if (!isInitialized) {
            startKoin {
                modules(mongoModule)
            }
            isInitialized = true
        }
    }
}
