package presentation.screen.payment

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class PaymentScreen : Screen {

    @Composable
    override fun Content() {

        Text(
            text = "Payment in progress",
            modifier = Modifier.fillMaxSize()
        )

    }
}