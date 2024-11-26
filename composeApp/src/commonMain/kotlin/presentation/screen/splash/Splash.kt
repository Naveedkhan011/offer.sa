package presentation.screen.splash

import SHARED_PREFERENCE
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.delay
import navigator
import offer.composeapp.generated.resources.Res
import offer.composeapp.generated.resources.ic_google
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.screen.main_screen.MyHomeScreen
import presentation.screen.onboarding_screen.OnboardingScreen

class Splash : Screen {

    @OptIn(ExperimentalResourceApi::class)
    @Composable
    override fun Content() {
        navigator = LocalNavigator.currentOrThrow

        //val isAlreadyViewed = SHARED_PREFERENCE.getBool("isAlreadyViewed", false)
        val isAlreadyViewed = false

        LaunchedEffect(Unit) {
            delay(5000) // 5-second delay
            navigator.replace(if (isAlreadyViewed) MyHomeScreen() else OnboardingScreen()) // Navigate to the onboarding screen
        }


        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(Res.drawable.ic_google),
                contentDescription = "Splash Logo",
                modifier = Modifier.size(150.dp)
            )
        }


    }

}