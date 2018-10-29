package pl.altkom.asc.lab.lambda.notification.notification

import com.amazonaws.util.json.Jackson
import org.slf4j.LoggerFactory
import pl.altkom.asc.lab.lambda.notification.invoicing.Invoice

data class InvoiceNotificationRequest(val invoice: Invoice? = null) {
    companion object {
        private val log = LoggerFactory.getLogger(InvoiceNotificationRequest.javaClass)!!

        fun fromJSON(json: String): InvoiceNotificationRequest {
            val start = System.currentTimeMillis()

            val result = Jackson.fromJsonString(json, InvoiceNotificationRequest::class.java)

            log.info("JSON deserialized in ${System.currentTimeMillis() - start}ms")

            return result
        }
    }
}