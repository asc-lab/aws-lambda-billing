package pl.altkom.asc.lab.lambda.billing.billing

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions
import pl.altkom.asc.lab.lambda.billing.billing.Gender
import pl.altkom.asc.lab.lambda.billing.billing.Pesel
import java.time.LocalDate

class PeselTest : Spek({

    describe("getting-birthdate-from-pesel") {

        it("should return date form 19xx") {
            Assertions.assertEquals(LocalDate.of(1983,4,21), Pesel.getBirthDateFromPesel("83042110494"))
        }

        it("should return date form 20xx") {
            Assertions.assertEquals(LocalDate.of(2018,7,4), Pesel.getBirthDateFromPesel("18270479617"))
        }
    }

    describe("getting-gender-from-pesel") {

        it("should return female") {
            Assertions.assertEquals(Gender.FEMALE, Pesel.getGenderFromPesel("00240418542"))
        }

        it("should return male") {
            Assertions.assertEquals(Gender.MALE, Pesel.getGenderFromPesel("18270479617"))
        }
    }
})