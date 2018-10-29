package pl.altkom.asc.lab.lambda.invoice

import com.amazonaws.services.lambda.runtime.events.SQSEvent
import org.slf4j.LoggerFactory
import pl.altkom.asc.lab.lambda.invoice.billing.BillingItem
import pl.altkom.asc.lab.lambda.invoice.billing.BillingItemRepository
import pl.altkom.asc.lab.lambda.invoice.invoicing.InvoiceGenerationRequest
import pl.altkom.asc.lab.lambda.invoice.invoicing.InvoiceGenerator
import pl.altkom.asc.lab.lambda.invoice.notification.InvoiceNotificationRequest
import pl.altkom.asc.lab.lambda.invoice.notification.InvoiceNotificationRequestPublisher
import pl.altkom.asc.lab.lambda.invoice.printing.InvoicePrintRequest
import pl.altkom.asc.lab.lambda.invoice.printing.InvoicePrintRequestPublisher
import java.util.function.Function

class GenerateInvoiceFunction(
        val billingItemRepository: BillingItemRepository = BillingItemRepository(),
        val invoiceGenerator: InvoiceGenerator = InvoiceGenerator(),
        val invoicePrintRequestPublisher: InvoicePrintRequestPublisher = InvoicePrintRequestPublisher(),
        val invoiceNotifyRequestPublisher: InvoiceNotificationRequestPublisher = InvoiceNotificationRequestPublisher()
) : Function<SQSEvent, String> {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun apply(event: SQSEvent): String {

        event.records.forEach(this::processMessage)

        return "OK"
    }

    private fun processMessage(message: SQSEvent.SQSMessage) {
        val request = InvoiceGenerationRequest.fromJSON(message.body)
        val items = billingItemRepository.findForInvoice(request)

        items.map(BillingItem::toString).forEach(log::info)

        val invoice = invoiceGenerator.generate(request, items)

        invoicePrintRequestPublisher.publish(InvoicePrintRequest(invoice))
        invoiceNotifyRequestPublisher.publish(InvoiceNotificationRequest(invoice))
    }
}