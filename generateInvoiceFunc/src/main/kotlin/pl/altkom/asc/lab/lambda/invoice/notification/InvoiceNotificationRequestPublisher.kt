package pl.altkom.asc.lab.lambda.invoice.notification

import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.model.SendMessageRequest
import com.amazonaws.util.json.Jackson
import java.util.logging.Logger
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class InvoiceNotificationRequestPublisher @Inject constructor(private val sqs: AmazonSQS) {

    private val log = Logger.getLogger(this.javaClass.name)!!

    private val queueName: String = System.getenv("INVOICE_NOTIFY_REQUEST_QUEUE") ?: ""
    private val queueUrl = sqs.getQueueUrl(queueName).queueUrl

    fun publish(invoicePrintRequest: InvoiceNotificationRequest) {
        val time = measureTimeMillis {
            sqs.sendMessage(SendMessageRequest(queueUrl, Jackson.toJsonPrettyString(invoicePrintRequest)))
        }

        log.info("InvoiceGenerationRequest published in $time ms")
    }

}