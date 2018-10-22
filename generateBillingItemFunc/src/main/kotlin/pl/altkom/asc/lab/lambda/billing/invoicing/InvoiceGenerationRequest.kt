package pl.altkom.asc.lab.lambda.billing.invoicing

import pl.altkom.asc.lab.lambda.billing.billing.ActiveList

data class InvoiceGenerationRequest(
        var customerCode: String,
        var year: Int,
        var month: Int
) {
    companion object {
        fun forActiveList(activeList: ActiveList): InvoiceGenerationRequest {
            return InvoiceGenerationRequest(activeList.customerCode, activeList.year, activeList.month)
        }
    }
}
