package pl.altkom.asc.lab.lambda.notification.invoicing

import java.math.BigDecimal

data class Invoice(
        var customer: String = "",
        var invoiceNumber: String = "",
        var description: String = "",
        var lines: MutableList<InvoiceLine> = mutableListOf(),
        var totalCost: BigDecimal = BigDecimal.ZERO
)

data class InvoiceLine(
        var itemName: String = "",
        var cost: BigDecimal = BigDecimal.ZERO
)