package hyper_pay

actual class HyperPay actual constructor(){

    actual fun proceedPayment(checkoutId: String) {}

    actual fun setTransactionResultListener(onResult: (success: Boolean) -> Unit) {}

    actual fun setCardInfo(
        cardHolder: String,
        cardNumber: String,
        cardExpiryMonth: String,
        cardExpiryYear: String,
        cardCVV: String,
        paymentType: String
    ) {}

}