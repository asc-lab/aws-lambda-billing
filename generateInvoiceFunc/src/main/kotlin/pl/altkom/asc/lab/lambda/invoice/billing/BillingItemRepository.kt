package pl.altkom.asc.lab.lambda.invoice.billing

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression
import com.amazonaws.services.dynamodbv2.model.AttributeValue
import pl.altkom.asc.lab.lambda.invoice.invoicing.InvoiceGenerationRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BillingItemRepository @Inject constructor() {

    private val client = AmazonDynamoDBClientBuilder.standard().build()
    private val mapper = DynamoDBMapper(client)

    fun findForInvoice(request: InvoiceGenerationRequest): List<BillingItem> {
        val queryExpression = DynamoDBScanExpression()
                .withFilterExpression("billingKey = :billingKey")
                .withExpressionAttributeValues(mapOf(":billingKey" to AttributeValue().withS("${request.customerCode}-${request.year}-${request.month}")))

        return mapper.scan(BillingItem::class.java, queryExpression)
    }
}