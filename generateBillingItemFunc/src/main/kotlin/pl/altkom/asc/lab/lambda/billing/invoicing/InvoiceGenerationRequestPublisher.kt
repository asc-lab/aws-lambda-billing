package pl.altkom.asc.lab.lambda.billing.invoicing

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.util.json.Jackson
import org.slf4j.LoggerFactory
import javax.inject.Singleton
import kotlin.system.measureTimeMillis

@Singleton
class InvoiceGenerationRequestPublisher(private val sqs: AmazonSQS) {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    private val queueName: String = System.getenv("INVOICE_GENERATION_REQUEST_QUEUE") ?: ""
    private val queueUrl = sqs.getQueueUrl(queueName).queueUrl

    fun publish(invoiceGenerationRequest: InvoiceGenerationRequest) {
        val time = measureTimeMillis {
            sqs.sendMessage(SendMessageRequest(queueUrl, Jackson.toJsonPrettyString(invoiceGenerationRequest)))
        }

        log.info("InvoiceGenerationRequest published in $time ms")
    }

}