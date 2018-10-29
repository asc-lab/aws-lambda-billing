package pl.altkom.asc.lab.lambda.invoice.notification

import pl.altkom.asc.lab.lambda.invoice.invoicing.Invoice

data class InvoiceNotificationRequest(val invoice: Invoice)