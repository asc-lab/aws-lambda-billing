package pl.altkom.asc.lab.lambda.billing.billing

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions
import pl.altkom.asc.lab.lambda.billing.pricing.PriceList
import pl.altkom.asc.lab.lambda.billing.pricing.PriceListItem
import java.math.BigDecimal

class BillingItemGeneratorTest : Spek({

    describe("item-generation") {

        val generator = BillingItemGenerator()
        val activeList = ActiveList("ASC", 2018, 10, arrayOf(
                "99050555745;Ka Aa;A",
                "29120458762;Kb Ab;A",
                "39091666028;Kc Ac;B",
                "77050929111;Kd Ad;A",
                "76091166752;Ke Ae;A",
                "97031653569;Kf Af;B",
                "35060205229;Kg Ag;A",
                "38112669875;Kh Ah;B",
                "13102408939;Kh Ah;A"
        ))
        val priceList = PriceList("ASC", listOf(
                PriceListItem("A", Gender.FEMALE, 1, 199, BigDecimal("123")),
                PriceListItem("A", Gender.MALE, 1, 199, BigDecimal("124")),
                PriceListItem("B", Gender.FEMALE, 1, 199, BigDecimal("125")),
                PriceListItem("B", Gender.MALE, 1, 199, BigDecimal("126"))
        )
        )


        it("should return sequence with correct length") {
            Assertions.assertEquals(activeList.dataLines.size, generator.generate(activeList, priceList).count())
        }

        it("should return sequence with 6 A items") {
            Assertions.assertEquals(6, generator.generate(activeList, priceList).filter { it.productCode == "A" }.count())
        }

        it("should return sequence item with proper A price") {
            Assertions.assertEquals(
                    BigDecimal("123"),
                    generator.generate(activeList, priceList)
                            .filter { it.productCode == "A" && it.beneficiary.startsWith("99050555745") }
                            .first()
                            .amount
            )
        }
    }
})