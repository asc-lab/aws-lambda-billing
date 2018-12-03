package pl.altkom.asc.lab.lambda.billing.pricing

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import java.util.logging.Logger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PriceListRepository @Inject constructor() {

    private val log = Logger.getLogger(this.javaClass.name)!!

    private val client = AmazonDynamoDBClientBuilder.standard().build()
    private val mapper = DynamoDBMapper(client)

    fun get(customerCode: String): PriceList {
        val start = System.currentTimeMillis()
        val priceList = mapper.load(PriceList::class.java, customerCode)

        log.info("PriceList fetched in ${System.currentTimeMillis() - start}ms")

        return priceList
    }
}