package pl.altkom.asc.lab.lambda.billing.billing

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.lifecycle.CachingMode.TEST
import org.junit.jupiter.api.Assertions
import pl.altkom.asc.lab.lambda.billing.billing.ActiveListParser
import java.io.BufferedReader
import java.io.StringReader

class ActiveListParserTest : Spek({

    describe("file-parsing") {

        val parser = ActiveListParser()
        val reader by memoized(TEST) {
            BufferedReader(StringReader(
                    "99050555745;Ka Aa;A\n" +
                            "29120458762;Kb Ab;A\n" +
                            "39091666028;Kc Ac;B\n" +
                            "77050929111;Kd Ad;A\n" +
                            "76091166752;Ke Ae;A\n" +
                            "97031653569;Kf Af;B\n" +
                            "35060205229;Kg Ag;A\n" +
                            "38112669875;Kh Ah;B\n" +
                            "13102408939;Kh Ah;A"
            ))
        }


        it("should return proper customer code") {
            val result = parser.parse("ASC_2018_02_activeList.csv", reader)
            Assertions.assertEquals("ASC", result.customerCode)
        }

        it("should return proper year") {
            val result = parser.parse("ASC_2018_02_activeList.csv", reader)
            Assertions.assertEquals(2018, result.year)
        }

        it("should return proper month") {
            val result = parser.parse("ASC_2018_02_activeList.csv", reader)
            Assertions.assertEquals(2, result.month)
        }

        it("should return proper number of lines") {
            val result = parser.parse("ASC_2018_02_activeList.csv", reader)
            Assertions.assertEquals(9, result.dataLines.size)
        }

    }
})