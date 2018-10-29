package pl.altkom.asc.lab.lambda.printing

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import pl.altkom.asc.lab.lambda.printing.printing.InvoicePrintRequest
import pl.altkom.asc.lab.lambda.printing.printing.InvoicePrinter
import pl.altkom.asc.lab.lambda.printing.printing.InvoiceStore
import java.util.function.Function

class PrintInvoiceFunction : Function<SQSEvent, String> {

    private val invoicePrinter = InvoicePrinter()
    private val invoiceStore = InvoiceStore()

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