package pl.altkom.asc.lab.lambda.billing.billing

import java.io.BufferedReader
import javax.inject.Singleton

@Singleton
class ActiveListParser {

    fun parse(name: String, reader: BufferedReader): ActiveList {
        val parts = name.split("_")
        val dataLines: Array<String> = reader.lines().toArray<String> { length -> arrayOfNulls(length) }

        return ActiveList(
                customerCode = parts[0],
                year = parts[1].toInt(),
                month = parts[2].toInt(),
                dataLines = dataLines)
    }
}

data class ActiveList(
        var customerCode: String,
        var year: Int,
        var month: Int,
        var dataLines: Array<String>
)