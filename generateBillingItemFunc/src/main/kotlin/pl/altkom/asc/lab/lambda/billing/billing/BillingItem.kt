package pl.altkom.asc.lab.lambda.billing.billing

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable
import java.math.BigDecimal

@DynamoDBTable(tableName = "BillingItem")
data class BillingItem(
        @DynamoDBHashKey(attributeName = "key")
        var key: String,
        var beneficiary: String,
        var productCode: String,
        var amount: BigDecimal
)