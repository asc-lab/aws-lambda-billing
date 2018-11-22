package pl.altkom.asc.lab.lambda.printing

import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.function.FunctionBean
import pl.altkom.asc.lab.lambda.printing.printing.InvoicePrintRequest
import pl.altkom.asc.lab.lambda.printing.printing.InvoicePrinter
import pl.altkom.asc.lab.lambda.printing.printing.InvoiceStore
import java.util.function.Function

@FunctionBean("print-invoice-func")
class PrintInvoiceFunction(
        private val invoicePrinter: InvoicePrinter,
        private val invoiceStore: InvoiceStore
) : Function<Event, String> {

    override fun apply(event: Event): String {

        event.records.forEach(this::processMessage)

        return "OK"
    }

    private fun processMessage(message: EventRecord) {
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