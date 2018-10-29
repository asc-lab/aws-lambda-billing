package pl.altkom.asc.lab.lambda.notification

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import pl.altkom.asc.lab.lambda.notification.notification.InvoiceNotificationRequest
import pl.altkom.asc.lab.lambda.notification.sendgrid.EmailSender
import pl.altkom.asc.lab.lambda.notification.twilio.SmsSender
import java.util.function.Function

class NotifyInvoiceFunction(
        val emailSender: EmailSender = EmailSender(),
        val smsSender: SmsSender = SmsSender()
) : Function<SQSEvent, String> {

    override fun apply(event: SQSEvent): String {

        event.records.forEach(this::processMessage)

        return "OK"
    }

    private fun processMessage(message: SQSEvent.SQSMessage) {
        val request = InvoiceNotificationRequest.fromJSON(message.body)

        if (request.invoice != null) {
            emailSender.send(request)
            smsSender.sendSms(request)
        }
    }

}