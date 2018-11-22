package pl.altkom.asc.lab.lambda.invoice.printing

import pl.altkom.asc.lab.lambda.billing.aws.SQSPublisher

@SQSPublisher("\${INVOICE_PRINT_REQUEST_QUEUE:}")
interface InvoicePrintRequestPublisher {

    fun publish(invoicePrintRequest: InvoicePrintRequest)
}