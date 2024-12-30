package presentation.screen.payment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import hyper_pay.HyperPay
import io.ktor.util.pipeline.StackWalkingFailedFrame.context
import navigator

class PaymentScreen : Screen {
    private val hyperPay = HyperPay()

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {


        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Top App Bar with Back Button
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = { Text("Payment") },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Back"
                        )
                    }
                }
            )

            Column {

                Spacer(modifier = Modifier.height(16.dp))
                hyperPay.setTransactionResultListener {success ->
                    if (success){
                        println("success")
                    }else{
                        println("error")
                    }
                }

                hyperPay.setCardInfo(
                    cardHolder = "John Doe",
                    cardNumber = "4111111111111111",
                    cardExpiryMonth = "12",
                    cardExpiryYear = "25",
                    cardCVV = "123",
                    paymentType = "MADA"
                )

                Button(modifier = Modifier.fillMaxSize(), onClick = {
                    hyperPay.proceedPayment(
                        checkoutId = "EE6B3914A416E74224A1F2A678CB52EA.uat01-vm-tx01"
                    )
                }){
                    Text("Pay")
                }
            }
        }

    }
}