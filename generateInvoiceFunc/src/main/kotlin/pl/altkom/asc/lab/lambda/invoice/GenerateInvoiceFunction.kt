package pl.altkom.asc.lab.lambda.invoice

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import com.amazonaws.util.json.Jackson
import com.fasterxml.jackson.annotation.JsonProperty
import pl.altkom.asc.lab.lambda.invoice.billing.BillingItemRepository
import pl.altkom.asc.lab.lambda.invoice.invoicing.InvoiceGenerationRequest
import pl.altkom.asc.lab.lambda.invoice.invoicing.InvoiceGenerator
import pl.altkom.asc.lab.lambda.invoice.notification.InvoiceNotificationRequest
import pl.altkom.asc.lab.lambda.invoice.notification.InvoiceNotificationRequestPublisher
import pl.altkom.asc.lab.lambda.invoice.printing.InvoicePrintRequest
import pl.altkom.asc.lab.lambda.invoice.printing.InvoicePrintRequestPublisher
import java.util.function.Function
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenerateInvoiceFunction @Inject constructor(
        val billingItemRepository: BillingItemRepository,
        val invoiceGenerator: InvoiceGenerator,
        val invoicePrintRequestPublisher: InvoicePrintRequestPublisher,
        val invoiceNotifyRequestPublisher: InvoiceNotificationRequestPublisher
) : Function<SQSEvent, String> {

    private val log = Logger.getLogger(this.javaClass.name)!!

    override fun apply(event: SQSEvent): String {

        log.info(Jackson.toJsonPrettyString(event))

        event.records!!.map { it.body }.forEach(this::processMessage)

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

class Event {

    @JsonProperty(value = "Records")
    var records: Array<EventRecord>? = null
        public get
        @JsonProperty(value = "Records")
        public set
}

data class EventRecord(var body: String)