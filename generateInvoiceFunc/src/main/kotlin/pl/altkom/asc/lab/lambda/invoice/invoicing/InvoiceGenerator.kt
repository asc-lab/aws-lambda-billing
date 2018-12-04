package pl.altkom.asc.lab.lambda.invoice.invoicing

import pl.altkom.asc.lab.lambda.invoice.billing.BillingItem
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InvoiceGenerator @Inject constructor() {

    fun generate(request: InvoiceGenerationRequest, items: List<BillingItem>): Invoice {
        val invoice = Invoice.create(request.customerCode, request.year, request.month)
        invoice.billItems(items)
        return invoice
    }

}