package hyper_pay

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.oppwa.mobile.connect.exception.PaymentError
import com.oppwa.mobile.connect.exception.PaymentException
import com.oppwa.mobile.connect.payment.CheckoutInfo
import com.oppwa.mobile.connect.payment.card.CardPaymentParams
import com.oppwa.mobile.connect.provider.Connect
import com.oppwa.mobile.connect.provider.ITransactionListener
import com.oppwa.mobile.connect.provider.OppPaymentProvider
import com.oppwa.mobile.connect.provider.ThreeDSWorkflowListener
import com.oppwa.mobile.connect.provider.Transaction
import com.oppwa.mobile.connect.provider.TransactionType
import com.oppwa.msa.MerchantServerApplication
import com.stevdza_san.todo.R
import preference.KMMContext

actual class HyperPay {

    private var checkoutId: String = ""
    private var cardHolder: String = ""
    private var cardNumber: String = ""
    private var cardExpiryMonth: String = ""
    private var cardExpiryYear: String = ""
    private var cardCVV: String = ""
    private var paymentType: String = "MADA"

    private var paymentProvider: OppPaymentProvider? = null
    private var resourcePath: String? = null

    private var context = KMMContext()
    private var onTransactionResult: ((Boolean) -> Unit)? = null

    init {
        paymentProvider = OppPaymentProvider(context, Connect.ProviderMode.TEST)
        /*paymentProvider?.setThreeDSWorkflowListener(object : ThreeDSWorkflowListener {
            override fun onThreeDSChallengeRequired(): Activity {
                return context as Activity
            }
        })*/
    }

    private val listener: ITransactionListener = object : ITransactionListener {

        override fun transactionCompleted(transaction: Transaction) {
            if (transaction.transactionType == TransactionType.SYNC) {
                // check the status of synchronous transaction
                requestPaymentStatus(resourcePath!!)
            } else {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW, Uri.parse(transaction.redirectUrl))
                )
            }
        }

        override fun transactionFailed(p0: Transaction, paymentError: PaymentError) {
            showErrorDialog(paymentError)
        }

        override fun paymentConfigRequestFailed(paymentError: PaymentError) {
            super.paymentConfigRequestFailed(paymentError)
            showErrorDialog(paymentError)
        }

        override fun paymentConfigRequestSucceeded(checkoutInfo: CheckoutInfo) {
            super.paymentConfigRequestSucceeded(checkoutInfo)
            // get the resource path from checkout info to request the payment status later
            resourcePath = checkoutInfo.resourcePath

            showConfirmationDialog(
                checkoutInfo.amount.toString(), checkoutInfo.currencyCode!!
            )
        }
    }

    actual fun setTransactionResultListener(onResult: (success: Boolean) -> Unit) {
        this.onTransactionResult = onResult
    }

    actual fun setCardInfo(
        cardHolder: String, cardNumber: String,
        cardExpiryMonth: String, cardExpiryYear: String,
        cardCVV: String, paymentType: String
    ) {
        this.cardHolder = cardHolder
        this.cardNumber = cardNumber
        this.cardExpiryMonth = cardExpiryMonth
        this.cardExpiryYear = cardExpiryYear
        this.cardCVV = cardCVV
        this.paymentType = paymentType
    }

    actual fun proceedPayment(checkoutId: String, ) {
        try { paymentProvider?.requestCheckoutInfo(checkoutId, listener) }
        catch (e: PaymentException) { showAlertDialog(e.message.orEmpty()) }
    }

    private fun requestPaymentStatus(resourcePath: String) {
        MerchantServerApplication.requestPaymentStatus(
            MerchantServerApplication.getDefaultAuthorization(),
            resourcePath
        ) { paymentStatusResponse, _ ->
            val isSuccessful = MerchantServerApplication.isSuccessful(paymentStatusResponse)
            onTransactionResult?.invoke(isSuccessful)
        }
    }

    private fun showConfirmationDialog(amount: String, currency: String) {
        AlertDialog.Builder(context)
            .setMessage("Payment of $amount $currency will be executed")
            .setPositiveButton("OK") { _, _ -> pay() }
            .setNegativeButton("Cancel", null)
            .setCancelable(false)
            .show()
    }

    private fun pay() {
        val paymentParams = CardPaymentParams(
            checkoutId, paymentType,
            cardNumber, cardHolder,
            cardExpiryMonth, "20$cardExpiryYear",
            cardCVV
        )

        paymentParams.shopperResultUrl = context.getString(R.string.custom_ui_callback_scheme) + "://callback"
        paymentProvider?.submitTransaction(Transaction(paymentParams), listener)
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(context)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .setCancelable(false)
            .show()
    }

    private fun showErrorDialog(paymentError: PaymentError) {
        showErrorDialog(paymentError.errorMessage)
    }

    private fun showErrorDialog(message: String) {
        showAlertDialog(message)
    }

}


