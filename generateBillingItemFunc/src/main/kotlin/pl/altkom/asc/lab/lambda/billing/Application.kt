package pl.altkom.asc.lab.lambda.billing

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("pl.altkom.asc.lab.lambda.billing")
                .mainClass(Application.javaClass)
                .start()
    }
}