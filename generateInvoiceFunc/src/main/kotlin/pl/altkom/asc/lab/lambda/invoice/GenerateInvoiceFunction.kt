package pl.altkom.asc.lab.lambda.invoice

import com.amazonaws.util.json.Jackson
import com.fasterxml.jackson.annotation.JsonProperty
import io.micronaut.function.FunctionBean
import org.slf4j.LoggerFactory
import pl.altkom.asc.lab.lambda.invoice.billing.BillingItemRepository
import pl.altkom.asc.lab.lambda.invoice.invoicing.InvoiceGenerationRequest
import pl.altkom.asc.lab.lambda.invoice.invoicing.InvoiceGenerator
import pl.altkom.asc.lab.lambda.invoice.notification.InvoiceNotificationRequest
import pl.altkom.asc.lab.lambda.invoice.notification.InvoiceNotificationRequestPublisher
import pl.altkom.asc.lab.lambda.invoice.printing.InvoicePrintRequest
import pl.altkom.asc.lab.lambda.invoice.printing.InvoicePrintRequestPublisher
import java.util.function.Function

@FunctionBean("generate-invoice-func")
class GenerateInvoiceFunction(
        val billingItemRepository: BillingItemRepository,
        val invoiceGenerator: InvoiceGenerator,
        val invoicePrintRequestPublisher: InvoicePrintRequestPublisher,
        val invoiceNotifyRequestPublisher: InvoiceNotificationRequestPublisher
) : Function<Event, String> {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun apply(event: Event): String {

        log.info(Jackson.toJsonPrettyString(event))

        event.records.map { it.body }.forEach(this::processMessage)

        return "OK"
    }

    private fun processMessage(body: String) {
        val request = InvoiceGenerationRequest.fromJSON(body)
        val items = billingItemRepository.findForInvoice(request)

        val invoice = invoiceGenerator.generate(request, items)

        invoicePrintRequestPublisher.publish(InvoicePrintRequest(invoice))
        invoiceNotifyRequestPublisher.publish(InvoiceNotificationRequest(invoice))
    }
}

data class Event(
        @JsonProperty(value = "Records")
        var records: Array<EventRecord>
)

data class EventRecord(var body: String)