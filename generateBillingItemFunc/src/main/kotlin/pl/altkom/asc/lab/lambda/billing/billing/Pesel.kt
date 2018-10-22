package pl.altkom.asc.lab.lambda.billing.billing

import java.time.LocalDate

class Pesel {
    companion object {
        fun getBirthDateFromPesel(pesel: String): LocalDate {
            val ctrl = pesel.substring(2, 3).toInt()

            val year = pesel.substring(0, 2).toInt() + (if (ctrl <= 1) 1900 else 2000)

            var month = pesel.substring(3, 4).toInt()
            month += if (ctrl % 2 != 0) 10 else 0

            val day = pesel.substring(4, 6).toInt()

            return LocalDate.of(year, month, day)
        }

        fun getGenderFromPesel(pesel: String) = if (pesel.substring(9, 10).toInt() % 2 == 0) Gender.FEMALE else Gender.MALE
    }

}

