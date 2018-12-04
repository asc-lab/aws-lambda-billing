package pl.altkom.asc.lab.lambda.notification.twilio

import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
import org.slf4j.LoggerFactory
import pl.altkom.asc.lab.lambda.notification.notification.InvoiceNotificationRequest
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SmsSender @Inject constructor(){

    val accountSid: String = System.getenv("TWILIO_ACCOUNT_SID") ?: ""
    val authToken: String = System.getenv("TWILIO_AUTH_TOKEN") ?: ""

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    init {
        val start = System.currentTimeMillis()
        Twilio.init(accountSid, authToken)
        log.info("Twilio initialized in ${System.currentTimeMillis() - start}ms")
    }


    fun sendSms(request: InvoiceNotificationRequest) {
        val start = System.currentTimeMillis()

        val message = Message.creator(
                PhoneNumber("+15005550006"),
                PhoneNumber("+15005550006"),
                "You have new invoice ${request.invoice?.invoiceNumber} for ${request.invoice?.totalCost}.")
                .create()

        log.info("Twilio message sent with status:${message.status}")
        log.info("Twilio message sent in ${System.currentTimeMillis() - start}ms")
    }
}