/*

class HyperPay(
    val context: Context = Application(),
    val onTransactionResult: (success : Boolean) -> Unit
) {

    private var paymentProvider: OppPaymentProvider? = null
    private var resourcePath: String? = null

    private var checkoutId: String = ""
    private var cardHolder: String = ""
    private var cardNumber: String = ""
    private var cardExpiryMonth: String = ""
    private var cardExpiryYear: String = ""
    private var cardCVV: String = ""
    private var paymentType: String = "MADA"

    init {
        paymentProvider = OppPaymentProvider(context, Connect.ProviderMode.TEST)
        paymentProvider!!.setThreeDSWorkflowListener(object : ThreeDSWorkflowListener {
            override fun onThreeDSChallengeRequired(): Activity {
                return context as Activity
            }
        })
    }

    private val listener: ITransactionListener = object : ITransactionListener {

        override fun transactionCompleted(transaction: Transaction) {
            if (transaction.transactionType == TransactionType.SYNC) {
                // check the status of synchronous transaction
                requestPaymentStatus(resourcePath!!)
            } else {
                // open redirect url and wait for the final callback in the onNewIntent()
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW, Uri.parse(transaction.redirectUrl)
                    )
                )
            }
        }

        override fun transactionFailed(p0: Transaction, paymentError: PaymentError) {
            showErrorDialog(paymentError)
        }

        override fun paymentConfigRequestFailed(paymentError: PaymentError) {
            super.paymentConfigRequestFailed(paymentError)
            showErrorDialog(paymentError)
        }

        override fun paymentConfigRequestSucceeded(checkoutInfo: CheckoutInfo) {
            super.paymentConfigRequestSucceeded(checkoutInfo)
            // get the resource path from checkout info to request the payment status later
            resourcePath = checkoutInfo.resourcePath

            showConfirmationDialog(
                checkoutInfo.amount.toString(), checkoutInfo.currencyCode!!
            )
        }
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

            paymentProvider!!.requestCheckoutInfo(checkoutId, listener)
        } catch (e: PaymentException) {
            e.message?.let { showAlertDialog(it) }
        }
    }

    private fun pay(checkoutId: String) {
        try {
            val paymentParams = createPaymentParams(checkoutId)
            paymentParams.shopperResultUrl =
                context.getString(R.string.custom_ui_callback_scheme) + "://callback"
            val transaction = Transaction(paymentParams)

            paymentProvider!!.submitTransaction(transaction, listener)
        } catch (e: PaymentException) {
            showErrorDialog(e.error)
        }
    }

    private fun showConfirmationDialog(amount: String, currency: String) {
        androidx.appcompat.app.AlertDialog.Builder(context).setMessage(
            String.format(
                "Payment of $amount  $currency will be executed"
            )
        ).setPositiveButton("OK") { _, _ -> pay(checkoutId) }
            .setNegativeButton("Cancel") { _, _ -> {} }.setCancelable(false).show()
    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(context).setMessage(message).setPositiveButton("OK", null)
            .setCancelable(false).show()
    }

    private fun createPaymentParams(checkoutId: String): CardPaymentParams {
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


    private fun requestPaymentStatus(resourcePath: String) {

        MerchantServerApplication.requestPaymentStatus(
            MerchantServerApplication.getDefaultAuthorization(), resourcePath
        ) { paymentStatusResponse, _ ->
            onPaymentStatusReceived(paymentStatusResponse)
        }
    }


    private fun showErrorDialog(paymentError: PaymentError) {
        showErrorDialog(paymentError.errorMessage)
    }

    private fun showErrorDialog(message: String) {
        showAlertDialog(message)
    }

    private fun onPaymentStatusReceived(paymentStatusResponse: PaymentStatusResponse?) {

        val message =
            if (MerchantServerApplication.isSuccessful(paymentStatusResponse)) context.getString(R.string.message_successful_payment) else context.getString(
                R.string.message_unsuccessful_payment
            )

        showAlertDialog(message)

        onTransactionResult(true)
    }

}*/
