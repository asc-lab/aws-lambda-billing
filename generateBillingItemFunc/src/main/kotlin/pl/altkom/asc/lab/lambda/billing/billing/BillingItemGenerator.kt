package pl.altkom.asc.lab.lambda.billing.billing

import com.amazonaws.util.StringUtils
import pl.altkom.asc.lab.lambda.billing.pricing.PriceList
import java.time.LocalDate
import java.util.*
import javax.inject.Singleton

@Singleton
class BillingItemGenerator {

    fun generate(activeList: ActiveList, priceList: PriceList): Sequence<BillingItem> {
        val billingDate = LocalDate.of(activeList.year, activeList.month, 1)

        return activeList.dataLines.asSequence()
                .filter { !StringUtils.isNullOrEmpty(it) }
                .map {
                    val beneficiary = Beneficiary.fromCsvLine(it)
                    val price = priceList.getPrice(beneficiary, billingDate)

                    BillingItem(UUID.randomUUID().toString(),
                            "${activeList.customerCode}-${activeList.year}-${activeList.month}",
                            "${beneficiary.nationalId} ${beneficiary.name}",
                            beneficiary.productCode,
                            price)
                }

    }

}