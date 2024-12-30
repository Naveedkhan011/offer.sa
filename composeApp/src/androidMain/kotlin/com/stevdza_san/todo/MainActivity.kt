package com.stevdza_san.todo

import App
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.oppwa.mobile.connect.exception.PaymentError
import com.oppwa.mobile.connect.exception.PaymentException
import com.oppwa.mobile.connect.payment.CheckoutInfo
import com.oppwa.mobile.connect.payment.card.CardPaymentParams
import com.oppwa.mobile.connect.provider.Connect
import com.oppwa.mobile.connect.provider.ITransactionListener
import com.oppwa.mobile.connect.provider.OppPaymentProvider
import com.oppwa.mobile.connect.provider.Transaction
import com.oppwa.mobile.connect.provider.TransactionType
import com.oppwa.msa.MerchantServerApplication
import com.oppwa.msa.model.response.PaymentStatusResponse
import preference.KMMPreference

class MainActivity : ComponentActivity() {
    private var paymentProvider: OppPaymentProvider? = null
    private var resourcePath: String? = null
    private var checkoutId: String = ""

    private var cardHolder: String = ""
    private var cardNumber: String = ""
    private var cardExpiryMonth: String = ""
    private var cardExpiryYear: String = ""
    private var cardCVV: String = ""
    private var paymentType: String = "MADA"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT
            )
        )*/

        val pref = KMMPreference(application)

        setContent {
            App(pref)
        }

        paymentProvider = OppPaymentProvider(this, Connect.ProviderMode.TEST)
        paymentProvider!!.setThreeDSWorkflowListener { this }

    }

    override fun getOnBackInvokedDispatcher(): OnBackInvokedDispatcher {
        return super.getOnBackInvokedDispatcher()
    }

    private fun proceedPayment(
        checkoutId: String,
        cardHolder: String,
        cardNumber: String,
        cardExpiryMonth: String,
        cardExpiryYear: String,
        cardCVV: String,
        paymentType: String
    ) {
        try {
            this.checkoutId = checkoutId
            this.cardHolder = cardHolder
            this.cardNumber = cardNumber
            this.cardExpiryMonth = cardExpiryMonth
            this.cardExpiryYear = cardExpiryYear
            this.cardCVV = cardCVV
            this.paymentType = paymentType

            paymentProvider!!.requestCheckoutInfo(checkoutId, object : ITransactionListener {

                override fun transactionCompleted(transaction: Transaction) {
                    if (transaction.transactionType == TransactionType.SYNC) {
                        // check the status of synchronous transaction
                        requestPaymentStatus(resourcePath!!)
                    } else {
                        // open redirect url and wait for the final callback in the onNewIntent()
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(transaction.redirectUrl)
                            )
                        )
                    }
                }

                override fun transactionFailed(transaction: Transaction, paymentError: PaymentError) {
                    showErrorDialog(paymentError)
                }

                override fun paymentConfigRequestSucceeded(checkoutInfo: CheckoutInfo) {
                    super.paymentConfigRequestSucceeded(checkoutInfo)
                    // get the resource path from checkout info to request the payment status later
                    resourcePath = checkoutInfo.resourcePath

                    runOnUiThread {
                        showConfirmationDialog(
                            checkoutInfo.amount.toString(),
                            checkoutInfo.currencyCode!!
                        )
                    }

                }
            })
        } catch (e: PaymentException) {
            e.message?.let { showAlertDialog(it) }
        }
    }

    private fun showConfirmationDialog(amount: String, currency: String) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setMessage(
                String.format(
                    "Payment of $amount  $currency will be executed"
                )
            )
            .setPositiveButton("OK") { _, _ -> pay(checkoutId) }
            .setNegativeButton("Cancel") { _, _ -> {} }
            .setCancelable(false)
            .show()
    }

    protected fun showAlertDialog(message: String) {
        AlertDialog.Builder(this)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .setCancelable(false)
            .show()
    }

    private fun pay(checkoutId: String) {
        try {
            val paymentParams = createPaymentParams(checkoutId)
            paymentParams.shopperResultUrl =
                getString(R.string.custom_ui_callback_scheme) + "://callback"
            val transaction = Transaction(paymentParams)

            paymentProvider!!.submitTransaction(transaction, object : ITransactionListener {

                override fun transactionCompleted(transaction: Transaction) {
                    if (transaction.transactionType == TransactionType.SYNC) {
                        // check the status of synchronous transaction
                        requestPaymentStatus(resourcePath!!)
                    } else {
                        // open redirect url and wait for the final callback in the onNewIntent()
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(transaction.redirectUrl)
                            )
                        )
                    }
                }

                override fun transactionFailed(p0: Transaction, paymentError: PaymentError) {
                    showErrorDialog(paymentError)
                }

            })
        } catch (e: PaymentException) {
            showErrorDialog(e.error)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)

        // check of the intent contains the callback scheme
        if (intent.scheme == getString(R.string.custom_ui_callback_scheme)) {
            requestPaymentStatus(resourcePath!!)
        }
    }

    private fun showErrorDialog(paymentError: PaymentError) {
        runOnUiThread { showErrorDialog(paymentError.errorMessage) }
    }

    private fun showErrorDialog(message: String) {
        runOnUiThread { showAlertDialog(message) }
    }

    private fun createPaymentParams(checkoutId: String): CardPaymentParams {
        val cardHolder = ""
        val cardNumber = ""
        val cardExpiryMonth = ""
        val cardExpiryYear = ""
        val cardCVV = ""

        return CardPaymentParams(
            checkoutId,
            paymentType,
            cardNumber,
            cardHolder,
            cardExpiryMonth,
            "20$cardExpiryYear",
            cardCVV
        )
    }


    protected fun requestPaymentStatus(resourcePath: String) {

        MerchantServerApplication.requestPaymentStatus(
            MerchantServerApplication.getDefaultAuthorization(),
            resourcePath
        ) { paymentStatusResponse, _ ->
            runOnUiThread {
                onPaymentStatusReceived(paymentStatusResponse)
            }
        }
    }

    private fun onPaymentStatusReceived(paymentStatusResponse: PaymentStatusResponse?) {

        val message = if (MerchantServerApplication.isSuccessful(paymentStatusResponse))
            getString(R.string.message_successful_payment) else
            getString(R.string.message_unsuccessful_payment)

        showAlertDialog(message)
    }
}
