package pl.altkom.asc.lab.lambda.billing.invoicing

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.util.json.Jackson

class InvoiceGenerationRequestPublisher(private val sqs: AmazonSQS) {

    private val queueName: String = System.getenv("INVOICE_GENERATION_REQUEST_QUEUE") ?: ""
    private val queueUrl = sqs.getQueueUrl(queueName).queueUrl

    fun publish(invoiceGenerationRequest: InvoiceGenerationRequest) {
        sqs.sendMessage(SendMessageRequest(queueUrl, Jackson.toJsonPrettyString(invoiceGenerationRequest)))
    }

}