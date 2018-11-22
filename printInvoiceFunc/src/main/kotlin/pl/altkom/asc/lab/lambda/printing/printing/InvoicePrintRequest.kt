package pl.altkom.asc.lab.lambda.printing.printing

import com.amazonaws.util.json.Jackson
import org.slf4j.LoggerFactory
import pl.altkom.asc.lab.lambda.printing.invoicing.Invoice

data class InvoicePrintRequest(val invoice: Invoice? = null) {
    companion object {
        private val log = LoggerFactory.getLogger(InvoicePrintRequest.javaClass)!!

        fun fromJSON(json: String): InvoicePrintRequest {
            val start = System.currentTimeMillis()

            val result = Jackson.fromJsonString(json, InvoicePrintRequest::class.java)

            log.info("JSON deserialized in ${System.currentTimeMillis()-start}ms")

            return result
        }
    }
}


