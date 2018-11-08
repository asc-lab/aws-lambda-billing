package pl.altkom.asc.lab.lambda.billing

import com.amazonaws.services.s3.event.S3EventNotification
import io.micronaut.function.FunctionBean
import org.slf4j.LoggerFactory
import pl.altkom.asc.lab.lambda.billing.aws.S3Client
import pl.altkom.asc.lab.lambda.billing.billing.ActiveListParser
import pl.altkom.asc.lab.lambda.billing.billing.BillingItemGenerator
import pl.altkom.asc.lab.lambda.billing.billing.BillingItemRepository
import pl.altkom.asc.lab.lambda.billing.invoicing.InvoiceGenerationRequest
import pl.altkom.asc.lab.lambda.billing.invoicing.InvoiceGenerationRequestPublisher
import pl.altkom.asc.lab.lambda.billing.pricing.PriceListRepository
import java.util.function.Function

@FunctionBean("generate-billing-item-func")
class GenerateBillingItemFunction(
        val s3Client: S3Client,
        val activeListParser: ActiveListParser,
        val priceListRepository: PriceListRepository,
        val billingItemRepository: BillingItemRepository,
        val billingItemGenerator: BillingItemGenerator,
        var invoiceGenerationRequestPublisher: InvoiceGenerationRequestPublisher
) : Function<S3EventNotification, String> {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun apply(event: S3EventNotification): String {
        log.info("Triggered by event with ${event.records.size} records")
        log.info(event.toJson())

        for ((index, record) in event.records.withIndex()) {
            processRecord(index, event, record)
        }

        return "OK"
    }

    private fun processRecord(index: Int, event: S3EventNotification, record: S3EventNotification.S3EventNotificationRecord) {
        log.info("Processing record ${index + 1} of ${event.records.size}")
        val reader = s3Client.getReader(record)
        val activeList = activeListParser.parse(record.s3.`object`.key, reader)

        val priceList = priceListRepository.get(activeList.customerCode)

        billingItemGenerator.generate(activeList, priceList).forEach(billingItemRepository::save)

        invoiceGenerationRequestPublisher.publish(InvoiceGenerationRequest.forActiveList(activeList))

        log.info(activeList.toString())
    }
}