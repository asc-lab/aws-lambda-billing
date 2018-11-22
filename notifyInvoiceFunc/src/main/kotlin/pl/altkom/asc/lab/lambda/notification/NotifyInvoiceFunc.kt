package pl.altkom.asc.lab.lambda.notification

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.function.FunctionBean
import pl.altkom.asc.lab.lambda.notification.notification.InvoiceNotificationRequest
import pl.altkom.asc.lab.lambda.notification.sendgrid.EmailSender
import pl.altkom.asc.lab.lambda.notification.twilio.SmsSender
import java.util.function.Function

@FunctionBean("notify-invoice-func")
class NotifyInvoiceFunction(
        val emailSender: EmailSender,
        val smsSender: SmsSender
) : Function<Event, String> {

    override fun apply(event: Event): String {

        event.records.forEach(this::processMessage)

        return "OK"
    }

    private fun processMessage(message: EventRecord) {
        val request = InvoiceNotificationRequest.fromJSON(message.body)

        if (request.invoice != null) {
            emailSender.send(request)
            smsSender.sendSms(request)
        }
    }

}

data class Event(
        @JsonProperty(value = "Records")
        var records: Array<EventRecord>
)

data class EventRecord(var body: String)