package pl.altkom.asc.lab.lambda.billing.pricing

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Singleton
class PriceListRepository {

    private val log = LoggerFactory.getLogger(this.javaClass)!!

    private val client = AmazonDynamoDBClientBuilder.standard().build()
    private val mapper = DynamoDBMapper(client)

    fun get(customerCode: String): PriceList {
        val start = System.currentTimeMillis()
        val priceList = mapper.load(PriceList::class.java, customerCode)

        log.info("PriceList fetched in ${System.currentTimeMillis() - start}ms")

        return priceList
    }
}