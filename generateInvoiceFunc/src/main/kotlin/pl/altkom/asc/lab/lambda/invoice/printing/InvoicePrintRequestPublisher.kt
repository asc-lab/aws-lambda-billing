package pl.altkom.asc.lab.lambda.invoice.printing

import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.util.json.Jackson
import javax.inject.Singleton

@Singleton
class InvoicePrintRequestPublisher {

    private val sqs = AmazonSQSClientBuilder.defaultClient()
    private val queueName: String = System.getenv("INVOICE_PRINT_REQUEST_QUEUE") ?: ""
    private val queueUrl = sqs.getQueueUrl(queueName).queueUrl

    fun publish(invoicePrintRequest: InvoicePrintRequest) {
        sqs.sendMessage(SendMessageRequest(queueUrl, Jackson.toJsonPrettyString(invoicePrintRequest)))
    }
}