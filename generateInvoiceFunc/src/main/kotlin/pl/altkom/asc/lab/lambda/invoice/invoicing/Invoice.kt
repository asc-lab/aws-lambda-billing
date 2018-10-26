package pl.altkom.asc.lab.lambda.invoice.invoicing

import pl.altkom.asc.lab.lambda.invoice.billing.BillingItem
import java.math.BigDecimal

data class Invoice(
        val customer: String,
        val invoiceNumber: String,
        val description: String,
        val lines: MutableList<InvoiceLine>,
        var totalCost: BigDecimal
) {

    fun billItems(itemsToBill: List<BillingItem>) {

        val groups = itemsToBill.groupBy { it.productCode }.toSortedMap()
        groups.forEach { group ->
            lines.add(InvoiceLine(
                    "Policy  ${group.key}",
                    group.value.map { it.amount }.fold(BigDecimal.ZERO, BigDecimal::add)))
        }

        totalCost = lines.map { it.cost }.fold(BigDecimal.ZERO, BigDecimal::add)

    }

    companion object {
        fun create(customerCode: String, year: Int, month: Int): Invoice {
            return Invoice(
                    customerCode,
                    "${customerCode}/${month}/${year}",
                    "Invoice for insurance policies for ${month}/${year}",
                    mutableListOf(),
                    BigDecimal.ZERO
            )
        }
    }

}

data class InvoiceLine(
        val itemName: String,
        val cost: BigDecimal
)