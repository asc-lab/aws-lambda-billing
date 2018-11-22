package pl.altkom.asc.lab.lambda.invoice

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.xit
import org.junit.jupiter.api.Assertions.assertEquals


class GenerateInvoiceFuncFunctionTest: Spek({

    describe("generate-invoice-func function") {
        val server = ApplicationContext.run(EmbeddedServer::class.java)
        val client = server.applicationContext.getBean(GenerateInvoiceFuncClient::class.java)

        it("should return 'generate-invoice-func'") {

            val event = Event(arrayOf(EventRecord("{}")))

            assertEquals(client.apply(
                    event
            ).blockingGet(), "OK")
        }

        afterGroup {
            server.stop()
        }
    }
})
