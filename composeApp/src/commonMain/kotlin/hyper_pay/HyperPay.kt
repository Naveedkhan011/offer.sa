package hyper_pay

expect class HyperPay constructor(){

    fun setCardInfo(
        cardHolder: String,
        cardNumber: String,
        cardExpiryMonth: String,
        cardExpiryYear: String,
        cardCVV: String,
        paymentType: String
    )

    fun proceedPayment(
        checkoutId: String
    )

    fun setTransactionResultListener(onResult: (success: Boolean) -> Unit)
}
