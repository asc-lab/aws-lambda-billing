package pl.altkom.asc.lab.lambda.billing.billing

import java.time.LocalDate
import java.time.temporal.ChronoUnit


data class Beneficiary(
        val name: String,
        val nationalId: String,
        val birthdate: LocalDate,
        val gender: Gender,
        val productCode: String
) {

    constructor(nationalId: String, name: String, productCode: String) :
            this(name, nationalId, Pesel.getBirthDateFromPesel(nationalId), Pesel.getGenderFromPesel(nationalId), productCode)

    fun ageAt(date: LocalDate) :Int {
        return birthdate.until(date, ChronoUnit.YEARS).toInt()
    }

    companion object {
        fun fromCsvLine(csvLine: String): Beneficiary {
            val parts = csvLine.split(';')
            return Beneficiary(parts[0], parts[1], parts[2])
        }
    }

}