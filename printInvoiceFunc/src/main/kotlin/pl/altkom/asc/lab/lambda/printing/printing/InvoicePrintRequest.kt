package pl.altkom.asc.lab.lambda.printing.printing

import com.amazonaws.util.json.Jackson
import pl.altkom.asc.lab.lambda.printing.invoicing.Invoice
import java.util.logging.Logger

data class InvoicePrintRequest(val invoice: Invoice? = null) {
    companion object {
        private val log = Logger.getLogger(InvoicePrintRequest.javaClass.name)!!

        fun fromJSON(json: String): InvoicePrintRequest {
            val start = System.currentTimeMillis()

            val result = Jackson.fromJsonString(json, InvoicePrintRequest::class.java)

            log.info("JSON deserialized in ${System.currentTimeMillis()-start}ms")

            return result
        }
    }
}


