package pl.altkom.asc.lab.lambda.printing

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import com.fasterxml.jackson.annotation.JsonProperty
import pl.altkom.asc.lab.lambda.printing.printing.InvoicePrintRequest
import pl.altkom.asc.lab.lambda.printing.printing.InvoicePrinter
import pl.altkom.asc.lab.lambda.printing.printing.InvoiceStore
import java.util.function.Function
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrintInvoiceFunction @Inject constructor (
        private val invoicePrinter: InvoicePrinter,
        private val invoiceStore: InvoiceStore
) : Function<SQSEvent, String> {

    override fun apply(event: SQSEvent): String {

        event.records.forEach(this::processMessage)

        return "OK"
    }

    private fun processMessage(message: SQSEvent.SQSMessage) {
        val request = InvoicePrintRequest.fromJSON(message.body)

        if (request.invoice != null) {
            val content = invoicePrinter.print(request.invoice)
            invoiceStore.store(content, request.invoice.invoiceNumber.replace('/', '_') + ".pdf")
        }
    }
}

data class Event(
        @JsonProperty(value = "Records")
        var records: Array<EventRecord>
)

data class EventRecord(var body: String)