package pl.altkom.asc.lab.lambda.billing.pricing

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper

class PriceListRepository {

    private val client = AmazonDynamoDBClientBuilder.standard().build()
    private val mapper = DynamoDBMapper(client)

    fun get(customerCode: String): PriceList {
        return mapper.load(PriceList::class.java, customerCode)
    }
}