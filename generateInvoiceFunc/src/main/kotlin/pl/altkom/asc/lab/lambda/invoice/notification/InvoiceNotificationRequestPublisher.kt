package pl.altkom.asc.lab.lambda.invoice.notification

import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.util.json.Jackson

class InvoiceNotificationRequestPublisher {

    private val sqs = AmazonSQSClientBuilder.defaultClient()
    private val queueName: String = System.getenv("INVOICE_NOTIFY_REQUEST_QUEUE") ?: ""
    private val queueUrl = sqs.getQueueUrl(queueName).queueUrl

    fun publish(invoicePrintRequest: InvoiceNotificationRequest) {
        sqs.sendMessage(SendMessageRequest(queueUrl, Jackson.toJsonPrettyString(invoicePrintRequest)))
    }
}