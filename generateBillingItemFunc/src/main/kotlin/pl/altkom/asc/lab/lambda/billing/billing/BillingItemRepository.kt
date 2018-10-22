package pl.altkom.asc.lab.lambda.billing.billing

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper

class BillingItemRepository {

    private val client = AmazonDynamoDBClientBuilder.standard().build()
    private val mapper = DynamoDBMapper(client)

    fun save(billingItem: BillingItem) {
        return mapper.save(billingItem)
    }
}