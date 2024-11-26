import androidx.compose.ui.window.ComposeUIViewController
import preference.KMMPreference
import preference.IOSContext

fun MainViewController() = ComposeUIViewController {
    val sharedPreference = KMMPreference(IOSContext.preferences)
    App(sharedPreference)
}