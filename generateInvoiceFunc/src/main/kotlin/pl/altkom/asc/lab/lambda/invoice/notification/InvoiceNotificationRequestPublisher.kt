package pl.altkom.asc.lab.lambda.invoice.notification

import pl.altkom.asc.lab.lambda.billing.aws.SQSPublisher

@SQSPublisher("\${INVOICE_NOTIFY_REQUEST_QUEUE:}")
interface InvoiceNotificationRequestPublisher {

    fun publish(invoicePrintRequest: InvoiceNotificationRequest)
}