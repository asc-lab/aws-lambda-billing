package pl.altkom.asc.lab.lambda.invoice.invoicing

import com.amazonaws.util.json.Jackson

data class InvoiceGenerationRequest(
        var customerCode: String = "",
        var year: Int = 0,
        var month: Int = 0
) {
    companion object {
        fun fromJSON(json: String): InvoiceGenerationRequest {
            return Jackson.fromJsonString(json, InvoiceGenerationRequest::class.java)
        }
    }
}
