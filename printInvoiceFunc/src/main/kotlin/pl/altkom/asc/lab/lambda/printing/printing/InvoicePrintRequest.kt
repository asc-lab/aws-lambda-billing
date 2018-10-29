package pl.altkom.asc.lab.lambda.printing.printing

import com.amazonaws.util.json.Jackson
import pl.altkom.asc.lab.lambda.printing.invoicing.Invoice

data class InvoicePrintRequest(val invoice: Invoice? = null) {
    companion object {
        fun fromJSON(json: String): InvoicePrintRequest = Jackson.fromJsonString(json, InvoicePrintRequest::class.java)
    }
}


