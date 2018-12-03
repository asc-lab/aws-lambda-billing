package pl.altkom.asc.lab.lambda.billing.invoicing

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.util.json.Jackson
import java.util.logging.Logger
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class InvoiceGenerationRequestPublisher @Inject constructor(private val sqs: AmazonSQS) {

    private val log = Logger.getLogger(this.javaClass.name)!!

    private val queueName: String = System.getenv("INVOICE_GENERATION_REQUEST_QUEUE") ?: ""
    private val queueUrl = sqs.getQueueUrl(queueName).queueUrl

    fun publish(invoiceGenerationRequest: InvoiceGenerationRequest) {
        val time = measureTimeMillis {
            sqs.sendMessage(SendMessageRequest(queueUrl, Jackson.toJsonPrettyString(invoiceGenerationRequest)))
        }

        log.info("InvoiceGenerationRequest published in $time ms")
    }

}