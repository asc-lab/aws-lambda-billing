package pl.altkom.asc.lab.lambda.printing

import io.micronaut.context.ApplicationContext
import io.micronaut.runtime.server.EmbeddedServer
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.jupiter.api.Assertions.assertEquals


class PrintInvoiceFuncFunctionTest : Spek({

    describe("print-invoice-func function") {
        val server = ApplicationContext.run(EmbeddedServer::class.java)
        val client = server.applicationContext.getBean(PrintInvoiceFuncClient::class.java)

        it("should return 'print-invoice-func'") {
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
