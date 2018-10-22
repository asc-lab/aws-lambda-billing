package pl.altkom.asc.lab.lambda.billing.pricing

import com.amazonaws.services.dynamodbv2.datamodeling.*
import pl.altkom.asc.lab.lambda.billing.billing.Beneficiary
import pl.altkom.asc.lab.lambda.billing.billing.Gender
import java.math.BigDecimal
import java.time.LocalDate

@DynamoDBTable(tableName = "PriceList")
data class PriceList(
        @DynamoDBHashKey(attributeName = "customerCode")
        var customerCode: String = "",

        @DynamoDBAttribute(attributeName = "prices")
        var prices: List<PriceListItem> = listOf()
) {
    fun getPrice(beneficiary: Beneficiary, date: LocalDate): BigDecimal {
        return prices.asSequence().filter { it.matches(beneficiary, date) }.map { it.price }.first()
    }
}

@DynamoDBDocument
data class PriceListItem(
        var productCode: String="",

        @DynamoDBTypeConvertedEnum
        var gender: Gender = Gender.MALE,
        var ageFrom: Int = 1,
        var ageTo: Int = 100,
        var price: BigDecimal = BigDecimal.ZERO
) {
    fun matches(beneficiary: Beneficiary, date: LocalDate): Boolean {
        return beneficiary.productCode == productCode &&
                beneficiary.gender == gender &&
                beneficiary.ageAt(date) >= ageFrom &&
                beneficiary.ageAt(date) <= ageTo
    }
}