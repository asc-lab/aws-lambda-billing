package pl.altkom.asc.lab.lambda.notification

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import com.fasterxml.jackson.annotation.JsonProperty
import pl.altkom.asc.lab.lambda.notification.notification.InvoiceNotificationRequest
import pl.altkom.asc.lab.lambda.notification.sendgrid.EmailSender
import pl.altkom.asc.lab.lambda.notification.twilio.SmsSender
import java.util.function.Function
import javax.inject.Inject

class NotifyInvoiceFunction @Inject constructor(
        val emailSender: EmailSender,
        val smsSender: SmsSender
) : Function<SQSEvent, String> {

    override fun apply(event: SQSEvent): String {

        event.records!!.forEach(this::processMessage)
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

data class Event(
        @JsonProperty(value = "Records")
        var records: Array<EventRecord>? = null
)

data class EventRecord(var body: String)